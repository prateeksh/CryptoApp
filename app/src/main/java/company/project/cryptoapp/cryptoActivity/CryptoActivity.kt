package company.project.cryptoapp.cryptoActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import company.project.cryptoapp.cryptoDetailActivity.CryptoDetailActivity
import company.project.cryptoapp.R
import company.project.cryptoapp.adapter.CryptoAdapter
import company.project.cryptoapp.cryptoActivity.viewmodel.CryptoViewModel
import company.project.cryptoapp.model.CryptoList
import company.project.cryptoapp.utils.NetworkUtils

class CryptoActivity : AppCompatActivity() {

    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var adapter: CryptoAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var constraint: ConstraintLayout
    lateinit var noInternet: ImageView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        progressBar = findViewById(R.id.main_progress_bar)
        constraint = findViewById(R.id.recycler_constraint)
        noInternet = findViewById(R.id.no_internet)
        swipeRefreshLayout = findViewById(R.id.main_refresh_layout)


        noInternet.isVisible = false
        constraint.isVisible = false
        progressBar.isVisible = true

        cryptoViewModel =
            ViewModelProvider(this).get(CryptoViewModel::class.java)


        if (NetworkUtils.isInternetAvailable(this)) {
            Log.e("Fragment", "onViewCreated: network connected", )
            cryptoViewModel.init()
            updateViews()
        }else{
            constraint.isVisible = false
            progressBar.isVisible = false
            noInternet.isVisible = true

            Log.e("Fragment", "onViewCreated: network not connected", )
        }

        swipeRefreshLayout.setOnRefreshListener {
            if (NetworkUtils.isInternetAvailable(this)) {
                cryptoViewModel.init()
                updateViews()
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

    private fun updateViews(){
        if (cryptoViewModel.mCryptoList != null) {

            constraint.isVisible = true
            progressBar.isVisible = false

            cryptoViewModel.mCryptoList!!.observe(this, Observer<List<CryptoList>> {
                adapter = CryptoAdapter(cryptoViewModel.mCryptoList!!.value!!)
                recyclerView.adapter = adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)

                adapter.onItemClick = { cryptoList ->

                    val intent = Intent(this, CryptoDetailActivity::class.java)
                    intent.putExtra("SYM", cryptoList.symbol)

                    startActivity(intent)


                    cryptoList.symbol?.let { Log.e("onCreate: ItemClicked ", it) }

                }

            })
        }else{
            Log.e("Home Fragment", "coins data is null" )
        }

    }

}