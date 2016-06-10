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

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rudolph on 2016/06/09.
 */
public class GetLocationsTask extends AsyncTask<String, Void, HashMap<Integer, String>> {
    private HashMap<Integer, String> h;
    private List<String> list;
    public AsyncResponse delegate = null;
    public GetLocationsTask(AsyncResponse response)
    {
        super();
        delegate = response;
    }

    @Override
    protected void onPostExecute(HashMap<Integer, String> h) {
        super.onPostExecute(h);
        delegate.processFinish(h);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<Integer, String> doInBackground(String... params) {
        final HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
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
                                        Log.i("COUNT", "@@@@@@@@@@@@ " + dataId.getInt("id") + ", " + dataId.getString("name"));
                                        hashMap.put(dataId.getInt("id"), dataId.getString("name"));
                                    }
                                    GetLocationsTask.this.onPostExecute(hashMap);

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
            }
        }
        return hashMap;
    }
}
