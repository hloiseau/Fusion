package fr.intechinfo.fusionandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");
        PermissionUtil.verrifyContactsPermissions(this);
        PermissionUtil.verrifySMSPermissions(this);
        PermissionUtil.verrifyPhoneStatePermissions(this);
    }
}
