package com.immedia.assessment.locationview;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rudolph on 2016/06/08.
 */
public class RequestHandler {

    public static void requestInstagram(double lat, double lng){
        if(ApplicationData.getAuthToken() != null) {
            //String url = "https://api.instagram.com/v1/media/search?lat=" + lat + "&lng=" + lng + "&access_token=" + ApplicationData.getAuthToken();
            String testUrl = "https://api.instagram.com/v1/users/self/media/recent/?access_token=" + ApplicationData.getAuthToken();
            Log.i("Login", testUrl);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, testUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //Log.i("LocationView", "Response = "+ response.toString());
                            try {
                                JSONObject jsonObject = response;
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int index = 0; index < jsonArray.length(); index++) {
                                    JSONObject dataId = jsonArray.getJSONObject(index).getJSONObject("location");
                                    String place = dataId.getString("name");
                                    Log.i("LocationView", "@@@@@ place from json = " + place);
                                }
                                //Log.i("LocationView", "location from JSON response: " + place);
                            } catch (JSONException je) {
                                Log.i("LocationView", "JSONException: " + je.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.i("Login", "Login ERROR" + error.getMessage());
                        }
                    });
            AppController.getInstance().addToRequestQueue(jsObjRequest);
        }
    }
}
