package com.mediaPlayer.link.ui.activity

import androidx.fragment.app.Fragment
import com.mediaPlayer.link.R
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivityMainBinding
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.ui.fragment.*


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    private val trendingFragment: Fragment = TrendingFragment()
    private val hollywoodFragment: Fragment = HollywoodFragment()
    private val bollywoodFragment: Fragment = BollywoodFragment()
    private val southMovieFragment: Fragment = SouthMovieFragment()
    private val webSeriesFragment: Fragment = WebSeriesFragment()

    //    private val fm : FragmentManager = supportFragmentManager
//    private var active = trendingFragment
    private lateinit var mViewModel: MainViewModel

    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.mViewModel = viewModel
    }


    override fun onCreateBase() {

//        val crashButton = Button(this)
//        crashButton.setText("Test Crash")
//        crashButton.setOnClickListener(View.OnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        })
//
//        addContentView(
//            crashButton, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        )

        with(mBinding!!) {
            navigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_trending -> {
//                        setFragment(trendingFragment, "trendingFragment")
                        replaceFragment(trendingFragment)

                    }
                    R.id.navigation_hollywood -> {
//                        setFragment(hollywoodFragment, "hollywoodFragment")
                        replaceFragment(hollywoodFragment)

                    }
                    R.id.navigation_bollywood -> {
//                        setFragment(bollywoodFragment, "bollywoodFragment")
                        replaceFragment(bollywoodFragment)
                    }
                    R.id.navigation_south_movie -> {
//                        setFragment(southMovieFragment, "southMovieFragment")
                        replaceFragment(southMovieFragment)
                    }
                    R.id.navigation_we_series -> {
//                        replaceFragment(webSeriesFragment, "webSeriesFragment")
                        replaceFragment(webSeriesFragment)
                    }
                }
                true
            }
            navigation.selectedItemId = R.id.navigation_trending
        }
    }

    /**
     * Reference by: Stack Overflow
     * URL: https://stackoverflow.com/questions/45130713/bottomnavigationview-how-to-avoid-recreation-of-fragments-and-reuse-them
     * */

//    private fun setFragment(fragment : Fragment, tag : String?) {
//        if (fragment.isAdded) {
//            val fragmentTag = supportFragmentManager.findFragmentByTag(tag)
//            if (fragmentTag != null) {
//                supportFragmentManager.beginTransaction().hide(active).show(fragmentTag).commit()
//            }
//        } else {
//            supportFragmentManager.beginTransaction().add(R.id.container, fragment, tag).commit()
//        }
////        supportFragmentManager.popBackStack()
//        active = fragment
//    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(R.id.container, fragment)
            }

            supportFragmentManager.fragments.forEach {
                if (it != fragment && it.isAdded) {
                    hide(it)
                }
            }
        }.commit()
    }

    override fun observe() {
    }

    override fun onBackPressed() {
        if (mBinding!!.navigation.selectedItemId == R.id.navigation_trending) {
            finishAffinity()
        } else {
            mBinding?.navigation?.selectedItemId = R.id.navigation_trending
        }
    }
}