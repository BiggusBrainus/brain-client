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

public class ApiAccess {
    private JsonResponseListener jrl;

    public JSONObject getData(String url, Context context, JSONObject body, Map<String, String> parameters, JsonResponseListener jrl){
       this. jrl = jrl;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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

            //Pass Your Parameters
            @Override
            protected Map<String, String> getParams() {
                return parameters;
            }

            // using Json and not String
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
}
