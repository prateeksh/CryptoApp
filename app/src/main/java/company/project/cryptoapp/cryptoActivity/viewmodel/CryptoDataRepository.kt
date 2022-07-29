package company.project.cryptoapp.cryptoActivity.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import company.project.cryptoapp.api.ApiService
import company.project.cryptoapp.api.RetrofitBuilder.apiService
import company.project.cryptoapp.model.CryptoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CryptoDataRepository {

    private var dataset: MutableLiveData<List<CryptoList>> = MutableLiveData()

    private var instance: CryptoDataRepository?= null

    fun getRepoInstance() : CryptoDataRepository {
        if (instance == null) instance = CryptoDataRepository()
        return instance!!
    }

    fun getCoinsData(): MutableLiveData<List<CryptoList>> {

        Log.e("Repository Class", "getCoinsData: method called ", )
        val retrofitCall = apiService.getCoins()

        Log.e("Repository Class", retrofitCall.request().url().toString() )
        retrofitCall.enqueue(object : Callback<List<CryptoList>> {
            override fun onResponse(call: Call<List<CryptoList>>, response: Response<List<CryptoList>>) {
                dataset.value = response.body()!!
                Log.e("RetrofitCall", response.body().toString())
            }

            override fun onFailure(call: Call<List<CryptoList>>, t: Throwable) {
                Log.e("RetrofitCall", t.toString()  )
            }


        })
        return dataset
    }
}