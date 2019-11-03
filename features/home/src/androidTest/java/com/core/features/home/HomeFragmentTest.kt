package com.core.features.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @MockK
    lateinit var homeViewModel: HomeViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModelFactory = ViewModelUtil.createFor(
            mapOf<Class<out ViewModel>, ViewModel>(
                Pair(HomeViewModel::class.java, homeViewModel)
            )
        )
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.core.features.home.test", appContext.packageName)
    }

    @Test
    fun bla() {
        val fragmentFactory = object: FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                val fragmentInstance = super.instantiate(classLoader, className)
                (fragmentInstance as HomeFragment).viewModelFactory = viewModelFactory
                return fragmentInstance
            }
        }
        val fragmentScenario = launchFragmentInContainer<HomeFragment>(factory = fragmentFactory)
    }
}
