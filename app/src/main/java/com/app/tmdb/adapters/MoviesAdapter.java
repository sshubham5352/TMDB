package com.app.tmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.interfaces.MoviesAdapterListener;
import com.app.tmdb.models.MovieResponse;
import com.app.tmdb.utils.Helper;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    //Field Declaration
    Context mContext;
    int recyclerViewID;
    MovieResponse mResponse;
    List<MovieResponse.Result> items;
    MoviesAdapterListener mListener;
    LayoutInflater inflater;
    int lastItemWithAnimation;

    public MoviesAdapter(MoviesAdapterListener listener, Context context, int recyclerViewID, MovieResponse response) {
        this.recyclerViewID = recyclerViewID;
        mContext = context;
        mResponse = response;
        mListener = listener;
        this.items = response.getResults();
        lastItemWithAnimation = -1;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieResponse.Result currentItem = items.get(position);
        float voteAverage = currentItem.getVote_average();

        setFadeInAnimation(position, holder.itemView);
        Helper.setW500Image(currentItem.getPoster_path(), holder.movie_poster);
        Helper.setText(currentItem.getTitle(), currentItem.getName(), holder.name, true);
        Helper.setDate(currentItem.getRelease_date(), currentItem.getFirst_air_date(), holder.releaseDate);
        Helper.setVotePercentage(voteAverage, holder.votePercentage);
        Helper.chooseUserScoreProgress(mContext, voteAverage, holder.progressBar, true);

        if ((position + 1) == getItemCount() && mResponse.getPage() != mResponse.getTotal_pages()) {
            mListener.fetchNextPageResponse(recyclerViewID);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setFadeInAnimation(int position, View view) {
        if (position <= lastItemWithAnimation || recyclerViewID == R.id.top_rated_RecyclerView)
            return;
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
        lastItemWithAnimation = position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Field declaration
        ImageView movie_poster;
        TextView name;
        TextView releaseDate;
        TextView votePercentage;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster = itemView.findViewById(R.id.movie_poster);
            name = itemView.findViewById(R.id.movie_name);
            releaseDate = itemView.findViewById(R.id.release_date);

            ConstraintLayout rootLayout = itemView.findViewById(R.id.user_score);
            progressBar = rootLayout.findViewById(R.id.user_score_progress);
            votePercentage = rootLayout.findViewById(R.id.user_score_percentage);
            //setting onClick listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener == null || getAdapterPosition() == RecyclerView.NO_POSITION)
                        return;
                    mListener.showMovieDetails(mResponse.getResults().get(getAdapterPosition()).getId());
                }
            });
        }
    }
}