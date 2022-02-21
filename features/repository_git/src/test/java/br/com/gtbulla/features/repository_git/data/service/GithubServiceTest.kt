package br.com.gtbulla.features.repository_git.data.service

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
@DisplayName("Feature > RepositoryGit - GithubService")
internal class GithubServiceTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        GithubServiceRobot.setup()
    }

    @AfterEach
    fun tearDown() {
        GithubServiceRobot.tearDown()
        unmockkAll()
    }

    @DisplayName("getRepositoryList - GIVEN query, sort, page")
    @Nested
    inner class GetRepositoryList {

        @DisplayName("WHEN get repository list")
        @Nested
        inner class When {

            @org.junit.jupiter.api.Test
            fun `THEN the result should be an list`() {
                withGithubServiceRobot {
                    when_none()
                } action {
                    getRepositoryList()
                } result {
                    check_getRepositoryList()
                }
            }

        }
    }

}