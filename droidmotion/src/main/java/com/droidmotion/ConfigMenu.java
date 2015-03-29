package com.droidmotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.droidmotion.data.CommonData;
import com.droidmotion.data.res.ButtonInfo;
import com.droidmotion.data.res.listView.button.ButtonListAdapter;

import java.util.ArrayList;


public class ConfigMenu extends ActionBarActivity implements View.OnClickListener, ListView.OnItemClickListener {

    private ArrayList<ButtonInfo> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_menu);

        //Floating Action Button
        Button mFab = (Button) findViewById(R.id.bDone);
        mFab.setOnClickListener(this);

        //Global data
        CommonData appState = (CommonData) getApplicationContext();
        this.buttons = appState.getAvailableButtons();
        ListView list = (ListView) findViewById(R.id.lConf);
        list.setAdapter(new ButtonListAdapter(this, R.layout.configuration_entry, this.buttons));
        list.setOnItemClickListener(this);

    }


    //Floating button handler
    @Override
    public void onClick(View v) {

        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager != null)
            imeManager.showInputMethodPicker();

    }

    //ListView handler
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, FetchApps.class);
        i.putExtra("buttonID", position);
        startActivity(i);
    }

}
