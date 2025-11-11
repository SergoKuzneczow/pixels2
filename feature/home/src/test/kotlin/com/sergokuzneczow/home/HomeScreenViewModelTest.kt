package com.sergokuzneczow.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyFakeUseCase
import com.sergokuzneczow.domain.get_home_screen_pager4_use_case.GetHomeScreenPager4FakeUseCase
import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Meta
import com.sergokuzneczow.domain.pager4.IPixelsPager4.Answer.Page
import com.sergokuzneczow.home.impl.HomeListUiState
import com.sergokuzneczow.home.impl.models.StandardQuery
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage.SuggestedQuery
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModel
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag
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
import java.util.TreeMap

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeScreenViewModelTest() {

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var getHomeScreenPager4FakeUseCase: GetHomeScreenPager4FakeUseCase

    private lateinit var getFirstPageKeyFakeUseCase: GetFirstPageKeyFakeUseCase

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
        getHomeScreenPager4FakeUseCase = GetHomeScreenPager4FakeUseCase()
        getFirstPageKeyFakeUseCase = GetFirstPageKeyFakeUseCase()
        homeScreenViewModel = HomeScreenViewModel(
            getHomeScreenPager4UseCase = getHomeScreenPager4FakeUseCase,
            getFirstPageKeyUseCase = getFirstPageKeyFakeUseCase,
        )
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `start state must be Loading`(): TestResult = runTest {
        homeScreenViewModel.uiState.test {
            val loading: HomeListUiState = awaitItem()
            assertThat(loading).isInstanceOf(HomeListUiState.Loading::class.java)
        }
    }

    @Test
    fun `must return Success state with standardQuery has list and suggestedQueriesPages equals null`(): TestResult = runTest {
        homeScreenViewModel.uiState.test {
            skipItems(1) // skip Loading state

            val successWithoutSuggestedQueriesPages: HomeListUiState = awaitItem()
            assertThat(successWithoutSuggestedQueriesPages).isInstanceOf(HomeListUiState.Success::class.java)
            assertThat((successWithoutSuggestedQueriesPages as HomeListUiState.Success).standardQuery).isEqualTo(StandardQuery.standardQueries)
            assertThat((successWithoutSuggestedQueriesPages as HomeListUiState.Success).suggestedQueriesPages).isEqualTo(null)
        }
    }

    @Test
    fun `must return Success state with suggestedQueriesPages equals notnull`(): TestResult = runTest {
        homeScreenViewModel.uiState.test {
            skipItems(1) // skip Loading state
            skipItems(1) // skip Success state with suggestedQueriesPages equals null

            getHomeScreenPager4FakeUseCase.emitAnswer(answer)

            val successWithoutSuggestedQueriesPages: HomeListUiState = awaitItem()
            assertThat(successWithoutSuggestedQueriesPages).isInstanceOf(HomeListUiState.Success::class.java)
            assertThat((successWithoutSuggestedQueriesPages as HomeListUiState.Success).suggestedQueriesPages).isNotEqualTo(null)
            assertThat(successWithoutSuggestedQueriesPages.suggestedQueriesPages?.get(0)?.items?.get(0)).isEqualTo(answerAfterMapping.get(0).items.get(0))
            assertThat(successWithoutSuggestedQueriesPages.suggestedQueriesPages?.get(0)?.items?.get(1)).isEqualTo(answerAfterMapping.get(0).items.get(1))
            assertThat(successWithoutSuggestedQueriesPages.suggestedQueriesPages?.get(0)?.items?.get(2)).isEqualTo(answerAfterMapping.get(0).items.get(2))
            assertThat(successWithoutSuggestedQueriesPages.suggestedQueriesPages?.get(0)?.items?.get(3)).isEqualTo(answerAfterMapping.get(0).items.get(3))
            assertThat(successWithoutSuggestedQueriesPages.suggestedQueriesPages?.get(0)?.items?.get(4)).isEqualTo(answerAfterMapping.get(0).items.get(4))
            assertThat(successWithoutSuggestedQueriesPages.suggestedQueriesPages?.get(0)?.items?.get(5)).isEqualTo(answerAfterMapping.get(0).items.get(5))
        }
    }
}

private val answerAfterMapping :List<SuggestedQueriesPage> = listOf(
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
                description = "#tag name",
                previewPath = "",
                pageQuery = PageQuery.Tag(
                    tagKey = 1,
                    description = "tag name"
                ),
                pageFilter = PageFilter.DEFAULT,
            ),
        )
    )
)

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
                    PictureWithRelations(
                        picture = picture("4"),
                        tags = listOf(tag(1), tag(2), tag(3)),
                        colors = listOf(color("1"), color("2"), color("3"))
                    ),
                    PictureWithRelations(
                        picture = picture("5"),
                        tags = listOf(tag(1), tag(2), tag(3)),
                        colors = listOf(color("1"), color("2"), color("3"))
                    ),
                    PictureWithRelations(
                        picture = picture("6"),
                        tags = listOf(tag(1), tag(2), tag(3)),
                        colors = listOf(color("1"), color("2"), color("3"))
                    ),
                ),
                pageState = Page.PageState.Cached,
            )
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