package com.pqs.flashlightnotification.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pqs.flashlightnotification.R;
import com.pqs.flashlightnotification.models.App;
import com.pqs.flashlightnotification.provider.SharePreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by truongpq on 5/1/17.
 */

public class Utils {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";

    public static void showCallSettingsDialog(final Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.incoming_call)
                .customView(R.layout.incoming_call_settings, false)
                .neutralText("TEST")
                .positiveText("SAVE")
                .negativeText("CANCEL")
                .autoDismiss(false)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        assert view != null;
                        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
                        SeekBar seekbar_off_length = (SeekBar) view.findViewById(R.id.seekbar_off_length);
                        Flash.test(getLength(seekbar_on_length.getProgress()), getLength(seekbar_off_length.getProgress()));
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        assert view != null;
                        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
                        SeekBar seekbar_off_length = (SeekBar) view.findViewById(R.id.seekbar_off_length);
                        SharePreferenceManager.setOnLengthCall(context, seekbar_on_length.getProgress());
                        SharePreferenceManager.setOffLengthCall(context, seekbar_off_length.getProgress());
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();

        View view = dialog.getCustomView();
        assert view != null;
        final TextView tv_on_length = (TextView) view.findViewById(R.id.tv_on_length);
        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
        seekbar_on_length.setProgress(SharePreferenceManager.getOnLengthCall(context));
        tv_on_length.setText(context.getString(R.string.ms, getLength(SharePreferenceManager.getOnLengthCall(context))));
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
        seekbar_off_length.setProgress(SharePreferenceManager.getOffLengthCall(context));
        tv_off_length.setText(context.getString(R.string.ms, getLength(SharePreferenceManager.getOffLengthCall(context))));
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
                .title(R.string.incoming_sms)
                .customView(R.layout.incoming_sms_settings, false)
                .neutralText("TEST")
                .positiveText("SAVE")
                .negativeText("CANCEL")
                .autoDismiss(false)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        assert view != null;
                        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
                        SeekBar seekbar_off_length = (SeekBar) view.findViewById(R.id.seekbar_off_length);
                        Flash.test(getLength(seekbar_on_length.getProgress()), getLength(seekbar_off_length.getProgress()));
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        assert view != null;
                        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
                        SeekBar seekbar_off_length = (SeekBar) view.findViewById(R.id.seekbar_off_length);
                        SeekBar seekbar_times = (SeekBar) view.findViewById(R.id.seekbar_times);
                        SharePreferenceManager.setOnLengthSMS(context, seekbar_on_length.getProgress());
                        SharePreferenceManager.setOffLengthSMS(context, seekbar_off_length.getProgress());
                        SharePreferenceManager.setTimesSMS(context, seekbar_times.getProgress());
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();

        View view = dialog.getCustomView();
        assert view != null;
        final TextView tv_on_length = (TextView) view.findViewById(R.id.tv_on_length);
        SeekBar seekbar_on_length = (SeekBar) view.findViewById(R.id.seekbar_on_length);
        seekbar_on_length.setProgress(SharePreferenceManager.getOnLengthSMS(context));
        tv_on_length.setText(context.getString(R.string.ms, getLength(SharePreferenceManager.getOnLengthSMS(context))));
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
        seekbar_off_length.setProgress(SharePreferenceManager.getOffLengthSMS(context));
        tv_off_length.setText(context.getString(R.string.ms, getLength(SharePreferenceManager.getOffLengthSMS(context))));
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
        seekbar_times.setProgress(SharePreferenceManager.getTimesSMS(context));
        tv_times.setText(context.getString(R.string.time, getTime(SharePreferenceManager.getTimesSMS(context))));
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

    public static void showNotificationPermissionDialog(final Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.notification_listener_service)
                .content(R.string.notification_listener_service_explanation)
                .positiveText("YES")
                .negativeText("NO")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    }
                }).show();
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

    public static List<App> getInstalledApp(Context context) throws IOException, JSONException {
        PackageManager pm = context.getPackageManager();
        List<App> apps = new ArrayList<>();
        List<App> checkApps = getAppsNotification(context);
        for (ApplicationInfo applicationInfo : pm.getInstalledApplications(PackageManager.GET_META_DATA)) {
            if (!isSystemPackage(applicationInfo) && !applicationInfo.packageName.equals(context.getPackageName())) {
                App app = new App(applicationInfo, false);
                for (App a : checkApps) {
                    if (app.equals(a))  {
                        app.setCheck(true);
                        break;
                    }
                }
                apps.add(app);
            }
        }
        return apps;
    }

    public static String getAppName(Context context, ApplicationInfo applicationInfo) {
        PackageManager pm = context.getPackageManager();
        return pm.getApplicationLabel(applicationInfo).toString();
    }

    public static Drawable getAppIcon(Context context, ApplicationInfo applicationInfo) {
        PackageManager pm = context.getPackageManager();
        return pm.getApplicationIcon(applicationInfo);
    }

    public static boolean hasFlash(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public static ApplicationInfo getApplicationInfo(Context context, String packagename) {
        try {
            return context.getPackageManager().getApplicationInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToJson(List<App> apps, String filepath) throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        for (App app : apps) {
            JSONObject jsonObject = app.buildJsonObject();
            jsonArray.put(jsonObject);
        }

        writeTextFile(filepath, jsonArray.toString());
    }

    static public void writeTextFile(String filePath, String text) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(text);
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    static public String loadTextFile(String filePath) throws IOException {
        //Get the text file
        File file = new File(filePath);

        //Read text from file
        StringBuilder text = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } finally {
            br.close();
        }

        return text.toString();
    }

    public static List<App> getAppsNotification(Context context) throws IOException, JSONException {
        List<App> apps = new ArrayList<>();

        String text = loadTextFile(getFilePath(context));
        JSONArray jsonArray = new JSONArray(text);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            App app = new App(getApplicationInfo(context, jsonObject.getString("packagename")), true);
            apps.add(app);
        }

        return apps;
    }

    public static boolean existsFile(String filePath) {
        File f = new File(filePath);
        if (f.exists() && !f.isDirectory())
            return true;
        else
            return false;
    }

    public static String getFilePath(Context context) {
        String filePath = Utils.getInternalDataPath(context) + File.separatorChar + "data.json";
        if (!Utils.existsFile(filePath)) {
            try {
                Utils.writeTextFile(filePath, "[]");
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
        return filePath;
    }

    public static String getInternalDataPath(Context c) {
        String path = c.getFilesDir().getPath();
        if (path.length() == 0) {
            path = "/data/data/" + c.getPackageName() + "/files";
            File filesDir = new File(path);
            filesDir.mkdirs();
        }
        return path;
    }

    public static boolean isNotificationServiceEnabled(Context context){
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
