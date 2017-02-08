package com.nonamehero2.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amy Hill on 1/31/2017.
 * This activity is used for interactions in the bet activity.
 */

public class BetActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String BET_KEY = "bet";
    private TextView et;
    private TextView total_et;
    private int total;
    private int bet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        et = (TextView) findViewById(R.id.bet_editText);
        total_et = (TextView) findViewById(R.id.total_textView);
        total = 10000;
        bet = 100;

        //Checks for an intent sent from the GameActivity
        Intent intent = getIntent();

        if(intent != null && intent.hasExtra(GameActivity.PLAYER)){
            Player user;
            user = (Player) intent.getSerializableExtra(GameActivity.PLAYER);
            total = user.getMoney();
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
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra(BET_KEY, bet);
                startActivity(intent);
            }
        });
    }
    private void addCheck( int total, int bet){
        bet += 100;
        if(bet <= total) {
            et.setText(Integer.toString(bet));
            total_et.setText(Integer.toString(total - bet));
        }else{
            Toast.makeText(getApplicationContext(), "The bet cannot exceed the total.",Toast.LENGTH_LONG).show();
        }
    }
    private void subtractCheck(int total, int bet){
        bet -= 100;
        if(bet > 0 && bet <= total)  {
            et.setText(Integer.toString(bet));
            total_et.setText(Integer.toString(total + bet));
        }else{
            Toast.makeText(getApplicationContext(), "The bet cannot be less than 0 or greater than the start total.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                addCheck(total, bet);
                break;
            case R.id.subtract_button:
                subtractCheck(total, bet);
                break;
            default:
                Toast.makeText(getApplicationContext(), "This button does not exist", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
