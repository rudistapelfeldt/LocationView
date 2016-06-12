package com.immedia.assessment.locationview;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rudolph on 2016/06/09.
 */
public class GetLocationsTask extends AsyncTask<String, Void, HashMap<String, Integer>> {
    private HashMap<Integer, String> h;
    private List<String> list;
    public AsyncLocationResponse delegate = null;

    public GetLocationsTask(AsyncLocationResponse response){
        super();
        delegate = response;
    }

    @Override
    protected void onPostExecute(HashMap<String, Integer> h) {
        super.onPostExecute(h);
        delegate.processFinish(h);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, Integer> doInBackground(String... params) {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for(int i =0; i < params.length; i++){
            String url = params[i];
            if(ApplicationData.getAuthToken() != null) {
                        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject jsonObject = response;
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int index = 0; index < jsonArray.length(); index++) {
                                        JSONObject dataId = jsonArray.getJSONObject(index);
                                        hashMap.put(dataId.getString("name"), dataId.getInt("id"));
                                    }
                                    GetLocationsTask.this.onPostExecute(hashMap);
                                } catch (JSONException je) {
                                    je.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.getMessage();
                            }
                        });
                AppController.getInstance().addToRequestQueue(jsObjRequest);
            }
        }
        return hashMap;
    }
}
