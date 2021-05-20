
package com.app.tmdb.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.app.tmdb.R;
import com.app.tmdb.models.MultiMediaSearchResponse;
import com.app.tmdb.utils.Helper;

import java.util.List;


public class MultiMediaSearchAdapter extends ArrayAdapter<MultiMediaSearchResponse.Result> {

    //Class variables
    List<MultiMediaSearchResponse.Result> results;
    LayoutInflater inflater;
    Context mContext;
    Activity mActivity;

    public MultiMediaSearchAdapter(@NonNull Context context, Activity activity, @NonNull List<MultiMediaSearchResponse.Result> results) {
        super(context, 0);
        mContext = context;
        mActivity = activity;
        this.results = results;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        organiseMultiSearchResponse();
    }

    private void organiseMultiSearchResponse() {
        int i = 0, position = 0;
        for (MultiMediaSearchResponse.Result result : results) {
            if (result.getMedia_type().matches(mContext.getString(R.string.movie))) {
                results.add(position, results.remove(i));
                position++;
                break;
            }
            i++;
        }

        i = 0;
        for (MultiMediaSearchResponse.Result result : results) {
            if (result.getMedia_type().matches(mContext.getString(R.string.tv))) {
                results.add(position, results.remove(i));
                position++;
                break;
            }
            i++;
        }

        i = 0;
        for (MultiMediaSearchResponse.Result result : results) {
            if (result.getMedia_type().matches(mContext.getString(R.string.person))) {
                results.add(position, results.remove(i));
                break;
            }
            i++;
        }
    }


    @Override
    public int getCount() {
        return results.size();
    }

    @Nullable
    @Override
    public MultiMediaSearchResponse.Result getItem(int position) {
        return results.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.multi_media_search_layout, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        bindData(convertView, position);
        hideSoftKeyboardWhileScrolling(convertView);
        return convertView;
    }

    private void bindData(View view, int position) {
        MultiMediaSearchResponse.Result currentItem = getItem(position);
        ViewHolder currentHolder = (ViewHolder) view.getTag();

        Helper.setText(currentItem.getTitle(), currentItem.getName(), currentHolder.mediaTitle, true);
        if (currentHolder.mediaTitle.getText().length() > 28) {
            currentHolder.mediaTitle.setText(currentHolder.mediaTitle.getText().toString().
                    substring(0, 28).concat(mContext.getString(R.string.three_dots)));
        }
        setMediaTypeIcon(currentItem.getMedia_type(), currentHolder);
        //setting txtMediaType
        if (currentItem.getMedia_type().matches(mContext.getString(R.string.movie)))
            currentHolder.txtMediaType.setText(mContext.getString(R.string.in_movies));
        else if (currentItem.getMedia_type().matches(mContext.getString(R.string.tv)))
            currentHolder.txtMediaType.setText(mContext.getString(R.string.in_tv_shows));
        else if (currentItem.getMedia_type().matches(mContext.getString(R.string.person)))
            currentHolder.txtMediaType.setText(mContext.getString(R.string.in_people));
    }

    private void setMediaTypeIcon(String mediaType, ViewHolder currentHolder) {
        if (mediaType.matches(mContext.getString(R.string.movie)))
            currentHolder.iconMediaType.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_movie_reel, null));
        else if (mediaType.matches(mContext.getString(R.string.tv)))
            currentHolder.iconMediaType.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_tv, null));
        else if (mediaType.matches(mContext.getString(R.string.person)))
            currentHolder.iconMediaType.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_person, null));
        else
            currentHolder.iconMediaType.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_magnifying_glass, null));

    }


    private void hideSoftKeyboardWhileScrolling(final View convertView) {
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
    }

    static class ViewHolder {
        //Variable Declaration
        TextView mediaTitle;
        TextView txtMediaType;
        ImageView iconMediaType;

        public ViewHolder(@NonNull View itemView) {
            mediaTitle = itemView.findViewById(R.id.media_title);
            txtMediaType = itemView.findViewById(R.id.txt_media_type);
            iconMediaType = itemView.findViewById(R.id.icon_media_type);
        }
    }
}													