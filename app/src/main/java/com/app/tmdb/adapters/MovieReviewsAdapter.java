package com.app.tmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.databinding.ReviewItemLayoutBinding;
import com.app.tmdb.models.MovieReviewsResponse;
import com.app.tmdb.utils.Helper;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {
    //Field Declaration
    Context mContext;
    LayoutInflater mInflater;
    MovieReviewsResponse mResponse;
    int lastItemWithAnimation;
    boolean hasReachedEnd;

    public MovieReviewsAdapter(Context context, MovieReviewsResponse response) {
        mContext = context;
        mResponse = response;
        lastItemWithAnimation = -1;
        hasReachedEnd = false;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mResponse.getResults().size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieReviewsAdapter.ViewHolder(DataBindingUtil.inflate(mInflater, R.layout.review_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
//            mListener.lastItemReached(R.id.top_billed_cast_RecyclerView);
            hasReachedEnd = true;
        } else if (hasReachedEnd) {
//            mListener.hideNoMoreItemsLayout(R.id.top_billed_cast_RecyclerView);
            hasReachedEnd = false;
        }
        MovieReviewsResponse.Review currentItem = mResponse.getResults().get(position);
        //setting fadeInAnim
        if (position > lastItemWithAnimation) {
            holder.itemView.startAnimation(Helper.getFadeInAnim(500));
            lastItemWithAnimation = position;
        }
        Helper.setW500Image(currentItem.getAuthor_details().getAvatar_path(), holder.binding.imgAvatar);
        Helper.setText(currentItem.getAuthor(), holder.binding.nameAuthor, true);
        Helper.setText(String.valueOf(currentItem.getAuthor_details().getRating()), holder.binding.rating, true);
        Helper.setText(currentItem.getContent(), holder.binding.content, false);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        //Field Declaration
        ReviewItemLayoutBinding binding;

        public ViewHolder(ViewDataBinding inflate) {
            super(inflate.getRoot());
            binding = (ReviewItemLayoutBinding) inflate;
        }
    }
}
