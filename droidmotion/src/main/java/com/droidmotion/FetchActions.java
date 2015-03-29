package com.droidmotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.droidmotion.data.CommonData;
import com.droidmotion.data.Constants;
import com.droidmotion.data.res.listView.actions.ActionListAdapter;

import java.util.ArrayList;

/**
 * Created by Resh on 22/02/2015.
 */
public class FetchActions extends ActionBarActivity implements ListView.OnItemClickListener {

    private ArrayList<Short> actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String packageName = getIntent().getStringExtra("appPackage");
        CommonData appState = (CommonData) getApplicationContext();
        actions = appState.getActions(packageName);

        if (actions == null) {
            actions = new ArrayList<Short>();
            actions.add(Constants.OPEN_APP);
        }
        if (actions.size() == 1) {
            finish();
            nextStep(actions.get(0));
        } else {
            setContentView(R.layout.activity_fetch_actions);

            ListView list = (ListView) findViewById(R.id.lActions);
            list.setAdapter(new ActionListAdapter(this, R.layout.action_entry, actions));
            list.setOnItemClickListener(this);
        }
    }

    private void nextStep(short action) {
        Intent i = new Intent(this, DetailsAction.class);
        i.putExtra("action", action);
        i.putExtra("appName", getIntent().getExtras().getString("appName"));
        i.putExtra("appPackage", getIntent().getExtras().getString("appPackage"));
        i.putExtra("buttonID", getIntent().getExtras().getInt("buttonID"));
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        nextStep(actions.get(position));
    }

}
