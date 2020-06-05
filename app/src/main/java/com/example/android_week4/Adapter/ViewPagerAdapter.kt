package com.example.android_week4.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.android_week4.Fragment.MyFavorite
import com.example.android_week4.Fragment.NowPlaying
import com.example.android_week4.Fragment.TopRating

class ViewPagerAdapter (fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->{
                NowPlaying()
            }
            1 ->{
                TopRating()
            }
            else ->{
                return MyFavorite()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}