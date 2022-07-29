package company.project.cryptoapp.cryptoDetailActivity

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import company.project.cryptoapp.R
import java.text.SimpleDateFormat
import java.util.*

class CryptoDetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: CryptoDetailViewModel
    private lateinit var coin: TextView
    private lateinit var lowPrice: TextView
    private lateinit var highPrice: TextView
    private lateinit var volume: TextView
    private lateinit var priceToday: TextView
    private lateinit var lastOpen: TextView
    private lateinit var date: TextView
    private lateinit var sellPrice: TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar
    lateinit var constraint: ConstraintLayout
    lateinit var noInternet: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_detail)

        coin = findViewById(R.id.coin)
        lowPrice = findViewById(R.id.low_price)
        highPrice = findViewById(R.id.high_price)
        volume = findViewById(R.id.volume)
        priceToday = findViewById(R.id.price_today)
        lastOpen = findViewById(R.id.last_open)
        date = findViewById(R.id.time_stamp)
        sellPrice = findViewById(R.id.ask_price)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh)
        progressBar = findViewById(R.id.progressBar)
        constraint = findViewById(R.id.constraint_view)
        noInternet = findViewById(R.id.no_internet)

        detailViewModel =
            ViewModelProvider(this).get(CryptoDetailViewModel::class.java)

        val ss: String = intent.getStringExtra("SYM").toString()

        constraint.isVisible = false
        progressBar.isVisible = true
        noInternet.isVisible = false

        if (this.isConnectedToNetwork()) {
            constraint.isVisible = true
            progressBar.isVisible = false
            detailViewModel.init(ss)
            updateView()
        }else{
            constraint.isVisible = false
            progressBar.isVisible = false
            noInternet.isVisible = true
        }

        swipeRefreshLayout.setOnRefreshListener {
            if (this.isConnectedToNetwork()) {
                detailViewModel.init(ss)
                updateView()
                swipeRefreshLayout.isRefreshing = false
                noInternet.isVisible = false
            }else{
                constraint.isVisible = false
                progressBar.isVisible = false
                noInternet.isVisible = true
                swipeRefreshLayout.isRefreshing = false
            }
        }


    }

    private fun updateView(){
        if (detailViewModel.mCryptoDetail != null) {
            constraint.isVisible = true
            progressBar.isVisible = false
            detailViewModel.mCryptoDetail!!.observe(this, Observer {

                coin.text = detailViewModel.mCryptoDetail!!.value!!.symbol.toString()
                lowPrice.text = detailViewModel.mCryptoDetail!!.value!!.lowPrice.toString()
                highPrice.text = detailViewModel.mCryptoDetail!!.value!!.highPrice.toString()
                volume.text = detailViewModel.mCryptoDetail!!.value!!.volume.toString()
                priceToday.text = detailViewModel.mCryptoDetail!!.value!!.openPrice.toString()
                lastOpen.text = detailViewModel.mCryptoDetail!!.value!!.lastPrice.toString()
                sellPrice.text = detailViewModel.mCryptoDetail!!.value!!.askPrice.toString()
                val mTime: String = dateFormatter(detailViewModel.mCryptoDetail!!.value!!.at!!)
                date.text = mTime
            })
        }
    }

    @Suppress("SimpleDateFormat")
    fun dateFormatter(milliseconds: Long): String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(milliseconds)).toString()
    }


    fun Context.isConnectedToNetwork(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}