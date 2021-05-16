package com.app.manoranjan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.manoranjan.R;
import com.app.manoranjan.interfaces.StarCastAdapterListener;
import com.app.manoranjan.models.MovieCreditsResponse;
import com.app.manoranjan.utils.Helper;

import java.util.List;


public class CastResponseAdapter extends RecyclerView.Adapter<CastResponseAdapter.ViewHolder> {
    //Field Declaration
    Context mContext;
    LayoutInflater inflater;
    List<MovieCreditsResponse.Cast> mResponse;
    StarCastAdapterListener mListener;
    int lastItemWithAnimation;
    boolean isLastItemLayoutShowing;

    public CastResponseAdapter(Context context, StarCastAdapterListener listener, List<MovieCreditsResponse.Cast> response) {
        mContext = context;
        mResponse = response;
        mListener = listener;
        lastItemWithAnimation = -1;
        isLastItemLayoutShowing = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.people_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            mListener.lastItemReached(R.id.top_billed_cast_RecyclerView);
            isLastItemLayoutShowing = true;
        } else if (isLastItemLayoutShowing) {
            mListener.hideNoMoreItemsLayout(R.id.top_billed_cast_RecyclerView);
            isLastItemLayoutShowing = false;
        }
        MovieCreditsResponse.Cast currentItem = mResponse.get(position);
        //setting person_img
        setInFadeAnimation(position, holder.itemView);
        Helper.setW500Image(currentItem.getProfile_path(), holder.personImg);
        Helper.setText(currentItem.getName(), holder.personName, true);
        Helper.setText(currentItem.getCharacter(), holder.character, true);
    }

    private void setInFadeAnimation(int position, View view) {
        if (position <= lastItemWithAnimation)
            return;
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
        lastItemWithAnimation = position;
    }

    @Override
    public int getItemCount() {
        return mResponse.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //Field declaration
        ImageView personImg;
        TextView personName;
        TextView character;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personImg = itemView.findViewById(R.id.person_img);
            personName = itemView.findViewById(R.id.person_name);
            character = itemView.findViewById(R.id.description);
        }
    }
}
