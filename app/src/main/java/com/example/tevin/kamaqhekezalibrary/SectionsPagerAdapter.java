package com.example.tevin.kamaqhekezalibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
                        return  new BookReview();
                case 2:
                    return  new Suggestion();
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
