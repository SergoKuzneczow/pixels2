package com.sergokuzneczow.main_menu.impl.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreenState
import com.sergokuzneczow.main_menu.impl.navigation.mainMenuGraph

private const val ANIMATION_DURATION: Int = 900

@Composable
internal fun MainMenuNavHost(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    rootScreenState: MainMenuRootScreenState,
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = rootScreenState.navController,
        startDestination = rootScreenState.startDestination,
        modifier = modifier,
        enterTransition = {
            slideIn(tween(ANIMATION_DURATION, easing = FastOutSlowInEasing)) { fullSize ->
                IntOffset(fullSize.width, 100)
            } + fadeIn(animationSpec = tween(durationMillis = ANIMATION_DURATION))
        },
        exitTransition = {
            slideOut(tween(ANIMATION_DURATION, easing = FastOutSlowInEasing)) { fullSize ->
                IntOffset(-1 * fullSize.width, 100)
            } + fadeOut(animationSpec = tween(durationMillis = ANIMATION_DURATION))
        },
        popEnterTransition = {
            slideIn(tween(ANIMATION_DURATION, easing = FastOutSlowInEasing)) { fullSize ->
                IntOffset(-1 * fullSize.width, 100)
            } + fadeIn(animationSpec = tween(durationMillis = ANIMATION_DURATION))
        },
        popExitTransition = {
            slideOut(tween(ANIMATION_DURATION, easing = FastOutSlowInEasing)) { fullSize ->
                IntOffset(fullSize.width, 100)
            } + fadeOut(animationSpec = tween(durationMillis = ANIMATION_DURATION))
        },
    ) {
        this.mainMenuGraph(
            onShowSnackbar = onShowSnackbar,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
            titleTextState = titleTextState,
        )
    }
}