package com.sergokuzneczow.search_suitable_pictures.impl

internal sealed interface SearchSuitablePicturesScreenState {
    data class SearchField(val searchField: String = "") : SearchSuitablePicturesScreenState
}

internal sealed interface SearchSuitablePicturesScreenIntent {
    data object ShowSearchField : SearchSuitablePicturesScreenIntent
    data class InputSearchField(val searchValue: String) : SearchSuitablePicturesScreenIntent
    data object DoneSearch : SearchSuitablePicturesScreenIntent
}

internal sealed interface SearchSuitablePicturesScreenSideEffect {
    data class SearchRequest(val pageKey: Long) : SearchSuitablePicturesScreenSideEffect
}