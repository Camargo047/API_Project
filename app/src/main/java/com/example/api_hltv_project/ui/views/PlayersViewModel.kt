package com.example.api_hltv_project.ui.views


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_hltv_project.data.Player
import com.example.api_hltv_project.network.HltvApi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PlayersViewModel: ViewModel() {

    private var _uiState: MutableStateFlow<PlayersUiState> = MutableStateFlow(PlayersUiState.Loading)
    val uiState: StateFlow<PlayersUiState> = _uiState.asStateFlow()

    init {
        getPlayers()
    }

    private fun getPlayers(){
        viewModelScope.launch {
            try {
                _uiState.value = PlayersUiState.Success(HltvApi.retrofitService.getPlayers())
            }catch (e: IOException){
                PlayersUiState.Error
            }catch (e: HttpException){
                PlayersUiState.Error
            }
        }
    }

}

sealed interface PlayersUiState{
    object Loading : PlayersUiState
    data class Success (val player:  List<Player>) : PlayersUiState
    object Error : PlayersUiState
}