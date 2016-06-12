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

import java.util.ArrayList;

/**
 * Created by Rudolph on 2016/06/10.
 */
public class GetImageUrlTask extends AsyncTask<String, Void, ArrayList<ImageProfile>> {

    public AsyncUrlResponse delegate = null;
    public GetImageUrlTask(AsyncUrlResponse response)
    {
        super();
        delegate = response;
    }

    @Override
    protected void onPostExecute(ArrayList<ImageProfile> image) {
        super.onPostExecute(image);
        delegate.processFinish(image);
    }

    @Override
    protected ArrayList<ImageProfile> doInBackground(String... params) {
        final ArrayList<ImageProfile> image = new ArrayList<ImageProfile>();
        String url = params[0];
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int index = 0; index < jsonArray.length(); index++) {
                                ImageProfile imageProfile = new ImageProfile();
                                JSONObject dataId = jsonArray.getJSONObject(index);
                                JSONObject images = dataId.getJSONObject("images");
                                JSONObject caption = dataId.getJSONObject("caption");
                                String cap = caption.getString("text");
                                JSONObject thumbnail = images.getJSONObject("thumbnail");
                                String url = thumbnail.getString("url");
                                imageProfile.setCaption(cap);
                                imageProfile.setUrl(url);
                                image.add(imageProfile);

                            }
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                        GetImageUrlTask.this.onPostExecute(image);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
        return image;
    }
}
