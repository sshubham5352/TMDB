package com.app.tmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.interfaces.StarCastAdapterListener;
import com.app.tmdb.models.MovieCreditsResponse;
import com.app.tmdb.utils.Helper;

import java.util.List;

public class CrewResponseAdapter extends RecyclerView.Adapter<CrewResponseAdapter.ViewHolder> {
    //Field Declaration
    Context mContext;
    LayoutInflater inflater;
    List<MovieCreditsResponse.Crew> mResponse;
    StarCastAdapterListener mListener;
    boolean isLastItemLayoutShowing, areViewsRecycling;
    int lastItemWithAnimation;

    public CrewResponseAdapter(Context context, StarCastAdapterListener listener, List<MovieCreditsResponse.Crew> response) {
        mContext = context;
        mResponse = response;
        mListener = listener;
        lastItemWithAnimation = -1;
        isLastItemLayoutShowing = areViewsRecycling = false;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CrewResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_item_layout, parent, false);
        return new CrewResponseAdapter.ViewHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        areViewsRecycling = true;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == getItemCount() - 1 && areViewsRecycling) {
            mListener.lastItemReached(R.id.crew_RecyclerView);
            isLastItemLayoutShowing = true;
        } else if (isLastItemLayoutShowing) {
            mListener.hideNoMoreItemsLayout(R.id.crew_RecyclerView);
            isLastItemLayoutShowing = false;
        }
        MovieCreditsResponse.Crew currentItem = mResponse.get(position);
        //setting person_img
        setInFadeAnimation(position, holder.itemView);
        Helper.setW500Image(currentItem.getProfile_path(), holder.personImg);
        Helper.setText(currentItem.getName(), holder.personName, true);
        Helper.setText(currentItem.getJob(), holder.job, true);
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
        //Response has more than 200 items and I don't want to show more than 30 crew members
        return Math.min(mResponse.size(), 30);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //Field declaration
        ImageView personImg;
        TextView personName;
        TextView job;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personImg = itemView.findViewById(R.id.person_img);
            personName = itemView.findViewById(R.id.person_name);
            job = itemView.findViewById(R.id.description);
        }
    }
}

