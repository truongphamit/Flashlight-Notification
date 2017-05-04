package com.pqs.flashlightnotification.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pqs.flashlightnotification.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by truongpq on 5/1/17.
 */

public class Utils {
    public static void showCallSettingsDialog(final Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.incoming_call)
                .customView(R.layout.incoming_call_settings, false)
                .neutralText("TEST")
                .positiveText("SAVE")
                .negativeText("CANCEL")
                .build();

        View view = dialog.getCustomView();
        assert view != null;
        final TextView tv_on_length = (TextView) view.findViewById(R.id.tv_on_length);
        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
        seekbar_on_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_on_length.setText(context.getString(R.string.ms, getLength(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final TextView tv_off_length = (TextView) view.findViewById(R.id.tv_off_length);
        SeekBar seekbar_off_length = (SeekBar) view.findViewById(R.id.seekbar_off_length);
        seekbar_off_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_off_length.setText(context.getString(R.string.ms, getLength(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dialog.show();
    }

    public static int getLength(int i) {
        int length = i * 50 + 50;
        return length;
    }

    public static void showSMSSettingsDialog(final Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.incoming_call)
                .customView(R.layout.incoming_sms_settings, false)
                .neutralText("TEST")
                .positiveText("SAVE")
                .negativeText("CANCEL")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Flash flash = Flash.getInstance();
                        flash.switchFlash();
                    }
                })
                .build();

        View view = dialog.getCustomView();
        assert view != null;
        final TextView tv_on_length = (TextView) view.findViewById(R.id.tv_on_length);
        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
        seekbar_on_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_on_length.setText(context.getString(R.string.ms, getLength(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final TextView tv_off_length = (TextView) view.findViewById(R.id.tv_off_length);
        SeekBar seekbar_off_length = (SeekBar) view.findViewById(R.id.seekbar_off_length);
        seekbar_off_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_off_length.setText(context.getString(R.string.ms, getLength(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final TextView tv_times = (TextView) view.findViewById(R.id.tv_times);
        SeekBar seekbar_times = (SeekBar) view.findViewById(R.id.seekbar_times);
        seekbar_times.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_times.setText(context.getString(R.string.time, getTime(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog.show();
    }

    public static int getTime(int i) {
        return i + 1;
    }

    public static void slideFragment(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up);
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static List<ApplicationInfo> getInstalledApp(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = new ArrayList<>();
        for (ApplicationInfo applicationInfo : pm.getInstalledApplications(PackageManager.GET_META_DATA)) {
            if (!isSystemPackage(applicationInfo) && !applicationInfo.packageName.equals(context.getPackageName())) applicationInfos.add(applicationInfo);
        }
        return applicationInfos;
    }

    public static String getAppName(Context context, ApplicationInfo applicationInfo) {
        PackageManager pm = context.getPackageManager();
        return  pm.getApplicationLabel(applicationInfo).toString();
    }

    public static Drawable getAppIcon(Context context, ApplicationInfo applicationInfo) {
        PackageManager pm = context.getPackageManager();
        return pm.getApplicationIcon(applicationInfo);
    }

    public static boolean hasFlash(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
