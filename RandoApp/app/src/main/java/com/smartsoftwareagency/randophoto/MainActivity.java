package com.smartsoftwareagency.randophoto;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rando.library.usermanager.UserInterfaces;
import com.smartsoftwareagency.randophoto.ObjectFactory;

public class MainActivity extends ActionBarActivity {
    private CharSequence m_viewTitle;
    private DrawerLayout m_drawerLayout;
    private ListView m_randoMenuView;
    private ActionBarDrawerToggle m_DrawerToggle;

    public MainActivity() {
        super();

        m_viewTitle = null;
        m_drawerLayout = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserInterfaces.IUserManager userManager = ObjectFactory.getUserManager();
        UserInterfaces.ILoggedUser loggedUser = userManager.GetCurrentUser();

        if(loggedUser != null) {
            this.setMainView();
        }
        else {
            // Go to login page
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void setMainView() {
        setContentView(R.layout.activity_main);

        CharSequence tab_title = m_viewTitle = getTitle();
        m_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Left menu initialization
        String[] rando_menu_list = getResources().getStringArray(R.array.rando_menu_string);
        m_randoMenuView = (ListView) findViewById(R.id.rando_menu);
        // Set the adapter for the list view
        m_randoMenuView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, rando_menu_list));
        // Set the list's click listener
        m_randoMenuView.setOnItemClickListener(new DrawerItemClickListener());

        // Setup tab menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

        return super.onOptionsItemSelected(item);
    }
}
