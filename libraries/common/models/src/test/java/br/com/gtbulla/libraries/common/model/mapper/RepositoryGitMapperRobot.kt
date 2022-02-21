package br.com.gtbulla.libraries.common.model.mapper

import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.common.model.data.RepositoryGitOwnerResponse
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitOwner
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI
import br.com.gtbulla.libraries.testcore.junitutils.v5.CoroutineTest
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.Assertions

internal fun withRepositoryGitMapperRobot(fn: RepositoryGitMapperRobot.() -> Unit) =
    RepositoryGitMapperRobot().apply(fn)

internal class RepositoryGitMapperRobot : CoroutineTest {
    override lateinit var testScope: TestCoroutineScope
    override var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    val mapper: RepositoryGitMapper = RepositoryGitMapperImpl(defaultDispatcher = dispatcher)

    lateinit var paramRemoteList: List<RepositoryGitItemResponse>
    lateinit var paramDomainList: List<RepositoryGitItem>

    lateinit var resultMapRemoteListToDomain: List<RepositoryGitItem>
    lateinit var resultMapDomainListToUI: List<RepositoryGitItemUI>

    infix fun action(fn: RepositoryGitMapperActionRobot.() -> Unit) =
        RepositoryGitMapperActionRobot(this).apply(fn)

    fun when_mapRemoteListToDomain() {
        val item = RepositoryGitItemResponse(
            id = 1,
            name = "okhttp",
            owner = RepositoryGitOwnerResponse(
                "square",
                "https://avatars.githubusercontent.com/u/82592?v=4"
            ),
            starCount = 200,
            forkCount = 5,
        )
        paramRemoteList = listOf(item)
    }

    fun when_mapDomainListToUI() {
        val item = RepositoryGitItem(
            id = 1,
            name = "okhttp",
            owner = RepositoryGitOwner(
                "square",
                "https://avatars.githubusercontent.com/u/82592?v=4"
            ),
            starCount = 200,
            forkCount = 5,
        )
        paramDomainList = listOf(item)
    }
}

internal class RepositoryGitMapperActionRobot(private val robot: RepositoryGitMapperRobot) {

    infix fun result(block: RepositoryGitMapperResultRobot.() -> Unit) =
        RepositoryGitMapperResultRobot(robot).apply(block)

    fun mapRemoteListToDomain() {
        runBlocking {
            robot.resultMapRemoteListToDomain =
                robot.mapper.mapRemoteListToDomain(robot.paramRemoteList)
        }
    }

    fun mapDomainListToUI() {
        runBlocking {
            robot.resultMapDomainListToUI =
                robot.mapper.mapDomainListToUI(robot.paramDomainList)
        }
    }

}

internal class RepositoryGitMapperResultRobot(private val robot: RepositoryGitMapperRobot) {

    fun check_mapRemoteListToDomain() {
        val item = robot.paramRemoteList[0]
        val domain = robot.resultMapRemoteListToDomain[0]
        Assertions.assertEquals(
            item.id,
            domain.id,
            "Remote Id is not the same as the Domain id",
        )
        Assertions.assertEquals(
            item.name,
            domain.name,
            "Remote Name field is not the same as the Domain",
        )
        Assertions.assertEquals(
            item.owner.login,
            domain.owner.login,
            "Remote Login field is not the same as the Domain",
        )
        Assertions.assertEquals(
            item.owner.avatarUrl,
            domain.owner.avatarUrl,
            "Remote AvatarUrl field is not the same as the Domain",
        )
        Assertions.assertEquals(
            item.starCount,
            domain.starCount,
            "Remote StarCount field is not the same as the Domain",
        )
        Assertions.assertEquals(
            item.forkCount,
            domain.forkCount,
            "Remote ForkCount field is not the same as the Domain",
        )
    }

    fun check_mapDomainListToUI() {
        val item = robot.paramDomainList[0]
        val ui = robot.resultMapDomainListToUI[0]
        Assertions.assertEquals(
            item.id,
            ui.id,
            "Domain Id is not the same as UI id",
        )
        Assertions.assertEquals(
            item.name,
            ui.name,
            "Domain Name field is not the same as UI",
        )
        Assertions.assertEquals(
            item.owner.login,
            ui.owner.login,
            "Domain Login field is not the same as UI",
        )
        Assertions.assertEquals(
            item.owner.avatarUrl,
            ui.owner.avatarUrl,
            "Domain AvatarUrl field is not the same as UI",
        )
        Assertions.assertEquals(
            item.starCount,
            ui.starCount,
            "Domain StarCount field is not the same as UI",
        )
        Assertions.assertEquals(
            item.forkCount,
            ui.forkCount,
            "Domain ForkCount field is not the same as UI",
        )
    }

}