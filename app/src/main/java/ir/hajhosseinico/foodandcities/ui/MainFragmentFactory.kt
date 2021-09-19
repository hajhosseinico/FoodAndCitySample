package ir.hajhosseinico.foodandcities.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ir.hajhosseinico.foodandcities.ui.foodandcitylist.FoodAndCityListFragment
import javax.inject.Inject

/**
 * MainFragmentFactory
 */

class MainFragmentFactory
@Inject
constructor() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return  when(className){
            FoodAndCityListFragment::class.java.name ->{
                FoodAndCityListFragment()
            }
//            MovieDetailFragment::class.java.name ->{
//                MovieDetailFragment()
//            }
//            SplashFragment::class.java.name ->{
//                SplashFragment()
//            }
            else -> super.instantiate(classLoader, className)
        }
    }
}
