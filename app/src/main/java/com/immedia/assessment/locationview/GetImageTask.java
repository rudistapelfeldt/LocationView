package com.immedia.assessment.locationview;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

/**
 * Created by Rudolph on 2016/06/10.
 */
public class GetImageTask extends AsyncTask<String, Void, Object> {
    public AsyncLocationResponse delegate = null;
    public GetImageTask(AsyncLocationResponse response)
    {
        super();
        delegate = response;
    }
    @Override
    protected Object doInBackground(String... params) {
        for (int i = 0; i < params.length; i++) {
            String url = (String)params[i];
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            AppController.getInstance().addToRequestQueue(request);
        }
        return null;
    }
}
