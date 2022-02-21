package br.com.gtbulla.features.repository_git.data.repository

import br.com.gtbulla.libraries.testcore.junitutils.v5.InstantTaskExecutorExtension
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
@DisplayName("Feature > RepositoryGit - RepoGitRepository")
internal class RepoGitRepositoryTest : AutoCloseKoinTest() {

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
        fun `THEN the result should be an list`() {
            withRepoGitRepositoryRobot {
                when_getList()
            } action {
                getList()
            } result {
                check_getList()
            }
        }
    }

}