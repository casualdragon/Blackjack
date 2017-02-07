package com.nonamehero2.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amy Hill on 1/31/2017.
 * This activity is used for interactions in the bet activity.
 */

public class BetActivity extends AppCompatActivity {

    public final static String BET_KEY = "bet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        final EditText et = (EditText) findViewById(R.id.bet_editText);
        final TextView total_et = (TextView) findViewById(R.id.total_textView);

        //Setting up the buttons
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.total_textView);
                String sBet = et.getText().toString();
                String sTotal = tv.getText().toString();
                int bet = Integer.parseInt(sBet);
                int total = Integer.parseInt(sTotal);
                    bet += 100;
                if(bet <= total) {
                    et.setText(Integer.toString(bet));
                    total_et.setText(Integer.toString(total + bet));
                }else{
                    Toast.makeText(getApplicationContext(), "The bet cannot exceed the total.",Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.subtract_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.total_textView);
                int bet = Integer.parseInt(et.getText().toString());
                String sTotal = tv.getText().toString();
                int total = Integer.parseInt(sTotal);
                bet -= 100;
                if(bet > 0 || bet < 10000)  {
                    et.setText(Integer.toString(bet));
                    total_et.setText(Integer.toString(total - bet));
                }else{
                    Toast.makeText(getApplicationContext(), "The bet cannot be less than 0 or greater than the start total.",Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.bet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bet = Integer.parseInt(et.getText().toString());
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra(BET_KEY, bet);
                startActivity(intent);
            }
        });
    }
}
