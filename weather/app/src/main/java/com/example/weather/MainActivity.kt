package com.example.weather

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etCity: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvWeatherResult: TextView
    private lateinit var ivWeatherIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCity = findViewById(R.id.etCity)
        btnSearch = findViewById(R.id.btnSearch)
        tvWeatherResult = findViewById(R.id.tvWeatherResult)
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon)

        btnSearch.setOnClickListener {
            val city = etCity.text.toString().trim()
            if (city.isNotEmpty()) {
                getWeather(city)
            } else {
                Toast.makeText(this, "Vui lòng nhập tên thành phố", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeather(city: String) {
        val apiKey = "ecb42934c6270688d4f20dd59bf78ac4"
        val call = RetrofitClient.weatherService.getWeather(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val temp = it.main.temp
                        val description = it.weather[0].description.capitalize(Locale.ROOT)
                        val iconUrl = "https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"

                        tvWeatherResult.text = "Nhiệt độ: $temp°C\nMiêu tả: $description"
                        Glide.with(this@MainActivity)
                            .load(iconUrl)
                            .into(ivWeatherIcon)
                    }
                }
                else {
                    tvWeatherResult.text = "Không tìm thấy thành phố"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                tvWeatherResult.text = "Có lỗi xảy ra: ${t.localizedMessage}"
            }
        })
    }
}
