package gamecore.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import gamecore.R;
import gamecore.adapters.InfAdapter;
import gamecore.fragment.NavigationDrawerFragment;
import gamecore.fragment.PCFragment;
import gamecore.fragment.PS4Fragment;
import gamecore.fragment.XboxFragment;
import gamecore.services.MyService;
import gamecore.views.SlidingTabLayout;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

public class GameCatalog extends ActionBarActivity {

    private static final int JOB_ID = 100 ;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private InfAdapter adapter;
    private JobScheduler jobScheduler;
    private static final int PC_RESULTS = 0;
    private static final int PS4_RESULTS = 1;
    private static final int XBOX_RESULTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jobScheduler = JobScheduler.getInstance(this);
        constructJob();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      /*  NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        */

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this, SubActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        // Return the Fragment associated with a specified position.
        @Override
        public Fragment getItem(int num) {
            Fragment fragment = null;
            switch (num) {
                case PC_RESULTS:
                    fragment = PCFragment.newInstance("", "");
                    break;
                case PS4_RESULTS:
                    fragment = XboxFragment.newInstance("", "");
                    break;
                case XBOX_RESULTS:
                    fragment = PS4Fragment.newInstance("", "");
                    break;
            }
            return fragment;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        //Return the number of views available.
        @Override
        public int getCount() {
            return 3;
        }
    }

    public void constructJob() {
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        builder.setPeriodic(2000)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);

        jobScheduler.schedule(builder.build());
    }
}
