package com.droidmotion.data.res.listView.actions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.droidmotion.R;
import com.droidmotion.data.Constants;

import java.util.ArrayList;

/**
 * Created by Resh on 22/02/2015.
 */
public class ActionListAdapter extends ArrayAdapter<Short> {

    Context mContext;
    int layoutResourceId;
    ArrayList<Short> data = null;

    public ActionListAdapter(Context mContext, int layoutResourceId, ArrayList<Short> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }


    public View getView(int i, View convertView, ViewGroup parent) {
        View item = convertView;
        ActionListHolder holder;

        if (item == null) {
            item = LayoutInflater.from(mContext).inflate(this.layoutResourceId,
                    null);
            holder = new ActionListHolder();
            holder.actionName = (TextView) item.findViewById(R.id.dAction);

            item.setTag(holder);
        }
        holder = (ActionListHolder) item.getTag();
        holder.actionName.setText(Constants.translateAction(data.get(i), mContext));

        return item;
    }
}
