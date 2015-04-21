package com.hellopopartz.hidlauncher.data.res.listView.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellopopartz.hidlauncher.R;
import com.hellopopartz.hidlauncher.data.res.AppInfo;

import java.util.ArrayList;

/**
 * Created by Resh on 22/02/2015.
 */
public class AppListAdapter extends ArrayAdapter<AppInfo> {

    Context mContext;
    int layoutResourceId;
    ArrayList<AppInfo> data = null;

    public AppListAdapter(Context mContext, int layoutResourceId, ArrayList<AppInfo> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }


    public View getView(int i, View convertView, ViewGroup parent) {
        View item = convertView;
        AppListHolder holder;

        if (item == null) {
            item = LayoutInflater.from(mContext).inflate(this.layoutResourceId,
                    null);
            holder = new AppListHolder();
            holder.appIcon = (ImageView) item.findViewById(R.id.dAppIcon);
            holder.appName = (TextView) item.findViewById(R.id.dAppName);
            holder.appPackage = (TextView) item.findViewById(R.id.dPackageName);
            holder.official = (TextView) item.findViewById(R.id.dOfficial);

            item.setTag(holder);
        }
        holder = (AppListHolder) item.getTag();

        holder.appIcon.setImageDrawable(data.get(i).appIcon);
        holder.appName.setText(data.get(i).appName);
        holder.appPackage.setText(data.get(i).appPackage);
        holder.official.setText(data.get(i).official ? mContext.getString(R.string.official) : "");
        return item;
    }
}
