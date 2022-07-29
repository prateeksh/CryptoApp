package company.project.cryptoapp.cryptoDetailActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import company.project.cryptoapp.cryptoActivity.viewmodel.CryptoDataRepository
import company.project.cryptoapp.model.CryptoList

class CryptoDetailViewModel: ViewModel(){

    var mCryptoDetail : MutableLiveData<CryptoList>?= null
    private lateinit var mRepository: CryptoDetailRepository

    fun init(param: String) {
        if (mCryptoDetail != null) {
            mCryptoDetail
        } else mCryptoDetail = MutableLiveData()

        mRepository = CryptoDetailRepository().getRepoInstance()
        mCryptoDetail = mRepository.getCryptoDetails(param)
    }
}