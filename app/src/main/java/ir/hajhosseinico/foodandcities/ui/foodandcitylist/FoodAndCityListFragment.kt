package ir.hajhosseinico.foodandcities.ui.foodandcitylist

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import ir.hajhosseinico.foodandcities.MainActivity
import ir.hajhosseinico.foodandcities.R
import ir.hajhosseinico.foodandcities.databinding.FragmentFoodAndCityListBinding
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.CityNetworkEntity
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.DataState
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodAndCityListNetworkEntity
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodNetworkEntity
import ir.hajhosseinico.foodandcities.util.TopSpacingItemDecoration
import java.io.Serializable

@AndroidEntryPoint
class FoodAndCityListFragment : Fragment(R.layout.fragment_food_and_city_list),
    FoodRecyclerAdapter.Interaction, CityRecyclerAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: FoodAndCityViewModel by viewModels()
    private lateinit var foodListRecyclerAdapter: FoodRecyclerAdapter
    private lateinit var cityListRecyclerAdapter: CityRecyclerAdapter
    private var _binding: FragmentFoodAndCityListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // setting view binding
        _binding = FragmentFoodAndCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setting actionbar
        setActionbar(
            activity as MainActivity,
            getString(R.string.food_and_city_list_fragment_title)
        )
        binding.swipeRefresh.setOnRefreshListener(this)
        // instantiating recyclerView
        initRecyclerView()
        // subscribing observer
        subscribeObservers()
        viewModel.setStateEvent(FoodAndCityListStateEvent.GetFoodAndCity)
    }

    private fun initRecyclerView() {
        binding.foodRecyclerView.apply {
            layoutManager = GridLayoutManager(this@FoodAndCityListFragment.context, 1)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            foodListRecyclerAdapter = FoodRecyclerAdapter(this@FoodAndCityListFragment)
            adapter = foodListRecyclerAdapter
        }

        binding.cityRecyclerView.apply {
            layoutManager = GridLayoutManager(this@FoodAndCityListFragment.context, 1)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            cityListRecyclerAdapter = CityRecyclerAdapter(this@FoodAndCityListFragment)
            adapter = cityListRecyclerAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<FoodAndCityListNetworkEntity> -> {
                    // getting response and handling it
                    binding.txtCityTitle.visibility = View.VISIBLE
                    binding.txtFoodTitle.visibility = View.VISIBLE
                    cityListRecyclerAdapter.submitList(dataState.data.cities)
                    foodListRecyclerAdapter.submitList(dataState.data.foods)

                }
                is DataState.Error -> {
                    // getting error and handling it
                    displayProgressBar(false)
                    displayError(dataState.exception.toString())
                    binding.txtCityTitle.visibility = View.GONE
                    binding.txtFoodTitle.visibility = View.GONE
                }

                is DataState.Loading -> {
                    // show loading
                    displayProgressBar(true)
                }
            }
            displayProgressBar(false)
            binding.swipeRefresh.isRefreshing = false
        })
    }

    private fun displayError(message: String?) {
        Toast.makeText(context, "Internet is not available", Toast.LENGTH_LONG)
            .show()
        Log.d("LOG", message ?: "")
    }

    private fun displayProgressBar(shouldDisplay: Boolean) {
        view?.findViewById<ProgressBar>(R.id.prg_loading)!!.visibility =
            if (shouldDisplay) View.VISIBLE else View.GONE
    }

    private fun setActionbar(activity: MainActivity, title: String) {
        activity.supportActionBar?.show()
        activity.supportActionBar?.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: FoodNetworkEntity) {
        val bundle = Bundle()
        bundle.putSerializable("food", item as Serializable)
        findNavController().navigate(R.id.action_foodAndCityListFragment_to_foodAndCityDetailFragment, bundle)
    }

    override fun onItemSelected(position: Int, item: CityNetworkEntity) {
        val bundle = Bundle()
        bundle.putSerializable("city", item as Serializable)
        findNavController().navigate(R.id.action_foodAndCityListFragment_to_foodAndCityDetailFragment, bundle)
    }

    override fun onRefresh() {
        viewModel.setStateEvent(FoodAndCityListStateEvent.GetFoodAndCity)
    }
}
