package com.example.tevin.kamaqhekezalibrary.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tevin.kamaqhekezalibrary.Fragment.BookReviewFragment;
import com.example.tevin.kamaqhekezalibrary.Fragment.NewsFeedFragment;
import com.example.tevin.kamaqhekezalibrary.Fragment.SuggestionFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private int numOfFrags;

        public SectionsPagerAdapter(FragmentManager fm, int numOfFrags) {
            super(fm);
            this.numOfFrags=numOfFrags;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    return new NewsFeedFragment();
                    case 1:
                        return  new BookReviewFragment();
                case 2:
                    return  new SuggestionFragment();
                    default:
                        return new NewsFeedFragment();
            }
        }

        @Override
        public int getCount() {
            // Show available pages
            return numOfFrags;
        }
}
