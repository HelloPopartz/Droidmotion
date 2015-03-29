package com.tactil.lpro.muv_config.data.res.listView.button;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tactil.lpro.muv_config.R;
import com.tactil.lpro.muv_config.data.Constants;
import com.tactil.lpro.muv_config.data.res.ButtonInfo;

import java.util.ArrayList;

/**
 * Created by Resh on 21/02/2015.
 */
public class ButtonListAdapter extends ArrayAdapter<ButtonInfo> {

    Context mContext;
    int layoutResourceId;
    ArrayList<ButtonInfo> data = null;

    public ButtonListAdapter(Context mContext, int layoutResourceId, ArrayList<ButtonInfo> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }


    public View getView(int i, View convertView, ViewGroup parent) {
        View item = convertView;
        ButtonListHolder holder;

        if (item == null) {
            item = LayoutInflater.from(mContext).inflate(this.layoutResourceId,
                    null);
            holder = new ButtonListHolder();
            holder.buttonName = (TextView) item.findViewById(R.id.dButtonName);
            holder.launcher = (TextView) item.findViewById(R.id.dLauncher);
            holder.action = (TextView) item.findViewById(R.id.dAction);

            item.setTag(holder);
        }
        holder = (ButtonListHolder) item.getTag();

        holder.buttonName.setText("Boton " + data.get(i).getName());
        String s = data.get(i).getApp();
        holder.launcher.setText(s == null ? mContext.getString(R.string.dNoAction) : s);
        holder.action.setText(Constants.translateAction(data.get(i).getAction(),mContext));

        return item;
    }

}
