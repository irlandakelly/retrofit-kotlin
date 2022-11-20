package com.example.retrofit_kotlin.network

import retrofit2.http.GET
import com.example.retrofit_kotlin.model.Alumno
import retrofit2.Call

interface GetDataService {
    @get:GET("todos")
    val alumnos: Call<List<Alumno?>?>?
}