package com.task_baham.ui.composable.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.task_baham.ui.activities.MainActivity
import com.task_baham.ui.composable.image.ImageScreen
import com.task_baham.ui.composable.player.PlayerScreen
import com.task_baham.ui.composable.universal.DisplayProgressBar
import com.task_baham.ui.composable.universal.DisplayTextAboveList
import com.task_baham.util.GridItemSpan
import com.task_baham.util.isVideo
import com.task_baham.viewModel.home.HomeViewModel
import java.io.File


@Composable
fun HomeScreen(
    mainActivity: MainActivity,
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val shouldDisplayImage = remember { mutableStateOf(false) }
    val shouldDisplayVideo = remember { mutableStateOf(false) }
    val selectedFile = remember { mutableStateOf(File("")) }
    val media = remember { homeViewModel.getMedia() }
    val mediaLazyItems = media.collectAsLazyPagingItems()

    val shouldDisplayProgress = homeViewModel.showProgressBar.collectAsState()
    if (shouldDisplayProgress.value)
        DisplayProgressBar(state = shouldDisplayProgress)


    Column(Modifier.fillMaxSize()) {

        if (shouldDisplayImage.value)
            ImageScreen(file = selectedFile.value)

        if (shouldDisplayVideo.value)
            PlayerScreen(file = selectedFile.value, shouldDisplayVideo = shouldDisplayVideo)


        DisplayTextAboveList()

        LazyVerticalGrid(
            columns = GridCells.Fixed(GridItemSpan),
            modifier = Modifier.fillMaxWidth()
        ) {

            items(
                mediaLazyItems.itemSnapshotList.items.size,
                key = {
                    mediaLazyItems.itemSnapshotList.items[it].name.hashCode()
                }
            ) {
                DisplayThumbs(
                    mediaLazyItems = mediaLazyItems,
                    index = it,
                    appContext = homeViewModel.getAppContext(),
                    onItemClick = { file ->

                        selectedFile.value = file
                        if (file.isVideo())
                            shouldDisplayVideo.value = true
                        else
                            shouldDisplayImage.value = true

                    }
                )

                DisplayLoadingDependOnState(
                    mediaLazyItems = mediaLazyItems,
                    lazyGridScope = this@LazyVerticalGrid
                )
            }


        }
    }

    BackHandler {
        when {
            shouldDisplayImage.value -> {
                shouldDisplayImage.value = false
            }

            shouldDisplayVideo.value -> {
                shouldDisplayVideo.value = false
            }

            else -> finishAffinity(mainActivity)
        }

    }
}




