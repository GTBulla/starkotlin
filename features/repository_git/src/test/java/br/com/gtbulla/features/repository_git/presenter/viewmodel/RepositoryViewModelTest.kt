package br.com.gtbulla.features.repository_git.presenter.viewmodel

import androidx.paging.PagingData
import br.com.gtbulla.features.repository_git.data.repository.RepoGitRepository
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitOwner
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.testcore.junitutils.v5.CoroutineTest
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*
import org.koin.test.AutoCloseKoinTest

//@ExperimentalCoroutinesApi
@DisplayName("Feature > RepositoryGit - RepositoryViewModel")
internal class RepositoryViewModelTest : AutoCloseKoinTest(), CoroutineTest {
    override lateinit  var dispatcher: TestCoroutineDispatcher
    override lateinit  var testScope: TestCoroutineScope

//    @BeforeEach
//    fun setUp() {
//        MockKAnnotations.init(this)
//    }
//
//    @AfterEach
//    fun tearDown() {
//        unmockkAll()
//    }

    @DisplayName("getList - WHEN get list")
    @Nested
    inner class GetList {

        @org.junit.jupiter.api.Test
        fun `THEN the result should be an list 1`() = runBlockingTest {
            val repository = mockk<RepoGitRepository>(relaxed = true)
            val mapper = mockk<RepositoryGitMapper>(relaxed = true)
            val viewModel = spyk(RepositoryViewModel(testScope, repository, mapper))

            val response = mutableListOf(
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

            val resultGetList = viewModel.getList()

            Assertions.assertEquals(resultGetList.count(), 1)


//            withRepositoryViewModelRobot {
//                when_getList(testScope)
//            } action {
//                getList(dispatcher)
//            } result {
//                check_getList()
//            }
        }

        @org.junit.jupiter.api.Test
        fun `THEN the result should be an list`() = dispatcher.runBlockingTest {
            withRepositoryViewModelRobot {
                when_getList(testScope)
            } action {
                getList(dispatcher)
            } result {
                check_getList()
            }
        }

    }

}