package com.droidmotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Resh on 18/03/2015.
 */
public class PhoneNumber extends ActionBarActivity implements Button.OnClickListener {

    protected final Button mNumbers[] = new Button[10];
    protected ImageButton mDelete;
    protected long mDialVal;
    protected int digits;
    private TextView mDial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_number);

        View v1 = findViewById(R.id.first);
        View v2 = findViewById(R.id.second);
        View v3 = findViewById(R.id.third);
        View v4 = findViewById(R.id.fourth);

        mDelete = (ImageButton) findViewById(R.id.delete);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialVal /= 10;
                digits = digits == 0 ? 0 : digits - 1;
                updateTime();
            }
        });

        mDial = (TextView) findViewById(R.id.dial);

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

        Button b;

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
                    if ((mDialVal == 0 && val == 0) || digits == 11)
                        return;
                    else {
                        mDialVal = mDialVal * 10 + val;
                        digits++;
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
    }

    protected void updateTime() {
        mDial.setText(String.format("%d", mDialVal));
    }

    protected void reset() {
        mDialVal = 0;
        digits = 0;
        updateTime();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent().putExtra("number", String.format("%d", mDialVal));
        setResult(RESULT_OK, i);
        finish();
    }
}
