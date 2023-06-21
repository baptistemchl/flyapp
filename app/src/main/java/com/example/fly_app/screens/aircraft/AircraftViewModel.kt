
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.usecases.GetAircraftDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AircraftViewModel : ViewModel() {
    private val _aircraftInfo = MutableStateFlow<String>("")
    val aircraftInfo: StateFlow<String> = _aircraftInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val getAircraftInfoUseCase = GetAircraftDetailUseCase()
    private val ioDispatcher = Dispatchers.IO

    fun fetchAircraftInfo(icao24: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val aircraftInfo = withContext(ioDispatcher) {
                    getAircraftInfoUseCase.execute(icao24)
                }
                _aircraftInfo.value = aircraftInfo

                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
}


