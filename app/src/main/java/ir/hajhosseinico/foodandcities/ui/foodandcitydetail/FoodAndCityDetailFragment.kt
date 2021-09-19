package ir.hajhosseinico.foodandcities.ui.foodandcitydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import ir.hajhosseinico.foodandcities.MainActivity
import ir.hajhosseinico.foodandcities.R
import ir.hajhosseinico.foodandcities.databinding.FragmentFoodAndCityDetailBinding
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.CityNetworkEntity
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodNetworkEntity

@AndroidEntryPoint
class FoodAndCityDetailFragment : Fragment(R.layout.fragment_food_and_city_detail) {

    private var _binding: FragmentFoodAndCityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var food: FoodNetworkEntity
    private lateinit var city: CityNetworkEntity
    private lateinit var dataType: DataType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.getSerializable("food").let { t ->
            if (t != null) {
                food = t as FoodNetworkEntity
                dataType = DataType.FOOD
            }
        }
        arguments?.getSerializable("city").let { t ->
            if (t != null) {
                city = t as CityNetworkEntity
                dataType = DataType.CITY
            }
        }


        _binding = FragmentFoodAndCityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setting actionbar
        setActionbar(
            activity as MainActivity,
            getString(R.string.food_and_city_detail_fragment_title)
        )

        setData()
    }

    private fun setData() {
        var image = ""
        var title = ""
        var description = ""
        if (dataType == DataType.FOOD) {
            title = food.name
            image = food.image
        } else if (dataType == DataType.CITY) {
            title = city.name
            description = city.description
            image = city.image
        }

        binding.txtTitle.text = title
        binding.txtDescription.text = description

        val requestOptions = RequestOptions
            .overrideOf(1920, 1080)
        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load(image)
            .into(binding.imgImage)
    }

    private fun setActionbar(activity: MainActivity, title: String) {
        activity.supportActionBar?.show()
        activity.supportActionBar?.title = title
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayUseLogoEnabled(false)
        activity.supportActionBar?.setDisplayShowHomeEnabled(false)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private enum class DataType {
        FOOD, CITY
    }
}
