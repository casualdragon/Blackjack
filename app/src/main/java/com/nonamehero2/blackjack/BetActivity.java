package com.nonamehero2.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amy Hill on 1/31/2017.
 * This activity is used for interactions in the bet activity.
 */

public class BetActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String BET_KEY = "bet";
    public final static String TOTAL = "total";
    private TextView et;
    private TextView total_et;
    private int total;
    private int totalMoney;
    private int totalGameMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        et = (TextView) findViewById(R.id.bet_editText);
        total_et = (TextView) findViewById(R.id.total_textView);
        total = 10000;
        int bet = 0;

        //Checks for an intent sent from the GameActivity
        Intent intent = getIntent();

        if(intent != null && intent.hasExtra(GameActivity.PLAYER)){
            Player user;
            user = (Player) intent.getSerializableExtra(GameActivity.PLAYER);
            total = user.getMoney();
            totalGameMoney = user.getMoney();
            bet = user.getCurrentBet();
            //Debug
            Log.i("===============", "Total: " + Integer.toString(total));
            Log.i("===============", "Bet: " + Integer.toString(bet));
            et.setText(Integer.toString(bet));
        }
        total_et.setText(Integer.toString(total - bet));

        //Setting up the buttons
        findViewById(R.id.add_button).setOnClickListener(this);

        findViewById(R.id.subtract_button).setOnClickListener(this);

        findViewById(R.id.bet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bet = Integer.parseInt(et.getText().toString());
                if(bet != 0) {
                    if(totalGameMoney == 0) {
                        totalGameMoney = Integer.parseInt(total_et.getText().toString());
                    }
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    Log.i("==============", "Total.Bet: " + Integer.toString(totalGameMoney));
                    intent.putExtra(BET_KEY, bet);
                    intent.putExtra(TOTAL, totalGameMoney);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Bet cannot be equal to 0", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void addCheck(int bet){
        if((bet + 100) <= total) {
            bet += 100;
            totalMoney = total - bet;
            et.setText(Integer.toString(bet));
            total_et.setText(Integer.toString((totalMoney)));
        }else{
            Toast.makeText(getApplicationContext(), "The bet cannot exceed the total.",Toast.LENGTH_LONG).show();
        }
    }
    private void subtractCheck(int bet){
        if((bet-100) >= 0)  {
            bet -= 100;
            totalMoney = total - bet;
            et.setText(Integer.toString(bet));
            total_et.setText(Integer.toString((totalMoney)));
        }else{
            Toast.makeText(getApplicationContext(), "The bet cannot be less than 0 or greater than the start total.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int bet = Integer.parseInt(et.getText().toString());
        switch (view.getId()){
            case R.id.add_button:
                addCheck(bet);
                break;
            case R.id.subtract_button:
                subtractCheck(bet);
                break;
            default:
                Toast.makeText(getApplicationContext(), "This button does not exist", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
