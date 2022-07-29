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

        val retrofitCall = RetrofitBuilder.apiService.getCryptoDetails(param)


        retrofitCall.enqueue(object : Callback<CryptoList> {
            override fun onResponse(call: Call<CryptoList>, response: Response<CryptoList>) {
                dataset.value = response.body()!!

            }

            override fun onFailure(call: Call<CryptoList>, t: Throwable) {
            }


        })
        return dataset
    }
}