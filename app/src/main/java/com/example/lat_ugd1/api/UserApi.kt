package com.example.lat_ugd1.api

class UserApi {
    companion object {
        val BASE_URL = "http://192.168.68.180:8081/ci4-tubesapiserver/public/"

        val GET_ALL_URL = BASE_URL + "user/"
        val GET_BY_ID_URL = BASE_URL + "user/"
        val ADD_URL = BASE_URL + "user"
        val UPDATE_URL = BASE_URL + "user/"
        val DELETE_URL = BASE_URL + "user/"
    }
}