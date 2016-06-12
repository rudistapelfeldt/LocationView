 package com.immedia.assessment.locationview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Rudolph on 2016/06/10.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    ArrayList<ImageProfile> mPost;
    Context mContext;
    ImageLoader imageLoader;
    ImageLoader.ImageCache imageCache;

    public ImageAdapter(ArrayList<ImageProfile> post, Context context){
        this.mPost = post;
        this.mContext = context;
        imageLoader = AppController.getInstance().getImageLoader(mContext);
        imageCache = AppController.getInstance().getCache();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mImageView.setImageUrl(mPost.get(position).getUrl(), imageLoader);
            holder.mCaption.setText(mPost.get(position).getCaption());
            holder.mImageView.setTag(position);
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getId();
                    Intent i = new Intent(mContext, ImagePreview.class);
                    int tag = (int)v.getTag();
                    Bitmap bitmap = imageCache.getBitmap(mPost.get(tag).getUrl());
                    i.putExtra("image", bitmap);
                    mContext.startActivity(i);

                }
            });

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private NetworkImageView mImageView;
        private TextView mCaption;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (NetworkImageView)itemView.findViewById(R.id.networkImageView);
            mCaption = (TextView)itemView.findViewById(R.id.tvcaprion);
        }
    }
}


