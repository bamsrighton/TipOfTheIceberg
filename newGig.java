package thecompound.brigh.tipsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addAnewGig extends AppCompatActivity {

    static double currGigTotal = 0.0;
    static String currUserTotHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anew_gig);

        if(getIntent().hasExtra("menu.passedUserTotal")){
            TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
            String passedUserTot = getIntent().getExtras().getString("menu.passedUserTotal");
            userTot.setText(passedUserTot);
        }
        if(getIntent().hasExtra("menu.passedUserTotalHours")){
            currUserTotHours = getIntent().getExtras().getString("menu.passedUserTotal");
        }

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backIntent = new Intent(getApplicationContext(), UserMenu.class);
                startActivity(backIntent);

            }
        });

        Button goToBillfoldBtn = (Button) findViewById(R.id.goToCurrGigBtn);
        goToBillfoldBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), Billfold.class);

                EditText hourly = (EditText) findViewById(R.id.wageActual);
                EditText gigName = (EditText) findViewById(R.id.gigName);
                EditText position = (EditText) findViewById(R.id.titleActual);
                TextView currUserTotal = (TextView) findViewById(R.id.UserTotalCurrent);

                String realHourly = hourly.getText().toString();
                String realGigName = gigName.getText().toString();
                String realPosition = position.getText().toString();
                String userCurrTotal = currUserTotal.getText().toString();
                String currGigTotalTxt = currGigTotal + "";

                startIntent.putExtra("new.passedWage", realHourly);
                startIntent.putExtra("new.passedName", realGigName);
                startIntent.putExtra("new.passedPosition", realPosition);
                startIntent.putExtra("new.passedCurrGigTotal", currGigTotalTxt);
                startIntent.putExtra("new.passedCurrUserTotal", userCurrTotal);
                startIntent.putExtra("new.passedCurrUserTotalHours", currUserTotHours);

                startActivity(startIntent);
            }
        });
    }
}