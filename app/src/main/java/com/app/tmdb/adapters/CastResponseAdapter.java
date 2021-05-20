package com.app.tmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.interfaces.StarCastAdapterListener;
import com.app.tmdb.models.MovieCreditsResponse;
import com.app.tmdb.utils.Helper;

import java.util.List;


public class CastResponseAdapter extends RecyclerView.Adapter<CastResponseAdapter.ViewHolder> {
    //Field Declaration
    Context mContext;
    LayoutInflater mInflater;
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
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mResponse.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.person_item_layout, parent, false));
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
        if (position > lastItemWithAnimation) {
            holder.itemView.startAnimation(Helper.getFadeInAnim(500));
            lastItemWithAnimation = position;
        }
        Helper.setW500Image(currentItem.getProfile_path(), holder.personImg);
        Helper.setText(currentItem.getName(), holder.personName, true);
        Helper.setText(currentItem.getCharacter(), holder.character, true);
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
