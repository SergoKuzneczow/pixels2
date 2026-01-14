package com.sergokuzneczow.home

import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyFakeUseCase
import com.sergokuzneczow.domain.get_home_screen_pager4_use_case.GetHomeScreenPager4FakeUseCase
import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Meta
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Page
import com.sergokuzneczow.home.impl.HomeScreenIntent
import com.sergokuzneczow.home.impl.HomeScreenSideEffect
import com.sergokuzneczow.home.impl.HomeScreenState
import com.sergokuzneczow.home.impl.models.StandardQuery
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage.SuggestedQuery
import com.sergokuzneczow.home.impl.models.toSuggestedQueriesPages
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModel
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.test
import java.util.TreeMap

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeScreenViewModelTest() {

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var getHomeScreenPager4FakeUseCase: GetHomeScreenPager4FakeUseCase

    private lateinit var getFirstPageKeyFakeUseCase: GetFirstPageKeyFakeUseCase

    private val dispatchersApi: DispatchersApi = object : DispatchersApi {
        override val io: CoroutineDispatcher
            get() = StandardTestDispatcher()
        override val default: CoroutineDispatcher
            get() = StandardTestDispatcher()
        override val main: CoroutineDispatcher
            get() = StandardTestDispatcher()
    }

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `the initial state should be Loading`(): TestResult = runTest {
        getHomeScreenPager4FakeUseCase = GetHomeScreenPager4FakeUseCase()
        getFirstPageKeyFakeUseCase = GetFirstPageKeyFakeUseCase()
        homeScreenViewModel = HomeScreenViewModel(
            dispatchersApi = dispatchersApi,
            getHomeScreenPager4UseCase = getHomeScreenPager4FakeUseCase,
            getFirstPageKeyUseCase = getFirstPageKeyFakeUseCase,
        )

        homeScreenViewModel.test(this) {
            val initialState: HomeScreenState = awaitState()
            assertThat(initialState).isInstanceOf(HomeScreenState.Loading::class.java)
            assertThat((initialState as HomeScreenState.Loading).standardQuery).isEqualTo(StandardQuery.standardQueries)
        }
    }

    @Test
    fun `must return Success state with suggestedQueriesPages equals notnull`(): TestResult = runTest {
        getHomeScreenPager4FakeUseCase = GetHomeScreenPager4FakeUseCase()
        getFirstPageKeyFakeUseCase = GetFirstPageKeyFakeUseCase()
        homeScreenViewModel = HomeScreenViewModel(
            dispatchersApi = dispatchersApi,
            getHomeScreenPager4UseCase = getHomeScreenPager4FakeUseCase,
            getFirstPageKeyUseCase = getFirstPageKeyFakeUseCase,
        )

        homeScreenViewModel.test(this) {
            skipItems(1) // skip Loading state
            containerHost.dispatch(HomeScreenIntent.UpdatePages(answer.toSuggestedQueriesPages()))
            expectState(HomeScreenState.Success(answerAfterMapping))
        }
    }

    @Test
    fun `must return ShowSelectedQuery side effect after sent SelectQuery intent`(): TestResult = runTest {
        getHomeScreenPager4FakeUseCase = GetHomeScreenPager4FakeUseCase()
        val fakePageKey = 0L
        getFirstPageKeyFakeUseCase = GetFirstPageKeyFakeUseCase().apply { returnFake(fakePageKey) }
        homeScreenViewModel = HomeScreenViewModel(
            dispatchersApi = dispatchersApi,
            getHomeScreenPager4UseCase = getHomeScreenPager4FakeUseCase,
            getFirstPageKeyUseCase = getFirstPageKeyFakeUseCase,
        )

        homeScreenViewModel.test(this) {
            skipItems(1) // skip Loading state
            containerHost.dispatch(HomeScreenIntent.SelectQuery(PageQuery.DEFAULT, PageFilter.DEFAULT))
            expectSideEffect(HomeScreenSideEffect.ShowPages(pageKey = fakePageKey))
        }
    }
}

private val answer: IPixelsPager4.Answer<PictureWithRelations?> = IPixelsPager4.Answer(
    pages = TreeMap(
        mapOf(
            1 to Page(
                data = listOf(
                    PictureWithRelations(
                        picture = picture("1"),
                        tags = listOf(tag(1), tag(2), tag(3)),
                        colors = listOf(color("1"), color("2"), color("3"))
                    ),
                    PictureWithRelations(
                        picture = picture("2"),
                        tags = listOf(tag(1), tag(2), tag(3)),
                        colors = listOf(color("1"), color("2"), color("3"))
                    ),
                    PictureWithRelations(
                        picture = picture("3"),
                        tags = listOf(tag(1), tag(2), tag(3)),
                        colors = listOf(color("1"), color("2"), color("3"))
                    ),
                ),
                pageState = Page.PageState.Cached,
            ),
        )
    ),
    meta = Meta(
        firstLoadedPage = 1,
        lastLoadedPage = 1,
        firstPage = 1,
        lastPage = 999,
        empty = false,
        nextEnd = false,
        prevEnd = true,
    )
)

private val answerAfterMapping: List<SuggestedQueriesPage> = listOf(
    SuggestedQueriesPage(
        items = listOf(
            SuggestedQuery(
                description = "Tag name",
                previewPath = "",
                pageQuery = PageQuery.KeyWord(word = "tag name"),
                pageFilter = PageFilter.DEFAULT,
            ),
            SuggestedQuery(
                description = "Tag name",
                previewPath = "",
                pageQuery = PageQuery.KeyWord(word = "tag name"),
                pageFilter = PageFilter.DEFAULT,
            ),
            SuggestedQuery(
                description = "Color color name",
                previewPath = "",
                pageQuery = PageQuery.Empty,
                pageFilter = PageFilter.DEFAULT.copy(pictureColor = PageFilter.PictureColor("color name")),
            ),
        )
    )
)

private fun picture(key: String) = Picture(
    key = key,
    url = "",
    shortUrl = "",
    views = 1,
    favorites = 1,
    source = "",
    purity = "",
    categories = "",
    dimensionX = 1,
    dimensionY = 1,
    resolution = "",
    ratio = "",
    fileSize = 1,
    fileType = "",
    createAt = "",
    path = "",
    large = "",
    original = "",
    small = ""
)

private fun tag(key: Int) = Tag(
    id = key,
    name = "tag name",
    alias = "",
    categoryId = 1,
    categoryName = "",
    purity = Tag.TagPurity.SFW,
    createdAt = "",
)

private fun color(key: String) = Color(
    key = key,
    name = "color name",
)