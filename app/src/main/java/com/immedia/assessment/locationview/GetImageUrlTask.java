package com.immedia.assessment.locationview;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rudolph on 2016/06/10.
 */
public class GetImageUrlTask extends AsyncTask<String, Void, String[]> {

    public AsyncUrlResponse delegate = null;
    public GetImageUrlTask(AsyncUrlResponse response)
    {
        super();
        delegate = response;
    }

    @Override
    protected String[] doInBackground(String... params) {
        String url = params[0];
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RESPONSE", "Get Url Response = " + response.toString());
                        try {
                            JSONObject jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int index = 0; index < jsonArray.length(); index++) {
                                JSONObject dataId = jsonArray.getJSONObject(index);
                                JSONObject images = dataId.getJSONObject("images");
                                JSONObject thumbnail = images.getJSONObject("thumbnail");
                                String url = thumbnail.getString("url");
                                Log.i("URL", url);
                            }

                        } catch (JSONException je) {
                            Log.i("COUNT", "JSONException: " + je.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("COUNT", "Login ERROR" + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
        return new String[0];
    }
}
