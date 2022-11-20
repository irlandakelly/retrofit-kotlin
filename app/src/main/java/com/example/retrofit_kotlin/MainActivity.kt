package com.example.retrofit_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.example.retrofit_kotlin.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.retrofit_kotlin.network.GetDataService
import com.example.retrofit_kotlin.model.Alumno
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var mJsonTxtView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJsonTxtView = findViewById(R.id.JsonText)
        alumnos
    }

    private val alumnos: Unit
        private get() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.100.88:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val getDataService = retrofit.create(GetDataService::class.java)
            val call = getDataService.alumnos
            call?.enqueue(object : Callback<List<Alumno?>?> {
                override fun onResponse(
                    call: Call<List<Alumno?>?>,
                    response: Response<List<Alumno?>?>
                ) {
                    if (!response.isSuccessful) {
                        mJsonTxtView!!.text = "Código: " + response.code()
                    }
                    val alumnosList = response.body()!!
                    for (alumno in alumnosList) {
                        var content = ""
                        content += """
                        Nombre: ${alumno?.nombre}
                        
                        """.trimIndent()
                        content += """
                        Matrícula: ${alumno?.matricula}
                        
                        """.trimIndent()
                        content += """
                        Correo: ${alumno?.correo}
                        
                        """.trimIndent()
                        content += """Telefono: ${alumno?.telefono}
 
"""
                        mJsonTxtView!!.append(content)
                    }
                }

                override fun onFailure(call: Call<List<Alumno?>?>, t: Throwable) {
                    mJsonTxtView!!.text = t.message
                }
            })
        }
}
