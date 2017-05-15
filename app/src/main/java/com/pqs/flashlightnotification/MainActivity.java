package com.pqs.flashlightnotification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.pqs.flashlightnotification.fragments.ApplicationsFragment;
import com.pqs.flashlightnotification.provider.SharePreferenceManager;
import com.pqs.flashlightnotification.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST = 1;

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

    @BindView(R.id.switch_notifications)
    Switch switch_notifications;

    @BindView(R.id.layout_incoming_call)
    View layout_incoming_call;

    @BindView(R.id.layout_incoming_sms)
    View layout_incoming_sms;

    @BindView(R.id.layout_normal)
    View layout_normal;

    @BindView(R.id.layout_vibration)
    View layout_vibration;

    @BindView(R.id.layout_silent)
    View layout_silent;

    @BindView(R.id.layout_notifications)
    View layout_notifications;

    @BindView(R.id.tv_incoming_call)
    TextView tv_incoming_call;

    @BindView(R.id.tv_incoming_sms)
    TextView tv_incoming_sms;

    @BindView(R.id.tv_normal)
    TextView tv_normal;

    @BindView(R.id.tv_virbation)
    TextView tv_virbation;

    @BindView(R.id.tv_silent)
    TextView tv_silent;

    @BindView(R.id.tv_notifications)
    TextView tv_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        final Switch switchOnOff = (Switch) menu.findItem(R.id.switch_control).getActionView().findViewById(R.id.switch_control);

        if (!Utils.hasFlash(MainActivity.this)) {
            switchOnOff.setClickable(false);
            offView();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Flash has not been detected", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        boolean isON = SharePreferenceManager.getOnOff(this);
        switchOnOff.setChecked(isON);

        if (isON) {
            init();
        } else {
            offView();
        }

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!checkPermission()) {
                    switchOnOff.setChecked(false);
                    requestCameraPermission();
                    return;
                }

                SharePreferenceManager.setOnOff(MainActivity.this, isChecked);
                if (isChecked) {
                    init();
                } else {
                    offView();
                }
            }
        });
        return true;
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

    @OnClick(R.id.layout_normal)
    public void onNormal(View view) {
        switch_normal.setChecked(!switch_normal.isChecked());
    }

    @OnClick(R.id.layout_vibration)
    public void onVibration(View view) {
        switch_vibration.setChecked(!switch_vibration.isChecked());
    }

    @OnClick(R.id.layout_silent)
    public void onSilent(View view) {
        switch_silent.setChecked(!switch_silent.isChecked());
    }

    private void init() {
        tv_incoming_call.setTextColor(getResources().getColor(android.R.color.black));
        tv_incoming_sms.setTextColor(getResources().getColor(android.R.color.black));
        tv_normal.setTextColor(getResources().getColor(android.R.color.black));
        tv_virbation.setTextColor(getResources().getColor(android.R.color.black));
        tv_silent.setTextColor(getResources().getColor(android.R.color.black));
        tv_notifications.setTextColor(getResources().getColor(android.R.color.black));

        layout_incoming_call.setClickable(true);
        layout_incoming_sms.setClickable(true);
        layout_normal.setClickable(true);
        layout_vibration.setClickable(true);
        layout_silent.setClickable(true);
        layout_notifications.setClickable(true);

        switch_incoming_call.setClickable(true);
        switch_incoming_sms.setClickable(true);
        switch_normal.setClickable(true);
        switch_vibration.setClickable(true);
        switch_silent.setClickable(true);
        switch_notifications.setClickable(true);

        switch_incoming_call.setChecked(SharePreferenceManager.getIncomingCall(this));
        switch_incoming_sms.setChecked(SharePreferenceManager.getIncomingSMS(this));
        switch_normal.setChecked(SharePreferenceManager.getNormal(this));
        switch_vibration.setChecked(SharePreferenceManager.getVibration(this));
        switch_silent.setChecked(SharePreferenceManager.getSilent(this));
        switch_notifications.setChecked(SharePreferenceManager.getNotifications(this));

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

        switch_notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!Utils.isNotificationServiceEnabled(MainActivity.this)) {
                    Utils.showNotificationPermissionDialog(MainActivity.this);switch_notifications.setChecked(!isChecked);
                    return;
                }

                SharePreferenceManager.setNotifications(MainActivity.this, isChecked);
            }
        });
    }

    private void offView() {
        tv_incoming_call.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tv_incoming_sms.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tv_normal.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tv_virbation.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tv_silent.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tv_notifications.setTextColor(getResources().getColor(android.R.color.darker_gray));

        layout_incoming_call.setClickable(false);
        layout_incoming_sms.setClickable(false);
        layout_normal.setClickable(false);
        layout_vibration.setClickable(false);
        layout_silent.setClickable(false);
        layout_notifications.setClickable(false);

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        };

        switch_incoming_call.setOnCheckedChangeListener(checkedChangeListener);
        switch_incoming_call.setClickable(false);
        switch_incoming_call.setChecked(false);

        switch_incoming_sms.setOnCheckedChangeListener(checkedChangeListener);
        switch_incoming_sms.setClickable(false);
        switch_incoming_sms.setChecked(false);

        switch_normal.setOnCheckedChangeListener(checkedChangeListener);
        switch_normal.setClickable(false);
        switch_normal.setChecked(false);

        switch_vibration.setOnCheckedChangeListener(checkedChangeListener);
        switch_vibration.setClickable(false);
        switch_vibration.setChecked(false);

        switch_silent.setOnCheckedChangeListener(checkedChangeListener);
        switch_silent.setClickable(false);
        switch_silent.setChecked(false);

        switch_notifications.setOnCheckedChangeListener(checkedChangeListener);
        switch_notifications.setClickable(false);
        switch_notifications.setChecked(false);
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPhonePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission() {
        return checkCameraPermission() && checkPhonePermission() && checkSmsPermission();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS}, PERMISSIONS_REQUEST);
    }
}
