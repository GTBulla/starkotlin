package br.com.gtbulla.libraries.common.model.mapper

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
@DisplayName("Library > Common > Models - RepositoryGitMapper")
internal class RepositoryGitMapperTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @DisplayName("mapRemoteListToDomain - GIVEN remoteList")
    @Nested
    inner class MapRemoteListToDomain {

        @DisplayName("WHEN map remote list to domain")
        @Nested
        inner class When {

            @org.junit.jupiter.api.Test
            fun `THEN the result should be map to domain`() {
                withRepositoryGitMapperRobot {
                    when_mapRemoteListToDomain()
                } action {
                    mapRemoteListToDomain()
                } result {
                    check_mapRemoteListToDomain()
                }
            }

        }
    }

    @DisplayName("mapDomainListToUI - GIVEN domainList")
    @Nested
    inner class MapDomainListToUI {

        @DisplayName("WHEN map domain list to UI")
        @Nested
        inner class MapDomainListToUI {

            @org.junit.jupiter.api.Test
            fun `THEN the result should be map to UI`() {
                withRepositoryGitMapperRobot {
                    when_mapDomainListToUI()
                } action {
                    mapDomainListToUI()
                } result {
                    check_mapDomainListToUI()
                }
            }

        }
    }

}