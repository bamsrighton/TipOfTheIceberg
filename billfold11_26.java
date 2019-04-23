package thecompound.brigh.tipsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class Billfold extends AppCompatActivity {

    int gigId;
    userTotals currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billfold);

        gigId = 1;

        if(getIntent().hasExtra("userObj")){
            currUser = (userTotals) getIntent().getSerializableExtra("userObj");
/*
            TextView userName = (TextView) findViewById(R.id.UserName);
            userName.setText(currUser.getUserName());
            TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
            userTot.setText(currUser.getUserTotalEarnings() + "");
            TextView gigName = (TextView) findViewById(R.id.gigName);
            gigName.setText(currUser.listOfGigs[gigId].name);
            TextView gigTitle = (TextView) findViewById(R.id.gigTitle);
            gigTitle.setText(currUser.listOfGigs[gigId].pos);

            TextView grossHourly = (TextView) findViewById(R.id.grossHourlyActual);
            grossHourly.setText(currUser.listOfGigs[gigId].grossHourly + "");
            TextView gigCurrTotal = (TextView) findViewById(R.id.gigCurrTotal);
            gigCurrTotal.setText(currUser.listOfGigs[gigId].getTotalEarnings() + "");
            TextView avgNetWageActual = (TextView) findViewById(R.id.avgNetWageActual);
            avgNetWageActual.setText(currUser.listOfGigs[gigId].netHourly + "");
            */
        }

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backIntent = new Intent(getApplicationContext(), UserMenu.class);

                backIntent.putExtra("userObj", currUser);
                startActivity(backIntent);
            }
        });

        Button addPastShift = (Button) findViewById(R.id.addPastShiftBtn);
        addPastShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currUser.listOfGigs[gigId].numberOfShifts++;
                int shiftId = currUser.listOfGigs[gigId].numberOfShifts;
                EditText hours = (EditText) findViewById(R.id.hoursActual);
                EditText tips = (EditText) findViewById(R.id.tipsActual);
                EditText day = (EditText) findViewById(R.id.dayEditTxt);
                EditText month = (EditText) findViewById(R.id.monthEditTxt);
                EditText year = (EditText) findViewById(R.id.yearEditTxt);

                TextView dailyTotal = (TextView) findViewById(R.id.dailyTotalActual);
                TextView dailyNet = (TextView) findViewById(R.id.dailyNetWageActual);
                TextView currTotal = (TextView) findViewById(R.id.gigCurrTotal);
                TextView avgNetWage = (TextView) findViewById(R.id.avgNetWageActual);
                TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
                TextView date = (TextView) findViewById(R.id.date);

                if ( !hours.getText().toString().matches("") ) {
                    if ( !tips.getText().toString().matches("") ) {
                        if (!day.getText().toString().matches("") && !month.getText().toString().matches("") && !year.getText().toString().matches("")) {
                            if (Double.parseDouble(hours.getText().toString()) > 0 && Double.parseDouble(hours.getText().toString()) < 24) {
                                if (Double.parseDouble(tips.getText().toString()) >= 0) {
                                    if (Integer.parseInt(month.getText().toString()) > 0 && Integer.parseInt(month.getText().toString()) < 13) {
                                        if (Integer.parseInt(day.getText().toString()) > 0 && Integer.parseInt(day.getText().toString()) < 32) {

                                            double tipsActual = Double.parseDouble(tips.getText().toString());
                                            double hoursActual = Double.parseDouble(hours.getText().toString());

                                            // date format: MM/DD/YYYY
                                            String shiftDate = month.getText().toString() + "/" + day.getText().toString() + "/" + year.getText().toString();
                                            shiftElement newShift = new shiftElement(currUser.listOfGigs[gigId], hoursActual,
                                                    tipsActual, shiftDate, shiftId);

                                            currUser.listOfGigs[gigId].addToListOfShifts(newShift);
                                            currUser.addToTotalHours(hoursActual);

                                            double dailyTot = (currUser.listOfGigs[gigId].grossHourly * hoursActual) + tipsActual;
                                            double dailyWage = dailyTot / hoursActual;
                                            currUser.listOfGigs[gigId].addToTotalHours(hoursActual);
                                            currUser.listOfGigs[gigId].addToTotalEarnings(dailyTot);
                                            currUser.addToTotalEarnings(dailyTot);

                                            if (dailyWage > currUser.listOfGigs[gigId].getBestShiftHourly()) {
                                                currUser.listOfGigs[gigId].setBestShiftHourly(dailyWage);
                                                currUser.listOfGigs[gigId].setBestShiftTotal(dailyTot);
                                                currUser.listOfGigs[gigId].setBestShiftDate(shiftDate);
                                            }

                                            StringBuilder sbuf = new StringBuilder();
                                            Formatter fmt = new Formatter(sbuf);
                                            fmt.format("%.2f", dailyTot);
                                            String formattedDailyTot = sbuf.toString();

                                            StringBuilder sbuf1 = new StringBuilder();
                                            Formatter fmt1 = new Formatter(sbuf1);
                                            fmt1.format("%.2f", dailyWage);
                                            String formattedDailyNet = sbuf1.toString();

                                            StringBuilder sbuf2 = new StringBuilder();
                                            Formatter fmt2 = new Formatter(sbuf2);
                                            fmt2.format("%.2f", currUser.listOfGigs[gigId].getTotalEarnings());
                                            String formattedGigTot = sbuf2.toString();

                                            StringBuilder sbuf3 = new StringBuilder();
                                            Formatter fmt3 = new Formatter(sbuf3);
                                            fmt3.format("%.2f", currUser.listOfGigs[gigId].getNetHourly());
                                            String formattedAvgNetWage = sbuf3.toString();

                                            StringBuilder sbuf4 = new StringBuilder();
                                            Formatter fmt4 = new Formatter(sbuf4);
                                            fmt4.format("%.2f", currUser.getUserTotalEarnings());
                                            String formattedUserTot = sbuf4.toString();

                                            dailyTotal.setText(formattedDailyTot);
                                            dailyNet.setText(formattedDailyNet);
                                            currTotal.setText(formattedGigTot);
                                            avgNetWage.setText(formattedAvgNetWage);
                                            userTot.setText(formattedUserTot);
                                            date.setText(shiftDate);

                                        } else {
                                            Toast myToast = Toast.makeText(getApplicationContext(),
                                                    "   Invalid Day\n Shift NOT added!", Toast.LENGTH_LONG);
                                            myToast.show();
                                        }
                                    } else {
                                        Toast myToast = Toast.makeText(getApplicationContext(),
                                                "   Invalid Month\nShift NOT added!", Toast.LENGTH_LONG);
                                        myToast.show();
                                    }
                                } else {
                                    Toast myToast = Toast.makeText(getApplicationContext(),
                                            "    Invalid Tips\nShift NOT added!", Toast.LENGTH_LONG);
                                    myToast.show();
                                }
                            } else {
                                Toast myToast = Toast.makeText(getApplicationContext(),
                                        "   Invalid Hours\nShift NOT added!", Toast.LENGTH_LONG);
                                myToast.show();
                            }
                        }
                        else {
                            Toast myToast = Toast.makeText(getApplicationContext(),
                                    "       No Date\nShift NOT added!", Toast.LENGTH_LONG);
                            myToast.show();
                        }
                    }
                    else {
                        Toast myToast = Toast.makeText(getApplicationContext(),
                                "      No Tips\nShift NOT added!", Toast.LENGTH_LONG);
                        myToast.show();
                    }
                }
                else {
                    Toast myToast = Toast.makeText(getApplicationContext(),
                            "      No Hours\nShift NOT added!", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });

        Button today = (Button) findViewById(R.id.todayBtn);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currUser.listOfGigs[gigId].numberOfShifts++;
                int shiftId = currUser.listOfGigs[gigId].numberOfShifts;
                EditText hours = (EditText) findViewById(R.id.hoursActual);
                EditText tips = (EditText) findViewById(R.id.tipsActual);

                TextView dailyTotal = (TextView) findViewById(R.id.dailyTotalActual);
                TextView dailyNet = (TextView) findViewById(R.id.dailyNetWageActual);
                TextView currTotal = (TextView) findViewById(R.id.gigCurrTotal);
                TextView avgNetWage = (TextView) findViewById(R.id.avgNetWageActual);
                TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
                TextView currDate = (TextView) findViewById(R.id.date);

                if ( !hours.getText().toString().matches("") ) {
                    if (!tips.getText().toString().matches("")) {
                        if (Double.parseDouble(hours.getText().toString()) > 0 && Double.parseDouble(hours.getText().toString()) < 24) {
                            if (Double.parseDouble(tips.getText().toString()) >= 0) {

                                double tipsActual = Double.parseDouble(tips.getText().toString());
                                double hoursActual = Double.parseDouble(hours.getText().toString());

                                // date format: MM/DD/YYYY
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                                Date date = new Date();

                                String shiftDate = dateFormat.format(date);
                                shiftElement newShift = new shiftElement(currUser.listOfGigs[gigId], hoursActual,
                                        tipsActual, shiftDate, shiftId);

                                currUser.listOfGigs[gigId].addToListOfShifts(newShift);
                                currUser.addToTotalHours(hoursActual);

                                double dailyTot = (currUser.listOfGigs[gigId].grossHourly * hoursActual) + tipsActual;
                                double dailyWage = dailyTot / hoursActual;
                                currUser.listOfGigs[gigId].addToTotalHours(hoursActual);
                                currUser.listOfGigs[gigId].addToTotalEarnings(dailyTot);
                                currUser.addToTotalEarnings(dailyTot);

                                if (dailyWage > currUser.listOfGigs[gigId].getBestShiftHourly()) {
                                    currUser.listOfGigs[gigId].setBestShiftHourly(dailyWage);
                                    currUser.listOfGigs[gigId].setBestShiftTotal(dailyTot);
                                    currUser.listOfGigs[gigId].setBestShiftDate(shiftDate);
                                }

                                StringBuilder sbuf = new StringBuilder();
                                Formatter fmt = new Formatter(sbuf);
                                fmt.format("%.2f", dailyTot);
                                String formattedDailyTot = sbuf.toString();

                                StringBuilder sbuf1 = new StringBuilder();
                                Formatter fmt1 = new Formatter(sbuf1);
                                fmt1.format("%.2f", dailyWage);
                                String formattedDailyNet = sbuf1.toString();

                                StringBuilder sbuf2 = new StringBuilder();
                                Formatter fmt2 = new Formatter(sbuf2);
                                fmt2.format("%.2f", currUser.listOfGigs[gigId].getTotalEarnings());
                                String formattedGigTot = sbuf2.toString();

                                StringBuilder sbuf3 = new StringBuilder();
                                Formatter fmt3 = new Formatter(sbuf3);
                                fmt3.format("%.2f", currUser.listOfGigs[gigId].getNetHourly());
                                String formattedAvgNetWage = sbuf3.toString();

                                StringBuilder sbuf4 = new StringBuilder();
                                Formatter fmt4 = new Formatter(sbuf4);
                                fmt4.format("%.2f", currUser.getUserTotalEarnings());
                                String formattedUserTot = sbuf4.toString();

                                dailyTotal.setText(formattedDailyTot);
                                dailyNet.setText(formattedDailyNet);
                                currTotal.setText(formattedGigTot);
                                avgNetWage.setText(formattedAvgNetWage);
                                userTot.setText(formattedUserTot);
                                currDate.setText(shiftDate);
                            }
                            else {
                                Toast myToast = Toast.makeText(getApplicationContext(),
                                        "       Invalid Tips\nShift NOT added!", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        }
                        else {
                            Toast myToast = Toast.makeText(getApplicationContext(),
                                    "    Invalid Hours\nShift NOT added!", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    }
                    else {
                        Toast myToast = Toast.makeText(getApplicationContext(),
                                "       No Tips\nShift NOT added!", Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                }
                else {
                    Toast myToast = Toast.makeText(getApplicationContext(),
                            "      No Hours\nShift NOT added!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }
}
