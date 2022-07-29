package company.project.cryptoapp.cryptoDetailActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import company.project.cryptoapp.api.RetrofitBuilder
import company.project.cryptoapp.model.CryptoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoDetailRepository {

    private var dataset: MutableLiveData<CryptoList> = MutableLiveData()

    private var instance: CryptoDetailRepository?= null

    fun getRepoInstance() : CryptoDetailRepository {
        if (instance == null) instance = CryptoDetailRepository()
        return instance!!
    }

    fun getCryptoDetails(param: String): MutableLiveData<CryptoList> {

        Log.e("Detail Repository Class", "getCoinsData: method called ", )
        val retrofitCall = RetrofitBuilder.apiService.getCryptoDetails(param)

        Log.e("Detail Repository Class", retrofitCall.request().url().toString() )
        retrofitCall.enqueue(object : Callback<CryptoList> {
            override fun onResponse(call: Call<CryptoList>, response: Response<CryptoList>) {
                dataset.value = response.body()!!
                Log.e("RetrofitCall", response.body().toString())
            }

            override fun onFailure(call: Call<CryptoList>, t: Throwable) {
                Log.e("RetrofitCall", t.toString()  )
            }


        })
        return dataset
    }
}