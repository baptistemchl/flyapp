
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.data.model.AircraftData
import com.example.fly_app.usecases.GetAircraftDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class AircraftViewModel : ViewModel() {
    private val _aircraftInfo = MutableStateFlow<AircraftData?>(null)
    val aircraftInfo: MutableStateFlow<AircraftData?> = _aircraftInfo
    private val _isLoading = MutableStateFlow(false)
    private val getAircraftInfoUseCase = GetAircraftDetailUseCase()
    private val ioDispatcher = Dispatchers.IO


    fun fetchAircraftInfo(icao24: String) {
        viewModelScope.launch {
            try {
                val aircraftInfo = withContext(ioDispatcher) {
                    getAircraftInfoUseCase.execute(icao24)
                }

                _aircraftInfo.value = aircraftInfo
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



