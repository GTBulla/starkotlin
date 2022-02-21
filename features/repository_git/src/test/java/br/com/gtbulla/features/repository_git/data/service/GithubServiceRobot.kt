package br.com.gtbulla.features.repository_git.data.service

import br.com.gtbulla.features.repository_git.data.RepositoryData
import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.data.response.ResponseItems
import br.com.gtbulla.libraries.testcore.helper.FileHelper
import br.com.gtbulla.libraries.testcore.junitutils.v5.CoroutineTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.Assertions
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal fun withGithubServiceRobot(fn: GithubServiceRobot.() -> Unit) =
    GithubServiceRobot().apply(fn)

internal class GithubServiceRobot : CoroutineTest {
    override lateinit var testScope: TestCoroutineScope
    override var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var resultGetRepositoryList: ResponseItems<RepositoryGitItemResponse>

    infix fun action(fn: GithubServiceActionRobot.() -> Unit) =
        GithubServiceActionRobot(this).apply(fn)

    fun when_none() {
        return
    }

    companion object {
        private val mockWebServer = MockWebServer()
        lateinit var service: GithubService

        fun setup() {
            mockWebServer.start()
            mockWebServer.dispatcher = setupDispatcher()
            setupService()
        }

        fun tearDown() =
            mockWebServer.shutdown()

        private fun setupService() {
            service = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(mockWebServer.url("/"))
                .build()
                .create(GithubService::class.java)
        }

        private fun setupDispatcher(): Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/search/repositories?q=language:kotlin&sort=stars1&page=1" -> {
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(FileHelper.readFromResources("repositories.json"))
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }

    }
}

internal class GithubServiceActionRobot(private val robot: GithubServiceRobot) {

    infix fun result(block: GithubServiceResultRobot.() -> Unit) =
        GithubServiceResultRobot(robot).apply(block)

    fun getRepositoryList() {
        runBlocking {
            robot.resultGetRepositoryList =
                GithubServiceRobot.service.getRepositoryList(
                    query = "language:kotlin",
                    sort = "stars",
                    page = 1
                )
        }
    }

}

internal class GithubServiceResultRobot(private val robot: GithubServiceRobot) {

    fun check_getRepositoryList() {
        Assertions.assertEquals(
            RepositoryData.provideRemoteFromAssets().size,
            robot.resultGetRepositoryList.items.size,
            "Repositories size does not match the one provided in resources.",
        )
    }

}