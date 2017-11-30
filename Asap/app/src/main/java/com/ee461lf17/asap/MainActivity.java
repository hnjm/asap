package com.ee461lf17.asap;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

//    private ListView mainListView ;
//    private ArrayAdapter<String> listAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up sidenav drawer
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.navigation);
        setupDrawerContent(nvDrawer);

        // Set up toggle for drawer layout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.side_nav_open, R.string.side_nav_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        com.github.clans.fab.FloatingActionButton account = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabAccount);
        account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.activity_main,null);
                View popupLayout = inflater.inflate(R.layout.popup_new_account,null);
                float density = MainActivity.this.getResources().getDisplayMetrics().density;
                PopupWindow popup = new PopupWindow(popupLayout, (int)density*240, (int)density*285, true);
                //PopupWindow popup = new PopupWindow((int)density*240, (int)density*285);
                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });


        com.github.clans.fab.FloatingActionButton budget = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabBudget);
        budget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.activity_main,null);
                View popupLayout = inflater.inflate(R.layout.popup_new_budget,null);
                float density = MainActivity.this.getResources().getDisplayMetrics().density;
                PopupWindow popup = new PopupWindow(popupLayout, (int)density*240, (int)density*285, true);
                //PopupWindow popup = new PopupWindow((int)density*240, (int)density*285);
                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        com.github.clans.fab.FloatingActionButton expense = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabExpense);
        expense.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.activity_main,null);
                View popupLayout = inflater.inflate(R.layout.popup_new_expense,null);
                float density = MainActivity.this.getResources().getDisplayMetrics().density;
                PopupWindow popup = new PopupWindow(popupLayout, (int)density*240, (int)density*285, true);
                //PopupWindow popup = new PopupWindow((int)density*240, (int)density*285);
                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });




    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }

                });
    }
     // Use this function to take action on keypress
    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.sync_budgets:
                // Sync the budgets by pulling/pushing changes
                Snackbar.make(nvDrawer, "Sync budget selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.select_budget:
                // Trigger event to select a different budget, maybe use a dropdown?
                Snackbar.make(nvDrawer, "Select budget selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.user:
                // View user profile, maybe switch account
                Snackbar.make(nvDrawer, "User selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
        // Do anything else, maybe close drawer
        //mDrawer.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            int viewNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            if(viewNumber == 1){
                //Budget List
                String[] budgets = new String[] { "Monthy", "Fun", "House"};
                ArrayList<String> budgetList = new ArrayList<String>();
                budgetList.addAll( Arrays.asList(budgets) );
                // Create ArrayAdapter using the budget list.
                ListAdapter budgetAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, budgetList);
                ListView mainListView = (ListView) rootView.findViewById( R.id.mainListView );
                mainListView.setAdapter( budgetAdapter );
            }
            else if(viewNumber == 2){
                String[] accounts = new String[] { "Checking", "Personal Checking"};
                ArrayList<String> accountsList = new ArrayList<String>();
                accountsList.addAll( Arrays.asList(accounts) );
                // Create ArrayAdapter using the budget list.
                ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, accountsList);
                ListView mainListView = (ListView) rootView.findViewById( R.id.mainListView );
                mainListView.setAdapter( listAdapter );
            }
            else{
                
            }



            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
