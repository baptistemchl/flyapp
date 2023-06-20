package com.example.fly_app.usecases

import com.example.fly_app.BuildConfig
import com.example.fly_app.data.model.ScheduleData
import com.example.fly_app.data.model.SchedulesListData
import com.example.fly_app.repositories.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetScheduleListByDepIataCodeUseCase {
    private val scheduleRepository = ScheduleRepository()


    suspend fun execute(iataCode: String): List<ScheduleData> {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = BuildConfig.AIRPORT_API_KEY
                val schedulesList = scheduleRepository.getSchedulesByDepIataCode(apiKey, iataCode)
                convertToScheduleDataList(schedulesList)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }


    private fun convertToScheduleDataList(schedulesList: SchedulesListData?): List<ScheduleData> {
        return schedulesList?.response?:emptyList()
    }
}
