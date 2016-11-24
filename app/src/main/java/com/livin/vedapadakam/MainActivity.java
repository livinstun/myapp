package com.livin.vedapadakam;



import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livin.vedapadakam.customlayout.SlidingTabLayout;
import com.livin.vedapadakam.fragement.AboutFragment;
import com.livin.vedapadakam.fragement.SettingsDailogFragment;

/**
 * Created by Livin on 5/8/16.
 */
public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;
    ViewPager pager;
    TabsPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"വായനകൾ","ദിവ്യബലി"};
    int Numboftabs =2;
    public LinearLayout container;
    public TextView heading;
    public MainActivity obj;

    @Override
    public void onDestroy() {
        super.onDestroy();
        obj=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obj = this;
        setContentView(R.layout.activity_main);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new TabsPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                System.out.print("here "+position);
                if(position == 0){
                    Fragment currentView = adapter.getItem(position);
                    SharedPreferences sharedPref = obj.getApplicationContext().getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                    int textsizeadder = sharedPref.getInt(getString(R.string.text_size), 3);
                    ((ReadingFragment)currentView).updateTextSize(textsizeadder);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_MENU || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DialogFragment newFragment = new SettingsDailogFragment();
            newFragment.show(getFragmentManager(), "settings");
            return true;
        }
        else if(id == R.id.action_about)
        {
            DialogFragment newFragment = new AboutFragment();
            newFragment.show(getFragmentManager(), "settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public Fragment getCurrentFragment(){
        return adapter.getItem(pager.getCurrentItem());
    }
    public int getCurrentTabIndex(){
        return pager.getCurrentItem();
    }

}
