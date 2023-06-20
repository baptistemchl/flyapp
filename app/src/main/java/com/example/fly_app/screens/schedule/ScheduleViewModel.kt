
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.data.model.ScheduleData
import com.example.fly_app.usecases.GetScheduleListByArrIataCodeUseCase
import com.example.fly_app.usecases.GetScheduleListByDepIataCodeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ScheduleViewModel : ViewModel() {
    private val _scheduleDataList = MutableStateFlow<List<ScheduleData>>(emptyList())
    private val _scheduleDataListByArr = MutableStateFlow<List<ScheduleData>>(emptyList())
    val scheduleDataList: StateFlow<List<ScheduleData>> = _scheduleDataList
    val scheduleDataListByArr: StateFlow<List<ScheduleData>> = _scheduleDataListByArr

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val getScheduleListByArrIataCodeUseCase = GetScheduleListByArrIataCodeUseCase()
    private val getScheduleListByDepIataCodeUseCase = GetScheduleListByDepIataCodeUseCase()
    private val ioDispatcher = Dispatchers.IO


    fun fetchScheduleData(iataCode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val scheduleData = withContext(ioDispatcher) {
                    getScheduleListByDepIataCodeUseCase.execute(iataCode)
                }
                _scheduleDataList.value = scheduleData

                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }

    fun fetchScheduleDataByArr(iataCode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val scheduleDataByArr = withContext(ioDispatcher) {
                    getScheduleListByArrIataCodeUseCase.execute(iataCode)
                }
                _scheduleDataListByArr.value = scheduleDataByArr

                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
}

