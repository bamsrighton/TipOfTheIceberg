package thecompound.brigh.tipsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Formatter;

public class Billfold extends AppCompatActivity {

    static double currUserTotal;
    static double currGigAvgWage;
    static double gigTotalHours;
    static String[] listOfShiftTotals; // ONE INDEXED ARRAY of shift totals
    static String[] listOfShiftDates; // ONE INDEXED ARRAY of shift dates
    static String[] listOfShiftHours; // ONE INDEXED ARRAY of shift lengths
    static int numberOfShifts;
    static double bestShift;
    static String formattedBestShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billfold);

        currGigAvgWage = 0.0;
        gigTotalHours = 0.0;
        listOfShiftTotals = new String[100];
        listOfShiftDates = new String[100];
        numberOfShifts = 0;
        bestShift = 0.0;

        if(getIntent().hasExtra("new.passedCurrUserTotal")){
            TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
            String passedUserTot = getIntent().getExtras().getString("new.passedCurrUserTotal");
            currUserTotal = Double.parseDouble(passedUserTot);
            userTot.setText(passedUserTot);
        }
        if(getIntent().hasExtra("new.passedName")){
            TextView gigName = (TextView) findViewById(R.id.gigName);
            String passedGigName = getIntent().getExtras().getString("new.passedName");
            gigName.setText(passedGigName);
        }
        if(getIntent().hasExtra("new.passedPosition")){
            TextView jobTitle = (TextView) findViewById(R.id.gigTitle);
            String passedGigTitle = getIntent().getExtras().getString("new.passedPosition");
            jobTitle.setText(passedGigTitle);
        }
        if(getIntent().hasExtra("new.passedWage")) {
            TextView hourly = (TextView) findViewById(R.id.grossHourlyActual);
            String passedHourly = getIntent().getExtras().getString("new.passedWage");
            hourly.setText(passedHourly);
        }
        if(getIntent().hasExtra("new.passedCurrGigTotal")) {
            TextView total = (TextView) findViewById(R.id.gigCurrTotal);
            String passedTotal = getIntent().getExtras().getString("new.passedCurrGigTotal");
            total.setText(passedTotal);
        }

        if(getIntent().hasExtra("list.passedBestShift")) {
            bestShift = Double.parseDouble(getIntent().getExtras().getString("list.passedBestShift"));
        }
        if(getIntent().hasExtra("list.passedTotalGigHours")) {
            gigTotalHours = Double.parseDouble(getIntent().getExtras().getString("list.passedTotalGigHours"));
        }
        if(getIntent().hasExtra("list.passedCurrUserTotal")){
            TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
            String passedUserTot = getIntent().getExtras().getString("list.passedCurrUserTotal");
            currUserTotal = Double.parseDouble(passedUserTot);
            userTot.setText(passedUserTot);
        }
        if(getIntent().hasExtra("list.passedName")){
            TextView gigName = (TextView) findViewById(R.id.gigName);
            String passedGigName = getIntent().getExtras().getString("list.passedName");
            gigName.setText(passedGigName);
        }
        if(getIntent().hasExtra("list.passedNetWage")) {
            TextView netHourly = (TextView) findViewById(R.id.avgNetWageActual);
            String passedHourly = getIntent().getExtras().getString("list.passedNetWage");
            netHourly.setText(passedHourly);
        }
        if(getIntent().hasExtra("list.passedWage")) {
            TextView hourly = (TextView) findViewById(R.id.grossHourlyActual);
            String passedHourly = getIntent().getExtras().getString("list.passedWage");
            hourly.setText(passedHourly);
        }
        if(getIntent().hasExtra("list.passedCurrGigTotal")) {
            TextView total = (TextView) findViewById(R.id.gigCurrTotal);
            String passedTotal = getIntent().getExtras().getString("list.passedCurrGigTotal");
            total.setText(passedTotal);
        }

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backIntent = new Intent(getApplicationContext(), UserMenu.class);
                TextView gigName = (TextView) findViewById(R.id.gigName);
                TextView gigTitle = (TextView) findViewById(R.id.gigTitle);
                TextView hourly = (TextView) findViewById(R.id.grossHourlyActual);
                TextView currGigTotal = (TextView) findViewById(R.id.gigCurrTotal);
                TextView currGigHourly = (TextView) findViewById(R.id.avgNetWageActual);
                TextView currUserTot = (TextView) findViewById(R.id.UserTotalCurrent);

                if(getIntent().hasExtra("new.passedPosition")) {
                    String passedPosition = getIntent().getExtras().getString("new.passedPosition");
                    backIntent.putExtra("billfold.passedPosition", passedPosition);
                }
                backIntent.putExtra("billfold.passedCurrUserTotal", currUserTot.getText().toString());
                backIntent.putExtra("billfold.passedName", gigName.getText().toString());
                backIntent.putExtra("billfold.passedPosition", gigTitle.getText().toString());
                backIntent.putExtra("billfold.passedWage", hourly.getText().toString());
                backIntent.putExtra("billfold.passedCurrGigTotal", currGigTotal.getText().toString());
                backIntent.putExtra("billfold.passedNetWage", currGigHourly.getText().toString());
                backIntent.putExtra("billfold.passedBestShift", formattedBestShift);
                backIntent.putExtra("billfold.passedTotalGigHours", gigTotalHours + "");

                startActivity(backIntent);
            }
        });

        Button addShift = (Button) findViewById(R.id.addShiftBtn);
        addShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numberOfShifts++;
                EditText hours = (EditText) findViewById(R.id.hoursActual);
                EditText tips = (EditText) findViewById(R.id.tipsActual);
                EditText day = (EditText) findViewById(R.id.dayEditTxt);
                EditText month = (EditText) findViewById(R.id.monthEditTxt);
                EditText year = (EditText) findViewById(R.id.yearEditTxt);

                TextView hourly = (TextView) findViewById(R.id.grossHourlyActual);
                TextView dailyTotal = (TextView) findViewById(R.id.dailyTotalActual);
                TextView dailyNet = (TextView) findViewById(R.id.dailyNetWageActual);
                TextView currTotal = (TextView) findViewById(R.id.gigCurrTotal);
                TextView avgNetWage = (TextView) findViewById(R.id.avgNetWageActual);
                TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
                TextView date = (TextView) findViewById(R.id.date);

                String shiftDate = month.getText().toString() + " / " + day.getText().toString() + " / " + year.getText().toString();
                double tipsActual = Double.parseDouble(tips.getText().toString());
                double hoursActual = Double.parseDouble(hours.getText().toString());
                gigTotalHours += hoursActual;
                double dailyTot = (Double.parseDouble(hourly.getText().toString()) * hoursActual) + tipsActual;
                double currGigTot = Double.parseDouble(currTotal.getText().toString()) + dailyTot;
                currUserTotal = Double.parseDouble(userTot.getText().toString()) + dailyTot;
                double currGigWage = dailyTot / hoursActual;
                listOfShiftTotals[numberOfShifts] = dailyTot + "";
                listOfShiftDates[numberOfShifts] = shiftDate;

                double stuff = 0;
                for(int i=1; i <= numberOfShifts; i++){
                    stuff += Double.parseDouble(listOfShiftTotals[i]);
                    if(currGigWage > bestShift)
                        bestShift = currGigWage;
                }
                currGigAvgWage = stuff / gigTotalHours;

                StringBuilder sbuf = new StringBuilder();
                Formatter fmt = new Formatter(sbuf);
                fmt.format("%.2f", currGigWage);
                String formattedDailyWage = sbuf.toString();

                StringBuilder sbuf1 = new StringBuilder();
                Formatter fmt1 = new Formatter(sbuf1);
                fmt1.format("%.2f", currGigAvgWage);
                String formattedAvgWage = sbuf1.toString();

                StringBuilder sbuf2 = new StringBuilder();
                Formatter fmt2 = new Formatter(sbuf2);
                fmt2.format("%.2f", dailyTot);
                String formattedDailyTot = sbuf2.toString();

                StringBuilder sbuf3 = new StringBuilder();
                Formatter fmt3 = new Formatter(sbuf3);
                fmt3.format("%.2f", currGigTot);
                String formattedGigTot = sbuf3.toString();

                StringBuilder sbuf4 = new StringBuilder();
                Formatter fmt4 = new Formatter(sbuf4);
                fmt4.format("%.2f", currUserTotal);
                String formattedUserTot = sbuf4.toString();

                StringBuilder sbuf5 = new StringBuilder();
                Formatter fmt5 = new Formatter(sbuf5);
                fmt5.format("%.2f", bestShift);
                formattedBestShift = sbuf5.toString();

                date.setText(shiftDate);
                userTot.setText(formattedUserTot);
                dailyTotal.setText(formattedDailyTot);
                dailyNet.setText(formattedDailyWage);
                currTotal.setText(formattedGigTot);
                avgNetWage.setText(formattedAvgWage);
            }
        });
    }
}