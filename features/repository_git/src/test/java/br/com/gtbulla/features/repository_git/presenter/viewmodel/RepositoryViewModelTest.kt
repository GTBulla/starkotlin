package br.com.gtbulla.features.repository_git.presenter.viewmodel

import br.com.gtbulla.libraries.testcore.junitutils.v5.CoroutineTest
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
@DisplayName("Feature > RepositoryGit - RepositoryViewModel")
internal class RepositoryViewModelTest : AutoCloseKoinTest(), CoroutineTest {
    override lateinit  var dispatcher: TestCoroutineDispatcher
    override lateinit  var testScope: TestCoroutineScope

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @DisplayName("getList - WHEN get list")
    @Nested
    inner class GetList {

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