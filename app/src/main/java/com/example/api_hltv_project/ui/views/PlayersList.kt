package com.example.api_hltv_project.ui.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.api_hltv_project.R
import com.example.api_hltv_project.data.Player
import com.example.api_hltv_project.network.BASE_URL


@Composable
fun PlayerListScreen(
    playersViewModel: PlayersViewModel = viewModel()
) {
    val uiState by playersViewModel.uiState.collectAsState()
    when(uiState){
        is PlayersUiState.Loading -> LoadingScreen()
        is PlayersUiState.Success -> PlayerList(players = (uiState as PlayersUiState.Success).player)
        is PlayersUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {

}

@Composable
fun ErrorScreen() {

}


@Composable
fun PlayerList(
    players: List<Player>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        items(players) { player ->
            PlayerEntry(player = player)
        }
    }
}

@Composable
fun PlayerEntry(
    player: Player
) {
    val density = LocalDensity.current.density
    val width = remember {mutableStateOf(0F)}
    val height = remember {mutableStateOf(0F)}

    Card(
        modifier = Modifier.padding(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BASE_URL+player.logo)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.csplaceholder),
                contentDescription = player.logo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    }
            )
            Box(modifier = Modifier
                .size(width = width.value.dp, height = height.value.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black),
                        100F,
                        500F,
                    )
                )
            )
            Text(
                text = player.name,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                )
            )
        }
    }
}