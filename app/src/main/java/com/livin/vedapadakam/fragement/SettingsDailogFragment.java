package com.livin.vedapadakam.fragement;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.livin.vedapadakam.MainActivity;
import com.livin.vedapadakam.R;
import com.livin.vedapadakam.ReadingFragment;

/**
 * Created by Livin D'cruz on 5/1/16.
 */
public class SettingsDailogFragment extends DialogFragment {

        public static int textsizeadder;
        public static int change = 0;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(change == 1) {
            //getActivity().recreate();
            Fragment currentView = ((MainActivity) getActivity()).getCurrentFragment();
            if (((MainActivity) getActivity()).getCurrentTabIndex() == 0){
                ((ReadingFragment)currentView).updateTextSize(textsizeadder);
            }
        }

    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            change = 0;
            View rootView = inflater.inflate(R.layout.settingfragment, container,
                    false);
            getDialog().setTitle("Settings");

            final SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            textsizeadder = sharedPref.getInt(getString(R.string.text_size), 3);

            final TextView txtsize = (TextView) rootView.findViewById(R.id.rangetext);
            txtsize.setText(""+(textsizeadder+10));
            // Do something else
            SeekBar bar = (SeekBar) rootView.findViewById(R.id.seekbar);
            bar.setProgress(textsizeadder);
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getString(R.string.text_size), progress);
                    editor.commit();
                    textsizeadder = progress;
                    txtsize.setText(""+(progress+10));
                    change=1;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            return rootView;
        }

}
