package company.project.cryptoapp.api

import company.project.cryptoapp.model.CryptoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("tickers/24hr")
    fun getCoins(): Call<List<CryptoList>>

    @GET("ticker/24hr")
    fun getCryptoDetails(@Query("symbol") symbol: String ): Call<CryptoList>

}