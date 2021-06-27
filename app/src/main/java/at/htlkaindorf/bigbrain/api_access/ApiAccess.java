package at.htlkaindorf.bigbrain.api_access;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the requests
 * @version BigBrain v1
 * @since 18.05.2021
 * @author Nico Pessnegger
 */
public class ApiAccess {
    private JsonResponseListener jrl;

    // To send a request to the server
    public JSONObject getData(String url, Context context, JSONObject body, JsonResponseListener jrl, int method){
       this. jrl = jrl;
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    jrl.onSuccessJson(response);
                }
                else {
                    Log.i("Your Array Response", "Data Null");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
            }
        }) {

            // to use json
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            // The values you post to the server
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return null;
    }

    // Same as abouve, but without the body
    public JSONObject getData(String url, Context context, JsonResponseListener jrl, int method){
        this. jrl = jrl;
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            //JSONObject jObject;

            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    jrl.onSuccessJson(response);
                }
                else {
                    Log.i("Your Array Response", "Data Null");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
            }
        }) {

            // using Json and not String
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

        return null;
    }
}
