package com.hellopopartz.hidlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hellopopartz.hidlauncher.data.CommonData;
import com.hellopopartz.hidlauncher.data.res.AppInfo;
import com.hellopopartz.hidlauncher.data.res.listView.app.AppListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Resh on 22/02/2015.
 */
public class FetchApps extends ActionBarActivity implements ListView.OnItemClickListener {
    private PackageManager manager;
    private ArrayList<AppInfo> officialApps;
    private ArrayList<AppInfo> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_apps);
        loadApps();
        this.officialApps.addAll(apps);
        ListView list = (ListView) findViewById(R.id.lApps);
        list.setAdapter(new AppListAdapter(this, R.layout.app_entry, this.officialApps));
        list.setOnItemClickListener(this);
    }

    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<AppInfo>();
        officialApps = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        CommonData appState = (CommonData) getApplicationContext();

        officialApps.add(new AppInfo(getString(R.string.autoCall), "system.dialer", getResources().getDrawable(R.drawable.ic_auto_call), true));
        officialApps.add(new AppInfo(getString(R.string.camera), "system.camera", getResources().getDrawable(R.drawable.ic_camera), true));
        officialApps.add(new AppInfo(getString(R.string.alarm), "system.alarm", getResources().getDrawable(R.drawable.ic_add_alarm), true));
        officialApps.add(new AppInfo(getString(R.string.sms), "system.sms", getResources().getDrawable(R.drawable.ic_chat_grey), true));
        officialApps.add(new AppInfo(getString(R.string.media), "system.media", getResources().getDrawable(R.drawable.ic_equalizer), true));


        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            if (appState.isSupported(ri.activityInfo.packageName))
                officialApps.add(new AppInfo(ri.loadLabel(manager), ri.activityInfo.packageName, ri.activityInfo.loadIcon(manager), true));
            else
                apps.add(new AppInfo(ri.loadLabel(manager), ri.activityInfo.packageName, ri.activityInfo.loadIcon(manager), false));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, FetchActions.class);
        i.putExtra("appPackage", officialApps.get(position).appPackage);
        i.putExtra("appName", officialApps.get(position).appName);
        i.putExtra("buttonID", getIntent().getExtras().getInt("buttonID"));
        startActivity(i);
    }

}
