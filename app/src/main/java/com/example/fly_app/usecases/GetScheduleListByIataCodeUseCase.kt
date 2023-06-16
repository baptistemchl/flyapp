package com.example.fly_app.usecases

import com.example.fly_app.BuildConfig
import com.example.fly_app.data.model.ScheduleData
import com.example.fly_app.data.model.SchedulesListData
import com.example.fly_app.repositories.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetScheduleListByIataCodeUseCase {
    private val scheduleRepository = ScheduleRepository()


    suspend fun execute(iataCode: String): List<ScheduleData> {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = BuildConfig.AIRPORT_API_KEY
                val schedulesList = scheduleRepository.getSchedulesByIataCode(apiKey, iataCode)
                convertToScheduleDataList(schedulesList)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }


    private fun convertToScheduleDataList(schedulesList: SchedulesListData?): List<ScheduleData> {
        return schedulesList?.response?.map { schedule ->
            ScheduleData(
                flight_iata = schedule.flight_iata,
                flight_icao = schedule.flight_icao,
                dep_iata = schedule.dep_iata,
                dep_icao = schedule.dep_icao,
                arr_iata = schedule.arr_iata,
                arr_icao = schedule.arr_icao
            )
        } ?: emptyList()
    }
}
