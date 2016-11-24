package com.livin.vedapadakam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i302913 on 5/8/16.
 */
public class MassFragement extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mass_main, container, false);

        Spinner spinner1 = (Spinner)rootView.findViewById(R.id.spinner1);

        List<String > categories1 = new ArrayList<String>();
        categories1.add("I Confess");
        categories1.add("Have Mercy on Us, O Lord");
        categories1.add("You were sent to Heal");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinneritem);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();


                //Toast.makeText(parent.getContext(), parent.getId() + " Selected: " + item, Toast.LENGTH_LONG).show();

                LinearLayout container = (LinearLayout) getActivity().findViewById(R.id.container1);

                container.removeAllViews();

                int layoutid = R.layout.pentential_act1;

                switch (position) {
                    case 0:
                        layoutid = R.layout.pentential_act1;
                        break;
                    case 1:
                        layoutid = R.layout.pentential_act2;
                        break;
                    case 2:
                        layoutid = R.layout.pentential_act3;
                        break;
                }

                View layout = getActivity().getLayoutInflater().inflate(layoutid, container, false);
                container.addView(layout);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2 = (Spinner)rootView.findViewById(R.id.spinner2);

        List<String > categories2 = new ArrayList<String>();
        categories2.add("സതോത്രയാഗ പ്രാര്‍ത്ഥന 1");
        categories2.add("സതോത്രയാഗ പ്രാര്‍ത്ഥന 2");
        categories2.add("സതോത്രയാഗ പ്രാര്‍ത്ഥന 3");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(R.layout.spinneritem);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();


                //Toast.makeText(parent.getContext(), parent.getId() + " Selected: " + item, Toast.LENGTH_LONG).show();

                LinearLayout container = (LinearLayout) getActivity().findViewById(R.id.container2);

                container.removeAllViews();

                int layoutid = R.layout.canon3;

                switch (position) {
                    case 0:
                        layoutid = R.layout.canon1;
                        break;
                    case 1:
                        layoutid = R.layout.canon2;
                        break;
                    case 2:
                        layoutid = R.layout.canon3;
                        break;
                }

                View layout = getActivity().getLayoutInflater().inflate(layoutid, container, false);
                container.addView(layout);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setSelection(2);

        return rootView;
    }

}
