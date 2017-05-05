package com.pqs.flashlightnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.pqs.flashlightnotification.fragments.ApplicationsFragment;
import com.pqs.flashlightnotification.provider.SharePreferenceManager;
import com.pqs.flashlightnotification.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.switch_incoming_call)
    Switch switch_incoming_call;

    @BindView(R.id.switch_incoming_sms)
    Switch switch_incoming_sms;

    @BindView(R.id.switch_normal)
    Switch switch_normal;

    @BindView(R.id.switch_vibration)
    Switch switch_vibration;

    @BindView(R.id.switch_silent)
    Switch switch_silent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        init();
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

    private void init() {
        switch_incoming_call.setChecked(SharePreferenceManager.getIncomingCall(this));
        switch_incoming_sms.setChecked(SharePreferenceManager.getIncomingSMS(this));
        switch_normal.setChecked(SharePreferenceManager.getNormal(this));
        switch_vibration.setChecked(SharePreferenceManager.getVibration(this));
        switch_silent.setChecked(SharePreferenceManager.getSilent(this));

        switch_incoming_call.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceManager.setIncomingCall(MainActivity.this, isChecked);
            }
        });

        switch_incoming_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceManager.setIncomingSMS(MainActivity.this, isChecked);
            }
        });

        switch_normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceManager.setNormal(MainActivity.this, isChecked);
            }
        });

        switch_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceManager.setVibration(MainActivity.this, isChecked);
            }
        });

        switch_silent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceManager.setSilent(MainActivity.this, isChecked);
            }
        });
    }
}
