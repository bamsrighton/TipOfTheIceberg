package thecompound.brigh.tipsapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class userLogin extends AppCompatActivity {

    userTotals currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Button registerBtn = (Button) findViewById(R.id.userRegistrationBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), userRegister.class);
                startActivity(intent);
            }
        });

        Button loginBtn = (Button) findViewById(R.id.userLoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText usernameEditText = (EditText) findViewById(R.id.usernameActual);
                EditText passwordEditText = (EditText) findViewById(R.id.passwordActual);

                final String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Response.Listener<String> resp = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response){

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent startIntent = new Intent(userLogin.this, UserMenu.class);

                                currUser = new userTotals(username);
                                currUser.setUserId(jsonResponse.getInt("user_id"));
                                currUser.setTotalHours(jsonResponse.getDouble("totalHours"));
                                currUser.setTotalEarnings(jsonResponse.getDouble("totalEarnings"));
                                currUser.setNetHourly(jsonResponse.getDouble("netHourly"));
                                currUser.setNumberOfGigs(jsonResponse.getInt("numberOfGigs"));

                                startIntent.putExtra("userObj", currUser);
                                startActivity(startIntent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(userLogin.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                loginRequest login = new loginRequest(username, password, resp);
                RequestQueue q = Volley.newRequestQueue(userLogin.this);
                q.add(login);
            }
        });
    }
}