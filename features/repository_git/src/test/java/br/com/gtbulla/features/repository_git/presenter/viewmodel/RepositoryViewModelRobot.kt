package br.com.gtbulla.features.repository_git.presenter.viewmodel

import androidx.paging.PagingData
import br.com.gtbulla.features.repository_git.data.RepositoryData
import br.com.gtbulla.features.repository_git.data.repository.RepoGitRepository
import br.com.gtbulla.features.repository_git.data.repository.RepoGitRepositoryImpl
import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitOwner
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI
import br.com.gtbulla.libraries.data.response.ResponseItems
import br.com.gtbulla.libraries.testcore.helper.FileHelper
import br.com.gtbulla.libraries.testcore.junitutils.v5.CoroutineTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.Assertions
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal fun withRepositoryViewModelRobot(fn: RepositoryViewModelRobot.() -> Unit) =
    RepositoryViewModelRobot().apply(fn)

internal class RepositoryViewModelRobot {
    val repository = mockk<RepoGitRepository>(relaxed = true)
    val mapper = mockk<RepositoryGitMapper>(relaxed = true)
    lateinit var viewModel: RepositoryViewModel

    lateinit var response: List<RepositoryGitItem>
    lateinit var resultGetList: Flow<PagingData<RepositoryGitItemUI>>

    infix fun action(fn: RepositoryViewModelActionRobot.() -> Unit) =
        RepositoryViewModelActionRobot(this).apply(fn)

    fun when_getList(testScope: CoroutineScope) {
        viewModel = spyk(RepositoryViewModel(testScope, repository, mapper))
        response = mutableListOf(
            RepositoryGitItem(
                id = 1,
                name = "okhttp",
                owner = RepositoryGitOwner(
                    "square",
                    "https://avatars.githubusercontent.com/u/82592?v=4",
                ),
                starCount = 20000,
                forkCount = 5,
            ),
            RepositoryGitItem(
                id = 2,
                name = "kotlin",
                owner = RepositoryGitOwner(
                    "JetBrains",
                    "https://avatars.githubusercontent.com/u/878437?v=4",
                ),
                starCount = 5343,
                forkCount = 51,
            ),
        )
        val flowResponse = flowOf(PagingData.from(response))
        coEvery { repository.getList() } returns flowResponse
    }
}

internal class RepositoryViewModelActionRobot(private val robot: RepositoryViewModelRobot) {

    infix fun result(block: RepositoryViewModelResultRobot.() -> Unit) =
        RepositoryViewModelResultRobot(robot).apply(block)

    fun getList(dispatcher: TestCoroutineDispatcher) {
        dispatcher.runBlockingTest {
            robot.resultGetList = robot.viewModel.getList()
        }
    }

}

internal class RepositoryViewModelResultRobot(private val robot: RepositoryViewModelRobot) {

    fun check_getList() {
        runBlocking {
            Assertions.assertEquals(robot.resultGetList.count(), 1)
        }
    }

}