package thecompound.brigh.tipsapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brigh on 11/25/2017.
 */

public class loginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://bamsrighton.net/login.php";
    private Map<String, String> params;

    public loginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
