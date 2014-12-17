package com.smartsoftwareagency.randophoto;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rando.library.usermanager.UserInterfaces;
import com.smartsoftwareagency.randophoto.common.ObjectFactory;
import com.smartsoftwareagency.randophoto.tabs_activity.FragmentMain;
import com.smartsoftwareagency.randophoto.tabs_activity.FragmentSettings;

public class MainActivity extends ActionBarActivity {
    private CharSequence m_viewTitle;
    private DrawerLayout m_drawerLayout;
    private ListView m_randoMenuView;
    private ActionBarDrawerToggle m_randoMenuToggle;
    private String[] m_randoMenuList;
    private FragmentMain m_mainView;
    private FragmentSettings m_settingsView;

    public MainActivity() {
        super();

        m_viewTitle = null;
        m_drawerLayout = null;
        m_randoMenuView = null;
        m_randoMenuToggle = null;
        m_randoMenuList = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserInterfaces.IUserManager userManager = ObjectFactory.getUserManager();
        UserInterfaces.ILoggedUser loggedUser = userManager.GetCurrentUser();

        if(loggedUser != null) {
            this.setMainView(savedInstanceState);
        }
        else {
            try {
                showLoginView();
            }
            catch(Exception e) {
                //
            }
        }
    }

    // Go to login page
    private void showLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setMainView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        CharSequence tab_title = m_viewTitle = this.getTitle();
        m_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Setup rando menu
        m_randoMenuList = getResources().getStringArray(R.array.rando_menu_string);
        m_randoMenuView = (ListView) findViewById(R.id.rando_menu);
        // Устанавливаем, как должны отображаться элементы меню через адаптер
        m_randoMenuView.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_item_layout, m_randoMenuList));
        // Set the list's click listener
        m_randoMenuView.setOnItemClickListener(new RandoMenuClickListener());

        // Setup tab menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        m_randoMenuToggle = new ActionBarDrawerToggle(
            this, /* host Activity */
            m_drawerLayout, /* DrawerLayout object */
            //R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
            R.string.menu_open_text, /* "open drawer" description */
            R.string.menu_close_text /* "close drawer" description */
            )
        {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(m_viewTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(m_viewTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        m_drawerLayout.setDrawerListener(m_randoMenuToggle);

        // Initialize the first fragment when the application first loads.
        m_mainView = new FragmentMain();
        m_settingsView = new FragmentSettings();
        if (savedInstanceState == null) {
            showView(0); // Show main fragment
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (m_randoMenuToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        // We have no any action buttons in activity main. So just return.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        m_viewTitle = title;
        getSupportActionBar().setTitle(m_viewTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        m_randoMenuToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        m_randoMenuToggle.onConfigurationChanged(newConfig);
    }

    /* Отображаем контент */
    private void showView(int position) {
        // Update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = m_mainView;
                break;
            case 1:
                fragment = m_settingsView;
                break;
            case 2:
                UserInterfaces.IUserManager userManager = ObjectFactory.getUserManager();
                userManager.LogOffUser();
                this.showLoginView();
                break;
            default:
                break;
        }

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // Highlight the selected item, update the title, and close the drawer
            m_randoMenuView.setItemChecked(position, true);
            setTitle(m_randoMenuList[position]);
            m_drawerLayout.closeDrawer(m_randoMenuView);
        } else {
            // Error
            //Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
    }

    /* The click listener for Rando Menu in the navigation view */
    private class RandoMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showView(position);
        }
    }
}
