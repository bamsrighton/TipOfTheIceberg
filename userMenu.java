package thecompound.brigh.tipsapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMenu extends AppCompatActivity {

    userTotals currUser;
    gigElement currGig;
    int gigId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        if(getIntent().hasExtra("userObj")) {
            currUser = (userTotals) getIntent().getSerializableExtra("userObj");

            TextView userTotal = (TextView) findViewById(R.id.UserTotalCurrent);
            userTotal.setText(currUser.getUserTotalEarnings() + "");

            TextView userName = (TextView) findViewById(R.id.UserName);
            userName.setText(currUser.getUserName());
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(UserMenu.this);
            builder.setMessage("User Object issue")
                    .setNegativeButton("Bummer", null)
                    .create()
                    .show();
        }

        for(int i=0; i<currUser.numberOfGigs; i++){

            Response.Listener<String> resp = new Response.Listener<String>() {

                @Override
                public void onResponse(String response){

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){

                            gigId = jsonResponse.getInt("gig_id");
                            String name = jsonResponse.getString("name");
                            String pos = jsonResponse.getString("position");
                            double gross = jsonResponse.getDouble("grossHourly");
                            double net = jsonResponse.getDouble("netHourly");
                            double gigTotal = jsonResponse.getDouble("totalEarnings");
                            double totalHours = jsonResponse.getDouble("totalHours");
                            String bsDate = jsonResponse.getString("bestShiftDate");
                            double bsTotal = jsonResponse.getDouble("bestShiftTotal");
                            double bsHourly = jsonResponse.getDouble("bestShiftHourly");
                            int numberOfShifts = jsonResponse.getInt("numberOfShifts");

                            currGig = new gigElement(name, pos, gross, gigId);
                            currGig.setNetHourly(net);
                            currGig.setTotalEarnings(gigTotal);
                            currGig.setBestShiftDate(bsDate);
                            currGig.setBestShiftTotal(bsTotal);
                            currGig.setBestShiftHourly(bsHourly);
                            currGig.setTotalHours(totalHours);
                            currGig.setNumberOfShifts(numberOfShifts);

                            currUser.listOfGigs.add(currGig);

                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserMenu.this);
                            builder.setMessage("Gig Info Import Failure")
                                    .setNegativeButton("Bummer..", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            userGigsRequest userGigs = new userGigsRequest(currUser.getUserId(), i, resp);
            RequestQueue q = Volley.newRequestQueue(UserMenu.this);
            q.add(userGigs);

        }


        Button goToBestGigsBtn = (Button) findViewById(R.id.goToCurrGigsBtn);
        goToBestGigsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), ListOfGigs.class);
                startIntent.putExtra("userObj", currUser);
                startActivity(startIntent);
            }
        });

        Button goToNewGigBtn = (Button) findViewById(R.id.goToNewGigBtn);
        goToNewGigBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), addAnewGig.class);
                startIntent.putExtra("userObj", currUser);
                startActivity(startIntent);
            }
        });
    }
}
