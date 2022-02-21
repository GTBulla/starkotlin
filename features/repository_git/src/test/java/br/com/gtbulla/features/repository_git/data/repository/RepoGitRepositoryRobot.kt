package br.com.gtbulla.features.repository_git.data.repository

import androidx.paging.*
import br.com.gtbulla.features.repository_git.data.datasource.RepositoryDataSource
import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.common.model.data.RepositoryGitOwnerResponse
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.data.dao.RepositoryGitItemDao
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions

internal fun withRepoGitRepositoryRobot(fn: RepoGitRepositoryRobot.() -> Unit) =
    RepoGitRepositoryRobot().apply(fn)

internal class RepoGitRepositoryRobot {
    var dataSource = mockk<RepositoryDataSource>(relaxed = true)
    var mapper = mockk<RepositoryGitMapper>(relaxed = true)
    var repository = spyk(RepoGitRepositoryImpl(dataSource, mapper))
    lateinit var response: List<RepositoryGitItemEntity>
    val list = mutableListOf<RepositoryGitItem>()
    lateinit var resultGetList: Flow<PagingData<RepositoryGitItem>>

    infix fun action(fn: RepoGitRepositoryActionRobot.() -> Unit) =
        RepoGitRepositoryActionRobot(this).apply(fn)

    fun when_getList() {
        response = mutableListOf(
            RepositoryGitItemEntity(
                id = 1,
                name = "okhttp",
                owner = "square",
                avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                starCount = 20000,
                forkCount = 5,
            ),
            RepositoryGitItemEntity(
                id = 2,
                name = "kotlin",
                owner = "JetBrains",
                avatarUrl = "https://avatars.githubusercontent.com/u/878437?v=4",
                starCount = 5343,
                forkCount = 51,
            ),
        )
        val flowResponse = flowOf(PagingData.from(response))
        coEvery { dataSource.getList() } returns flowResponse
    }

}

internal class RepoGitRepositoryActionRobot(private val robot: RepoGitRepositoryRobot) {

    infix fun result(block: RepoGitRepositoryResultRobot.() -> Unit) =
        RepoGitRepositoryResultRobot(robot).apply(block)

    fun getList() {
        robot.resultGetList = robot.repository.getList()
    }

}

internal class RepoGitRepositoryResultRobot(private val robot: RepoGitRepositoryRobot) {

    fun check_getList() {
        val list = mutableListOf<RepositoryGitItem>()
        runBlocking {
            robot.resultGetList.collectLatest {
                it.map { item ->
                    list.add(item)
                }
            }
        }

        var count = 0
        for (item in list) {
            val resp = robot.response[count]
            Assertions.assertEquals(
                resp.id,
                item.id
            )
            Assertions.assertEquals(
                resp.name,
                item.name
            )
            Assertions.assertEquals(
                0,
                item.id
            )
            Assertions.assertEquals(
                resp.id,
                item.id
            )
            Assertions.assertEquals(
                resp.id,
                item.id
            )
            count++
        }
    }

}