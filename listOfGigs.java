package thecompound.brigh.tipsapp;

import android.content.Intent;
import android.sax.TextElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Formatter;

public class ListOfGigs extends AppCompatActivity {

    userTotals currUser;
    int gigNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_gigs);

        gigNumber = 0;

        final TextView userName = (TextView) findViewById(R.id.UserName);
        final TextView userTot = (TextView) findViewById(R.id.UserTotalCurrent);
        final TextView userHours = (TextView) findViewById(R.id.userHoursActual);
        final TextView userNumOfGigs = (TextView) findViewById(R.id.userGigs);
        final TextView gigName = (TextView) findViewById(R.id.gigName);
        final TextView gigTitle = (TextView) findViewById(R.id.gigTitle);
        final TextView gigAvg = (TextView) findViewById(R.id.gigAverageActual);
        final TextView bestShiftHourly = (TextView) findViewById(R.id.bestShiftHourly);
        final TextView bestShiftTotal = (TextView) findViewById(R.id.bestShiftTotal);
        final TextView bestShiftDate = (TextView) findViewById(R.id.bestShiftDate);
        final TextView totHours = (TextView) findViewById(R.id.totalHoursActual);

        if(getIntent().hasExtra("userObj")){

            currUser = (userTotals) getIntent().getSerializableExtra("userObj");

            userName.setText(currUser.getUserName());
            userTot.setText(currUser.getUserTotalEarnings() + "");
            userHours.setText(currUser.getUserTotalHours() + "");
            userNumOfGigs.setText(currUser.numberOfGigs + "");
            gigName.setText(currUser.listOfGigs.get(gigNumber).name);
            gigTitle.setText(currUser.listOfGigs.get(gigNumber).pos);
            totHours.setText(currUser.listOfGigs.get(gigNumber).getTotalHours() + "");
            bestShiftDate.setText(currUser.listOfGigs.get(gigNumber).getBestShiftDate());

            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format("%.2f", currUser.listOfGigs.get(gigNumber).getNetHourly());
            String formattedNetHourly = sbuf.toString();
            gigAvg.setText(formattedNetHourly);

            StringBuilder sbuf1 = new StringBuilder();
            Formatter fmt1 = new Formatter(sbuf1);
            fmt1.format("%.2f", currUser.listOfGigs.get(gigNumber).getBestShiftHourly());
            String formattedBestShiftHourly = sbuf1.toString();
            bestShiftHourly.setText(formattedBestShiftHourly);

            StringBuilder sbuf2 = new StringBuilder();
            Formatter fmt2 = new Formatter(sbuf2);
            fmt2.format("%.2f", currUser.listOfGigs.get(gigNumber).getBestShiftTotal());
            String formattedBestShiftTotal = sbuf2.toString();
            bestShiftTotal.setText(formattedBestShiftTotal);
        }

        Button goToNextGigBtn = (Button) findViewById(R.id.nextGigBtn);
        goToNextGigBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(gigNumber < currUser.numberOfGigs)
                    gigNumber++;
                else {
                    gigNumber=1;
                    Toast myToast = Toast.makeText(getApplicationContext(),
                            "No More Gigs!", Toast.LENGTH_LONG);
                    myToast.show();
                }

                userTot.setText(currUser.getUserTotalEarnings() + "");
                userHours.setText(currUser.getUserTotalHours() + "");
                userNumOfGigs.setText(currUser.numberOfGigs + "");
                gigName.setText(currUser.listOfGigs.get(gigNumber).name);
                gigTitle.setText(currUser.listOfGigs.get(gigNumber).pos);
                totHours.setText(currUser.listOfGigs.get(gigNumber).getTotalHours() + "");
                bestShiftDate.setText(currUser.listOfGigs.get(gigNumber).getBestShiftDate());

                StringBuilder sbuf = new StringBuilder();
                Formatter fmt = new Formatter(sbuf);
                fmt.format("%.2f", currUser.listOfGigs.get(gigNumber).getNetHourly());
                String formattedNetHourly = sbuf.toString();
                gigAvg.setText(formattedNetHourly);

                StringBuilder sbuf1 = new StringBuilder();
                Formatter fmt1 = new Formatter(sbuf1);
                fmt1.format("%.2f", currUser.listOfGigs.get(gigNumber).getBestShiftHourly());
                String formattedBestShiftHourly = sbuf1.toString();
                bestShiftHourly.setText(formattedBestShiftHourly);

                StringBuilder sbuf2 = new StringBuilder();
                Formatter fmt2 = new Formatter(sbuf2);
                fmt2.format("%.2f", currUser.listOfGigs.get(gigNumber).getBestShiftTotal());
                String formattedBestShiftTotal = sbuf2.toString();
                bestShiftTotal.setText(formattedBestShiftTotal);
            }
        });

        Button goToCurrGigBtn = (Button) findViewById(R.id.goToCurrGigBtn);
        goToCurrGigBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), Billfold.class);

                startIntent.putExtra("userObj", currUser);
                startIntent.putExtra("gigId", gigNumber + "");

                startActivity(startIntent);
            }
        });
    }
}
