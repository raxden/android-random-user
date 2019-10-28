package com.core.data.repository

import androidx.lifecycle.Observer
import com.core.common.android.Resource
import com.core.common.android.Result
import com.core.data.BaseRepositoryTest
import com.core.data.repository.mapper.UserEntityDataMapper
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ProjectRepositoryTest: BaseRepositoryTest() {

    @RelaxedMockK
    private lateinit var projectListObserver: Observer<Resource<List<Project>>>

    private lateinit var projectRepository: ProjectRepositoryImpl

    private var projectEntitySampleList = listOf(
        ProjectEntity(1),
        ProjectEntity(2),
        ProjectEntity(3)
    )

    private var projectSampleList = listOf(
        Project(1),
        Project(2),
        Project(3)
    )

    @Before
    override fun setUp() {
        super.setUp()

        val userDataMapper = UserEntityDataMapper()
        val projectDataMapper = ProjectDataMapper(userDataMapper)

        projectRepository = ProjectRepositoryImpl(gateway, projectDataMapper)
    }

    @Test
    fun `Get projects from network`() {
        coEvery { gateway.projectList("raxden") } returns Result.Success(projectEntitySampleList)

        runBlocking {
            projectRepository.list("raxden").observeForever(projectListObserver)
        }

        verifyOrder {
            projectListObserver.onChanged(Resource.loading(null)) // Init loading with no value
            projectListObserver.onChanged(Resource.success(projectSampleList)) // retrofit data loaded
        }
        confirmVerified(projectListObserver)
    }

    @Test
    fun `Get projects from server when no internet is available`() {
        val exception = Exception("Internet")
        coEvery { gateway.projectList("raxden") } returns Result.Error(exception)

        runBlocking {
            projectRepository.list("raxden").observeForever(projectListObserver)
        }

        verifyOrder {
            projectListObserver.onChanged(Resource.loading(null)) // Init loading with no value
            projectListObserver.onChanged(Resource.error(exception, projectSampleList)) // retrofit data loaded
        }
        confirmVerified(projectListObserver)
    }
}