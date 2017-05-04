package com.pqs.flashlightnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.pqs.flashlightnotification.fragments.ApplicationsFragment;
import com.pqs.flashlightnotification.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.layout_incoming_call)
    public void onIncomingCall(View view) {
        Utils.showCallSettingsDialog(this);
    }

    @OnClick(R.id.layout_incoming_sms)
    public void onIncomingSMS(View view) {
        Utils.showSMSSettingsDialog(this);
    }

    @OnClick(R.id.layout_notifications)
    public void onNotificationsClick(View view) {
        Utils.slideFragment(ApplicationsFragment.newInstance(), getSupportFragmentManager());
    }
}
