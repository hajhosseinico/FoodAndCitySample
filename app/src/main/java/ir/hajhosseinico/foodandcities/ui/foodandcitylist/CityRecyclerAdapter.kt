package ir.hajhosseinico.foodandcities.ui.foodandcitylist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.hajhosseinico.foodandcities.R
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.CityNetworkEntity
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodNetworkEntity

class CityRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CityNetworkEntity>() {

        override fun areItemsTheSame(
            oldItem: CityNetworkEntity,
            newItem: CityNetworkEntity
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: CityNetworkEntity,
            newItem: CityNetworkEntity
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviePostViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_food_and_city_item,
                parent,
                false
            ),
            interaction

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is MoviePostViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CityNetworkEntity>) {
        differ.submitList(list)
    }

    class MoviePostViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityNetworkEntity) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            // need to shrink images b/c they are very high resolution
            val requestOptions = RequestOptions
                .overrideOf(1920, 1080)
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(item.image)
                .into(itemView.findViewById(R.id.imgImage))
//                .placeholder(R.drawable.ic_placeholder)

            itemView.findViewById<TextView>(R.id.txtTitle).text = item.name
            itemView.findViewById<TextView>(R.id.txtDescription).text = item.description
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CityNetworkEntity)
    }
}


