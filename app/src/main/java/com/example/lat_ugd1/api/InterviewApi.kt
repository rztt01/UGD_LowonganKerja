package com.example.lat_ugd1.api

class InterviewApi {
    companion object {
        val BASE_URL = "https://tubespbpjob.herokuapp.com/api/"

        val GET_ALL_URL = BASE_URL + "interview"
        val GET_BY_ID_URL = BASE_URL + "interview/"
        val ADD_URL = BASE_URL + "interview"
        val UPDATE_URL = BASE_URL + "interview/"
        val DELETE_URL = BASE_URL + "interview/"
    }
}