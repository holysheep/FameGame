package gamecore.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gamecore.R;
import gamecore.fragment.CardsFragment;
import gamecore.fragment.EmptyFragment;
import gamecore.fragment.NavigationDrawerFragment;
import gamecore.fragment.NewGamesFragment;
import gamecore.views.SlidingTabLayout;

public class GameCatalog extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private static final int NEWGAME_RESULTS = 0;
    private static final int CARDS_RESULTS = 1;
    private static final int NONAME_RESULTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getDelegate().getSupportActionBar().setDisplayShowHomeEnabled(true);

       NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mPager);
        isNetworkAvailable(this);
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connMan=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=connMan.getActiveNetworkInfo();
        if (network == null || !network.isConnected()) {
            final Crouton crouton = Crouton.makeText(this, "Please check your internet connection", Style.ALERT)
                    .setConfiguration(new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build());

            crouton.setOnClickListener(new View.OnClickListener() {
                @ Override
                public void onClick(View v) {
                    Crouton.hide(crouton);
                }
            });
            crouton.show();
            return false;
        }
        return true;
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
        if (id == R.id.aboutapp) {
            showAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showAbout() {
        View messageView = getLayoutInflater().inflate(R.layout.about_app, null, false);
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
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
                case NEWGAME_RESULTS:
                    fragment = NewGamesFragment.newInstance("", "");
                    break;
                case CARDS_RESULTS:
                    fragment = CardsFragment.newInstance("", "");
                    break;
                case NONAME_RESULTS:
                    fragment = EmptyFragment.newInstance("", "");
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
}
