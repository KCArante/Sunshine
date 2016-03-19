package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SARJ on 3/15/2016.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private String mForecastStr;

    TextView mDetailText;
    TextView mDescription;
    ImageView mImageIcon;
    TextView mHighTemp;
    TextView mLowTemp;
    String[] temp = new String[5];


    public DetailFragment(){
        setHasOptionsMenu(true);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            mDetailText = (TextView) rootView.findViewById(R.id.detail_text);
            mDescription = (TextView) rootView.findViewById(R.id.description);
            mImageIcon = (ImageView) rootView.findViewById(R.id.image_icon);
            mHighTemp = (TextView) rootView.findViewById(R.id.high_temp);
            mLowTemp = (TextView) rootView.findViewById(R.id.low_temp);

            if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                temp = mForecastStr.split("-");
                mDetailText.setText(temp[0]);
                mDescription.setText(temp[1]);
                temp = temp[2].split("/");
                mHighTemp.setText(temp[0]);
                mLowTemp.setText(temp[1]);

            }
            else{
                Log.d(LOG_TAG, "Intent is null");
            }
            return rootView;
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if(mShareActionProvider != null){
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }

    private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
}
