
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.data.model.ScheduleData
import com.example.fly_app.usecases.GetScheduleListByIataCodeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ScheduleViewModel : ViewModel() {
    private val _scheduleDataList = MutableStateFlow<List<ScheduleData>>(emptyList())
    val scheduleDataList: StateFlow<List<ScheduleData>> = _scheduleDataList

    private val _isLoading = MutableStateFlow(false)

    private val getScheduleListByIataCodeUseCase = GetScheduleListByIataCodeUseCase()
    private val ioDispatcher = Dispatchers.IO

    fun fetchScheduleData(iataCode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val scheduleData = withContext(ioDispatcher) {
                    getScheduleListByIataCodeUseCase.execute(iataCode)
                }
                _scheduleDataList.value = scheduleData

                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
}





