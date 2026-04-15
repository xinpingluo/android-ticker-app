package com.aureum.ticker.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aureum.ticker.data.model.TimeRange
import com.aureum.ticker.data.repository.TickerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TickerRepository,
) : ViewModel() {

    private val symbol: String = savedStateHandle.get<String>("symbol") ?: ""

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val detail = repository.getTickerDetail(symbol)
                val history = repository.getPriceHistory(symbol, _uiState.value.selectedRange)
                _uiState.update {
                    it.copy(
                        ticker = detail,
                        priceHistory = history,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun selectTimeRange(range: TimeRange) {
        _uiState.update { it.copy(selectedRange = range) }
        viewModelScope.launch {
            try {
                val history = repository.getPriceHistory(symbol, range)
                _uiState.update { it.copy(priceHistory = history) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
