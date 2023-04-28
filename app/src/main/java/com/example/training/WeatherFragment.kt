package com.example.training

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.training.Interfaces.WeatherService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class WeatherFragment : Fragment() {
    private lateinit var tempView: TextView
    private lateinit var humidityView: TextView
    private lateinit var weatherView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        val apikey = "8cf634b5e0223c582f41a7683d98353e"
        var coordinates = getCurrentLocation()

        tempView = view.findViewById(R.id.temperature)
        humidityView = view.findViewById(R.id.humidity)
        weatherView = view.findViewById(R.id.weather)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//        val service: WeatherService = retrofit
//            .create<WeatherService>(WeatherService::class.java)
//
//        val listCall: Call<WeatherResponse> = service.getCurrentWeather(
//            coordinates.first, coordinates.second, "metric", apikey
//        )

//        listCall.enqueue(object : Callback<WeatherResponse>?, retrofit2 : Retrofit){
//            override fun OnFailure(t: Throwable){
//
//            }
//
//            override fun onResponse(response: Response<WeatherResponse>){
//
//            }
//        }

        val openWeatherMapApi = retrofit.create(WeatherService::class.java)
        openWeatherMapApi.getCurrentWeather(coordinates.first, coordinates.second, "metric", apikey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val weatherResponse = response.body()
                val temperature = weatherResponse?.main?.temp
                val humidity = weatherResponse?.main?.humidity
                val weatherType = weatherResponse?.weather?.firstOrNull()?.main

                Log.d("TAG", "temp: $temperature")
                Log.d("TAG", "hum: $humidity")
                Log.d("TAG", "wea: $weatherType")


                tempView.text = temperature
                humidityView.text = humidity
                weatherView.text = weatherType
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        })

        return view
    }
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Pair<Double?, Double?> {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            val locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            val latitude = location?.latitude
            val longitude = location?.longitude

            return Pair(latitude, longitude)        }
        else {
            val locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            Log.d("TAG", "Location: $location")

            val latitude = location?.latitude
            val longitude = location?.longitude


            Log.d("TAG", "Latitude: $latitude")
            Log.d("TAG", "Longitude: $longitude")


            return Pair(latitude, longitude)
        }
    }





}