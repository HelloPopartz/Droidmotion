package com.hellopopartz.hidlauncher;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hellopopartz.hidlauncher.data.CommonData;
import com.hellopopartz.hidlauncher.data.Constants;
import com.hellopopartz.hidlauncher.data.res.view.TimerView;


/**
 * Created by Resh on 24/02/2015.
 */
public class DetailsAction extends ActionBarActivity implements Button.OnClickListener {

    // Auto call
    static final int PICK_CONTACT = 1;
    static final int PICK_NUMBER = 2;
    protected final Button mNumbers[] = new Button[10];
    //Alarm
    protected int mInputSize = 6;
    protected int mInput[] = new int[mInputSize];
    protected int mInputPointer = -1;
    protected TimerView mEnteredTime;
    protected ImageButton mDelete;

    //General
    private short action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = getIntent().getShortExtra("action", (short) 0);

        Button b;
        switch (action) {
            case Constants.AUTO_MESSAGE:
            case Constants.AUTO_MESSAGE_SMS:
                setContentView(R.layout.activity_auto_message);
                b = (Button) findViewById(R.id.bDone);
                b.setOnClickListener(this);
                break;
            case Constants.AUTO_CALL:
                setTitle(getResources().getString(R.string.autoCallSelect));
                setContentView(R.layout.activity_auto_call);

                b = (Button) findViewById(R.id.bNumber);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), PhoneNumber.class);
                        startActivityForResult(i, PICK_NUMBER);
                    }
                });

                b = (Button) findViewById(R.id.bContacts);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);
                    }
                });

                break;
            case Constants.QUICK_ALARM:
                setTitle(getResources().getString(R.string.alarmSelect));
                setContentView(R.layout.activity_auto_alarm);
                View v1 = findViewById(R.id.first);
                View v2 = findViewById(R.id.second);
                View v3 = findViewById(R.id.third);
                View v4 = findViewById(R.id.fourth);
                mEnteredTime = (TimerView) findViewById(R.id.timer_time_text);

                mDelete = (ImageButton) findViewById(R.id.delete);
                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mInputPointer >= 0) {
                            for (int i = 0; i < mInputPointer; i++) {
                                mInput[i] = mInput[i + 1];
                            }
                            mInput[mInputPointer] = 0;
                            mInputPointer--;
                            updateTime();
                        }
                    }
                });

                reset();

                mNumbers[1] = (Button) v1.findViewById(R.id.key_left);
                mNumbers[2] = (Button) v1.findViewById(R.id.key_middle);
                mNumbers[3] = (Button) v1.findViewById(R.id.key_right);

                mNumbers[4] = (Button) v2.findViewById(R.id.key_left);
                mNumbers[5] = (Button) v2.findViewById(R.id.key_middle);
                mNumbers[6] = (Button) v2.findViewById(R.id.key_right);

                mNumbers[7] = (Button) v3.findViewById(R.id.key_left);
                mNumbers[8] = (Button) v3.findViewById(R.id.key_middle);
                mNumbers[9] = (Button) v3.findViewById(R.id.key_right);

                b = (Button) v4.findViewById(R.id.key_left);
                b.setEnabled(false);
                b = (Button) v4.findViewById(R.id.key_right);
                b.setEnabled(false);
                mNumbers[0] = (Button) v4.findViewById(R.id.key_middle);

                b = (Button) findViewById(R.id.bDone);
                b.setOnClickListener(this);

                View.OnClickListener v = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer val = (Integer) v.getTag(R.id.numbers_key);
                        // A number was pressed
                        if (val != null) {
                            // pressing "0" as the first digit does nothing
                            if (mInputPointer == -1 && val == 0) {
                                return;
                            }
                            if (mInputPointer < mInputSize - 1) {
                                for (int i = mInputPointer; i >= 0; i--) {
                                    mInput[i + 1] = mInput[i];
                                }
                                mInputPointer++;
                                mInput[0] = val;
                                updateTime();
                            }
                            return;
                        }
                    }
                };
                for (int i = 0; i < 10; i++) {
                    mNumbers[i].setOnClickListener(v);
                    mNumbers[i].setText(String.format("%d", i));
                    mNumbers[i].setTextColor(getResources().getColor(R.color.green));
                    mNumbers[i].setTag(R.id.numbers_key, new Integer(i));
                }
                updateTime();
                break;
            default:
                finish();
                commitChanges(action, null);
                break;
        }
    }

    protected void reset() {
        for (int i = 0; i < mInputSize; i++) {
            mInput[i] = 0;
        }
        mInputPointer = -1;
        updateTime();
    }

    protected void updateTime() {
        mEnteredTime.setTime(mInput[5], mInput[4], mInput[3], mInput[2],
                mInput[1] * 10 + mInput[0]);
    }

    protected int getTime() {
        return mInput[5] * 36000 + mInput[4] * 3600 + mInput[3] * 600 + mInput[2] * 60 + mInput[1] * 10 + mInput[0];
    }

    protected void commitChanges(short action, String actionDetails) {
        CommonData appState = (CommonData) getApplicationContext();
        Bundle extras = getIntent().getExtras();
        appState.setButton(extras.getInt("buttonID"), extras.getString("appName"), extras.getString("appPackage"), action, actionDetails);
        appState.save();
        Intent i = new Intent(this, ConfigMenu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        finish();
        switch (action) {
            case Constants.AUTO_MESSAGE:
            case Constants.AUTO_MESSAGE_SMS:
                EditText e = (EditText) findViewById(R.id.eAutoMessage);
                commitChanges(action, e.getText().toString());
                break;
            case Constants.QUICK_ALARM:
                commitChanges(action, Integer.toString(getTime()));
                break;
        }
    }

    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_NUMBER):
                if (resultCode == this.RESULT_OK) {
                    commitChanges(action, data.getStringExtra("number"));
                }
                break;
            case (PICK_CONTACT):
                if (resultCode == this.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            commitChanges(action, cNumber);
                        }
                    }
                }
                break;
        }
    }
}
