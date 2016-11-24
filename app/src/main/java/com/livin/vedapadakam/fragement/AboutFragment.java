package com.livin.vedapadakam.fragement;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livin.vedapadakam.R;

/**
 * Created by i302913 on 5/9/16.
 */
public class AboutFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.aboutfragement, container,
                false);
        getDialog().setTitle(R.string.action_about);
        return rootView;
    }
}
