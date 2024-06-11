package com.example.movieviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel
    private lateinit var mockWebServer: MockWebServer
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        viewModel = MovieViewModel(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testFetchMovies_success() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("[{\"id\":1,\"title\":\"Movie 1\",\"genre\":\"Action\",\"posterUrl\":\"url1\",\"year\":\"2020\",\"rated\":\"PG\",\"plot\":\"Plot 1\",\"actors\":\"Actor 1\",\"awards\":\"Award 1\",\"writer\":\"Writer 1\",\"imdbID\":\"imdb1\",\"country\":\"Country 1\",\"runtime\":\"120 min\",\"director\":\"Director 1\",\"language\":\"English\",\"released\":\"2020\",\"metascore\":\"80\",\"imdbVotes\":\"1000\",\"imdbRating\":\"8.0\"}]")
        mockWebServer.enqueue(mockResponse)

        viewModel.fetchMovies()

        // verify the results after fetching movies
        assertEquals(1, viewModel.movies.value.size)
        assertEquals("Movie 1", viewModel.movies.value[0].title)
    }

    @Test
    fun testOnSearchQueryChanged() = runBlocking {
        val movies = listOf(
            Movie(1, "Movie 1", "2020", "Action", "PG", "Plot 1", "Actor 1", "Award 1", "url1", "Writer 1", "imdb1", "Country 1", "120 min", "Director 1", "English", "2020", "80", "1000", "8.0"),
            Movie(2, "Another Movie", "2021", "Drama", "R", "Plot 2", "Actor 2", "Award 2", "url2", "Writer 2", "imdb2", "Country 2", "90 min", "Director 2", "French", "2021", "70", "500", "7.0")
        )
        viewModel._movies.value = movies
        viewModel.onSearchQueryChanged("Another")

        // verify results after tweaking the search query
        assertEquals(1, viewModel.filteredMovies.value.size)
        assertEquals("Another Movie", viewModel.filteredMovies.value[0].title)
    }
}
