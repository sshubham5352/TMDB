package com.app.tmdb.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.palette.graphics.Palette;

import com.app.tmdb.R;
import com.app.tmdb.adapters.TvShowDetailsPagerAdapter;
import com.app.tmdb.databinding.ActivityTvShowDetailsBinding;
import com.app.tmdb.dialogs.LargeImageDialog;
import com.app.tmdb.models.TvShowDetailsResponse;
import com.app.tmdb.retrofit.ApiManager;
import com.app.tmdb.retrofit.ApiResponseInterface;
import com.app.tmdb.utils.Constants;
import com.app.tmdb.utils.Helper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class TvShowDetailsActivity extends AppCompatActivity implements ApiResponseInterface {
    // class variables
    ActivityTvShowDetailsBinding binding;
    TvShowDetailsResponse tvShowDetailsResponse;
    TvShowDetailsPagerAdapter tvDetailsPagerAdapter;
    ApiManager apiManager;
    long tvShowID;
    int backgroundLayersColor, tabLayoutColor;
    LargeImageDialog dialogLargeImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);

        fixLayout();
        Intent intent = getIntent();
        tvShowID = intent.getLongExtra(Constants.SEARCHED_TV_ID, -1);
        apiManager = new ApiManager(this, this);
        apiManager.getTvShowDetails(tvShowID);

        //TabLayout listener
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int height = tvDetailsPagerAdapter.getCorrectViewPagerHeight(tab.getPosition());
                if (height != 0)
                    binding.detailsViewPager.getLayoutParams().height = height;
                changeDrawableTint(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tab.setText(R.string.overview);
                        break;
                    case 1:
                        tab.setText(R.string.star_cast);
                        break;
                    case 2:
                        tab.setText(R.string.reviews);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        dialogLargeImage = new LargeImageDialog(this);
        dialogLargeImage.setContentView(R.layout.dialog_large_img_layout, Helper.isScreenSmallerThan6(this));
    }

    private void fixLayout() {
        if (Helper.isScreenSmallerThan6(this)) {
            float density = Helper.getDensity();
            binding.backdropImg.getLayoutParams().height = (int) (200 * density);
            if (binding.posterImgHolder.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) binding.posterImgHolder.getLayoutParams();
                p.setMargins((int) (8 * density), (int) (22 * density), 0, (int) (8 * density));
                (binding.posterImgHolder).requestLayout();
            }
        }
        //increasing the size of votePercentage text included in user_score
        ((TextView) binding.userScore
                .findViewById(R.id.user_score_percentage))
                .setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        //setting views visibility to invisible to create user experience when the data is on the way
        binding.runningTime.setVisibility(View.INVISIBLE);
        binding.userScore.setVisibility(View.INVISIBLE);
        binding.txtUserScore.setVisibility(View.INVISIBLE);
        binding.markAsFav.setVisibility(View.INVISIBLE);
        binding.addToWatchlist.setVisibility(View.INVISIBLE);
        binding.rateIt.setVisibility(View.INVISIBLE);
        binding.tagLine.setVisibility(View.INVISIBLE);
    }

    @Override
    public void apiCallSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constants.TV_SHOW_DETAILS_RESPONSE) {
            tvShowDetailsResponse = (TvShowDetailsResponse) response;
            if (binding.detailsViewPager.getAdapter() == null)
                setFragmentsInViewPager();
            setImages();
        }
    }

    @Override
    public void apiCallFailure(String errorCode) {
        binding.progressBar.setVisibility(View.GONE);
        Toast.makeText(this, errorCode, Toast.LENGTH_LONG).show();
        binding.gradientLayer1.setBackgroundColor(getResources().getColor(R.color.colorAppTheme));
    }

    private void setFragmentsInViewPager() {
        Bundle fragmentOverviewBundle = new Bundle();
        Bundle fragmentStarCastBundle = new Bundle();
        fragmentOverviewBundle.putSerializable(Constants.FRAGMENT_OVERVIEW_BUNDLE, tvShowDetailsResponse);
        fragmentStarCastBundle.putLong(Constants.FRAGMENT_STAR_CAST_BUNDLE, tvShowDetailsResponse.getId());
        tvDetailsPagerAdapter = new TvShowDetailsPagerAdapter(this, fragmentOverviewBundle, fragmentStarCastBundle, 3);
        binding.detailsViewPager.setAdapter(tvDetailsPagerAdapter);
        //setting tabLayout mediator
        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayout, binding.detailsViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setIcon(R.drawable.ic_movie_clapperboard);
                                tab.setText(R.string.overview);
                                break;
                            case 1:
                                tab.setIcon(R.drawable.ic_movie_cast);
                                break;
                            case 2:
                                tab.setIcon(R.drawable.ic_user_review);
                                break;
                        }
                    }
                });
        mediator.attach();
    }


    private void changeDrawableTint(final int position) {
        binding.tabLayout.post(new Runnable() {
            @Override
            public void run() {
                DrawableCompat.setTint(binding.tabLayout.getTabAt(position).getIcon(), tabLayoutColor);
            }
        });
    }

    private void setImages() {
        //poster image
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        binding.posterImg.startAnimation(anim);
        Helper.setW500Image(tvShowDetailsResponse.getPoster_path(), binding.posterImg);
        //background image
        Picasso.get().load(Constants.IMG_W1066_H600_BASE_URL + tvShowDetailsResponse.getBackdrop_path())
                .placeholder(R.drawable.placeholder_image_loading)
                .into(binding.backdropImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        setGradientLayers(binding.backdropImg, true);
                    }

                    @Override
                    public void onError(Exception e) {
                        setGradientLayers(binding.posterImg, false);
                    }
                });
    }

    private void setGradientLayers(ImageView image, final boolean isBackgroundImageAvailable) {
        final Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //work with the palette here
                int defaultValue = 0x000000;
                int backgroundLayerAlpha;
                int colorDominant = palette.getDominantColor(defaultValue);
                tabLayoutColor = palette.getVibrantColor(defaultValue);

                if (!isColorDark(colorDominant))
                    colorDominant = palette.getDarkVibrantColor(defaultValue);
                backgroundLayersColor = (colorDominant == 0) ? getResources().getColor(R.color.colorAppTheme) : colorDominant;
                tabLayoutColor = (tabLayoutColor == 0) ? backgroundLayersColor : tabLayoutColor;

                if (isBackgroundImageAvailable)
                    backgroundLayerAlpha = 160;
                else {
                    backgroundLayerAlpha = 220;
                    tabLayoutColor = backgroundLayersColor;
                }

                GradientDrawable gd1 = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{backgroundLayersColor, ColorUtils.setAlphaComponent(backgroundLayersColor, backgroundLayerAlpha)});
                GradientDrawable gd2 = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{ColorUtils.setAlphaComponent(backgroundLayersColor, 80), ColorUtils.setAlphaComponent(backgroundLayersColor, 60)});
                GradientDrawable gd3 = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{backgroundLayersColor, backgroundLayersColor, ColorUtils.setAlphaComponent(backgroundLayersColor, 1)});

                binding.gradientLayer1.setBackground(gd1);
                binding.gradientLayer2.setBackground(gd2);
                binding.gradientLayer3.setBackground(gd3);

                getWindow().setStatusBarColor(backgroundLayersColor);
                binding.tabLayout.setSelectedTabIndicatorColor(tabLayoutColor);
                binding.tabLayout.setTabTextColors(R.color.colorUnselectedText, tabLayoutColor);
                DrawableCompat.setTint(binding.tabLayout.getTabAt(0).getIcon(), tabLayoutColor);
            }
        });

        if (!isBackgroundImageAvailable)
            binding.backdropImg.setImageResource(0);
        binding.progressBar.setVisibility(View.GONE);

        setData();
    }

    private void setData() {
        //setting fadeInAnimation
        setFadeInAnimation(binding.tvShowTitle);
        setFadeInAnimation(binding.releaseYear);
        setFadeInAnimation(binding.genres);
        setFadeInAnimation(binding.countryCode);
        setFadeInAnimation(binding.runningTime);
        setFadeInAnimation(binding.userScore);
        setFadeInAnimation(binding.txtUserScore);
        setFadeInAnimation(binding.markAsFav);
        setFadeInAnimation(binding.addToWatchlist);
        setFadeInAnimation(binding.rateIt);
        setFadeInAnimation(binding.tagLine);
        //setting view visible
        binding.runningTime.setVisibility(View.VISIBLE);
        binding.userScore.setVisibility(View.VISIBLE);
        binding.txtUserScore.setVisibility(View.VISIBLE);
        binding.markAsFav.setVisibility(View.VISIBLE);
        binding.addToWatchlist.setVisibility(View.VISIBLE);
        binding.rateIt.setVisibility(View.VISIBLE);
        binding.tagLine.setVisibility(View.VISIBLE);
        //setting release date
        if (tvShowDetailsResponse.getFirst_air_date() != null && tvShowDetailsResponse.getFirst_air_date().length() != 0)
            Helper.setTextInParentheses(tvShowDetailsResponse.getFirst_air_date().substring(0, 4), binding.releaseYear, false);
        //setting title
        Helper.setText(tvShowDetailsResponse.getName(), binding.tvShowTitle, false);
        if (!isEllipsized()) {
            binding.tvShowTitle.append(" ");
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout layout = binding.rootLayout;
            set.clone(layout);
            set.clear(R.id.movie_title, ConstraintSet.END);
            set.clear(R.id.release_year, ConstraintSet.END);
            set.applyTo(layout);
        }
        //setting genres
        try {
            String genre = tvShowDetailsResponse.getGenres().get(0).getName();
            Helper.setText(genre, binding.genres, false);

            for (int i = 1; i < tvShowDetailsResponse.getGenres().size(); i++) {
                genre = tvShowDetailsResponse.getGenres().get(i).getName();
                if (i > 2 || genre == null || genre.length() == 0)
                    break;
                binding.genres.append("," + genre);
            }
        } catch (Exception e) {
            //empty catch
        }
        //setting country code
        String countryCode;
        try {
            countryCode = tvShowDetailsResponse.getProduction_countries().get(0).getIso_3166_1();
        } catch (IndexOutOfBoundsException e) {
            countryCode = null;
        }

        if (countryCode != null && countryCode.length() != 0) {
            if (countryCode.equals("GB"))
                Helper.setTextInParentheses("US", binding.countryCode, false);
            else
                Helper.setTextInParentheses(countryCode, binding.countryCode, false);
            binding.countryCode.setVisibility(View.VISIBLE);
        }
        //setting runningTime
        Helper.setTimeDuration(tvShowDetailsResponse.getEpisode_run_time()[0], binding.runningTime);
        //setting tagLine
        Helper.setText(tvShowDetailsResponse.getTagline(), binding.tagLine, false);
        //setting user score
        int userScore = (int) (tvShowDetailsResponse.getVote_average() * 10);
        Helper.chooseUserScoreProgress(this,
                tvShowDetailsResponse.getVote_average(),
                (ProgressBar) binding.userScore.findViewById(R.id.user_score_progress),
                false);
        if (userScore != 0) {
            ValueAnimator userScoreAnimator = ValueAnimator.ofInt(0, userScore);
            userScoreAnimator.setDuration(2250);
            userScoreAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    ((TextView) binding.userScore.findViewById(R.id.user_score_percentage)).setText(animation.getAnimatedValue().toString() + "%");
                }
            });
            ObjectAnimator userScoreProgressAnimator = ObjectAnimator.ofInt(binding.userScore.findViewById(R.id.user_score_progress),
                    "progress",
                    userScore);
            userScoreProgressAnimator.setDuration(2250);
            userScoreProgressAnimator.setInterpolator(new DecelerateInterpolator());

            userScoreProgressAnimator.start();
            userScoreAnimator.start();
        }
        tvDetailsPagerAdapter.getTvOverviewFragment().setOverviewVisible();
    }

    private void setFadeInAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    private boolean isEllipsized() {
        if (binding.tvShowTitle.length() > 21) {
            binding.tvShowTitle.setEllipsize(TextUtils.TruncateAt.END);
            return true;
        } else
            return false;
    }

    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return (darkness > 0.5); // returns true if color is dark & false if not
    }

    public void openDialogOriginalImage(View view) {
        if (binding.progressBar.getVisibility() == View.VISIBLE)
            return;
        //opening dialog
        dialogLargeImage.setImage(tvShowDetailsResponse.getPoster_path(), binding.posterImg.getDrawable());
    }
}