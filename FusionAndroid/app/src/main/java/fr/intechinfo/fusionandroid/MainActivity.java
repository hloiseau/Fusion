package fr.intechinfo.fusionandroid;

import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import retrofit2.Call;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Fragment actualFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add the toolbar and the Icon
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //Find our drawer view & Setup drawer view
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        onNewIntent(getIntent());
        FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");
        PermissionUtil.initPermissions(this);
        RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
        Token token = new Token();
        token.SetToken(FirebaseInstanceId.getInstance().getToken());
        final Call<Token> stringCall = retrofitAPI.CreateNewDevice(token);
        HttpExecute t = new HttpExecute(stringCall);
        t.start();
        //syncDataTest();
    }

    protected void syncDataTest(){
        RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
        ContentCollector cc = new ContentCollector();
        ContactsList contactLs = new ContactsList();
        contactLs.SetContact(cc.GetContacts(this));
        Log.d("SYNC","Name : "+ contactLs.GetContact().get(1).GetName()+" Number : " + contactLs.GetContact().get(1).GetNumber());
        SMSList SMSLs = new SMSList();
        SMSLs.SetSMS(cc.GetSMS(this));
        Log.d("SYNC","Address : "+ SMSLs.GetSMS().get(3).GetAddress()+" Message : " + SMSLs.GetSMS().get(3).GetBody()+" Date : "+SMSLs.GetSMS().get(3).GetDate()+" Type : "+SMSLs.GetSMS().get(3).GetType());

        new HttpExecute(retrofitAPI.CreateContacts(contactLs)).start();
        new HttpExecute(retrofitAPI.CreateSMS(SMSLs)).start();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    selectDrawerItem(menuItem);
                    mDrawerLayout.closeDrawers();   // close drawer when item is tapped

                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here

                    return true;
                }
            });
    }

    public void selectDrawerItem(MenuItem menuItem){
        //Create a new fragment and specify the fragment to show based on nav item cliked
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.nav_computer:
                this.showComputerFragment();
                break;
            default:
                break;
        }

        // Insert the fragment by replacing any existing fragment
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        menuItem.setChecked(true);      // set item as selected to persist highlight
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showComputerFragment(){

    }
}
