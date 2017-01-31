package com.nonamehero2.blackjack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity is used for interactions in the bet activity.
 */

public class BetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);





        //Setting up the add and subtract buttons
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.bet_editText);
                TextView tv = (TextView) findViewById(R.id.total_textView);
                String sBet = et.getText().toString();
                Log.i("=================", "Bet " + sBet);
                String sTotal = tv.getText().toString();
                int bet = Integer.parseInt(sBet);
                int total = Integer.parseInt(sTotal);
                    bet += 100;
                if(bet <= total) {
                    et.setText(new Integer(bet).toString());
                }else{
                    Toast.makeText(getApplicationContext(), "The bet cannot exceed the total.",Toast.LENGTH_LONG);
                }
            }
        });

        findViewById(R.id.subtract_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.bet_editText);
                int bet = Integer.parseInt(et.getText().toString());
                bet -= 100;
                if(bet > 0) {
                    et.setText(new Integer(bet).toString());
                }else{
                    Toast.makeText(getApplicationContext(), "The bet cannot be less than 0.",Toast.LENGTH_LONG);
                }
            }
        });
    }
}
