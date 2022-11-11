package com.example.myapplication.Gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    Context context;
    List<Item> postList;

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.gallery_cell,parent,false);
        final PostHolder holder= new PostHolder(view);
        //View mView= LayoutInflater.from(context).inflate(R.layout.fragment_gallery_cell,parent,false);
        //return new PostHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Item item = postList.get(position);
        holder.setImageView(item.getImageUrl());
        holder.setmTags(item.getTags());
        holder.setmLikes(item.getLikes());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public PostAdapter(Context context, List<Item> postList) {
        this.context=context;
        this.postList=postList;
    }
    public class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView mLikes,mTags;
        View view;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }
        public void setImageView(String url){
            imageView = view.findViewById(R.id.imageApi);
            Glide.with(context).load(url).into(imageView);
        }
        public void setmLikes(int likes){
            mLikes=view.findViewById(R.id.likes);
            mLikes.setText(likes+" Likes");
        }
        public void setmTags(String tag){
            mTags=view.findViewById(R.id.tags);
            mTags.setText(tag);
        }
    }
}
