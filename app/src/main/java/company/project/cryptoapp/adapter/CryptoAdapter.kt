package company.project.cryptoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import company.project.cryptoapp.R
import company.project.cryptoapp.model.CryptoList

class CryptoAdapter (private val coinsList: List<CryptoList>): RecyclerView.Adapter<CryptoAdapter.CoinsViewHolder>() {

    var onItemClick: ((CryptoList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crypto_view_design, parent, false)
        Log.e("CoinsAdapter ", "adapter called: " )
        return CoinsViewHolder(view)

    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

        //val image = dataList.get(position).pictures?.front?.url


        /*if (image != null){

            Glide
                .with(holder.itemView.context)
                .load(image)
                .into(holder.image)
        }
*/
        if (coinsList[position].message == null ) {
            holder.highPrice.text = coinsList[position].highPrice
            holder.lowPrice.text = coinsList[position].lowPrice
            holder.bidPrice.text = coinsList[position].bidPrice
            holder.name.text = coinsList[position].baseAsset
        }else{
            Log.e("CoinsAdapter ", "adapter called:  " + coinsList[position].message )
            holder.errorMessage.text = coinsList[position].message
        }
    }

    override fun getItemCount(): Int {

        return coinsList.size
    }

    inner class CoinsViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){

        val name: TextView = itemView.findViewById(R.id.name)
        val errorMessage: TextView = itemView.findViewById(R.id.error_view)
        val highPrice: TextView = itemView.findViewById(R.id.crypto_high_price)
        val lowPrice: TextView = itemView.findViewById(R.id.crypto_low_price)
        val bidPrice: TextView = itemView.findViewById(R.id.bid_price)
        //val image: ImageView = itemView.findViewById(R.id.crypto_image)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(coinsList[adapterPosition])
            }
        }
    }

}