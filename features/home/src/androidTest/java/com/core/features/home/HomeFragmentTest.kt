package com.core.features.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.core.common.android.Event
import com.core.common.android.Resource
import com.core.common.test.espresso.MyViewAction
import com.core.common.test.espresso.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.core.domain.User
import com.core.features.home.model.UserModel
import com.core.navigation.NavigationHelper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @RelaxedMockK
    lateinit var navigationHelper: NavigationHelper
    @RelaxedMockK
    lateinit var homeViewModel: HomeViewModel

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    private val throwable = MutableLiveData<Throwable>()
    private val users = MediatorLiveData<Resource<List<UserModel>>>()
    private val userSelected = MutableLiveData<Event<User>>()

    private lateinit var fragment: HomeFragment

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModelFactory = ViewModelUtil.createFor(
            mapOf<Class<out ViewModel>, ViewModel>(
                Pair(HomeViewModel::class.java, homeViewModel)
            )
        )

        every { homeViewModel.throwable } returns throwable
        every { homeViewModel.users } returns users
        every { homeViewModel.userSelected } returns userSelected

        val scenario = launchFragmentInContainer<HomeFragment>(
            themeResId = R.style.AppTheme,
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    val fragmentInstance = super.instantiate(classLoader, className)
                    (fragmentInstance as HomeFragment).viewModelFactory = viewModelFactory
                    fragmentInstance.navigationHelper = navigationHelper
                    return fragmentInstance
                }
            }
        )
        scenario.onFragment { fragment = it }
    }

    @Test
    fun testRecyclerViewContainsItems() {
        users.postValue(Resource.success(FakeModelData.users))

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )
        onView(withId(R.id.recycler_view)).check(withItemCount(FakeModelData.users.size))
        onView(withId(R.id.info_empty_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.info_empty_text_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testRecyclerViewNotContainsItems() {
        users.postValue(Resource.success(emptyList()))

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )

        onView(withId(R.id.recycler_view)).check(withItemCount(0))
        onView(withId(R.id.info_empty_view)).check(matches(isDisplayed()))
        onView(withId(R.id.info_empty_text_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testRefreshWhenError() {
        every { homeViewModel.refreshContent() } answers {
            users.postValue(Resource.error(Exception("no_internet"), FakeModelData.users))
        }

        onView(withId(R.id.swipe_refresh_layout)).perform(ViewActions.swipeDown())

        onView(withText(R.string.home_error_to_retrieve_users))
            .inRoot(RootMatchers.withDecorView(not(`is`(fragment.activity?.window?.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSelectItem() {
        users.postValue(Resource.success(FakeModelData.users))

        every { homeViewModel.onItemSelected(any()) } answers {
            userSelected.postValue(Event(User("1")))
        }
        
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        verify(exactly = 1) { navigationHelper.launchDetail(any()) }
    }

    @Test
    fun testRemoveItemWhenError() {
        users.postValue(Resource.success(FakeModelData.users))

        every { homeViewModel.onItemRemoved(any()) } answers {
            throwable.postValue(Exception("Ha ocurrido un error"))
        }

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                MyViewAction.clickChildViewWithId(R.id.removeView)
            )
        )

        onView(withText(R.string.home_error_unknown_title)).check(matches(isDisplayed()))
    }
}
