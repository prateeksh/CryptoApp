package company.project.cryptoapp.cryptoActivity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import company.project.cryptoapp.cryptoActivity.viewmodel.CryptoDataRepository
import company.project.cryptoapp.model.CryptoList

class CryptoViewModel : ViewModel() {

    var mCryptoList : MutableLiveData<List<CryptoList>> ?= null
    private lateinit var mRepository: CryptoDataRepository

    fun init() {
        if (mCryptoList != null) {
            mCryptoList
        } else mCryptoList = MutableLiveData()

        mRepository = CryptoDataRepository().getRepoInstance()
        mCryptoList = mRepository.getCoinsData()
    }
}