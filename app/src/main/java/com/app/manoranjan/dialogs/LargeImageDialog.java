package com.app.manoranjan.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.manoranjan.R;
import com.app.manoranjan.utils.Constants;
import com.app.manoranjan.utils.Helper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Objects;

public class LargeImageDialog extends Dialog {
    //class variables
    Context mContext;

    public LargeImageDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public void setContentView(int layoutResID, boolean isScreenSmallerThan6) {
        super.setContentView(layoutResID);
        setCanceledOnTouchOutside(true);
        Objects.requireNonNull(this.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getAttributes().windowAnimations = R.style.DialogLargeImage;

        if (isScreenSmallerThan6) {
            ViewGroup.LayoutParams imageParams = findViewById(R.id.high_res_image).getLayoutParams();
            ViewGroup.LayoutParams progressParams = findViewById(R.id.progress_bar).getLayoutParams();
            imageParams.height = (int) (480 * Helper.density);
            imageParams.width = (int) (320 * Helper.density);
            progressParams.height = (int) (32 * Helper.density);
            progressParams.width = (int) (32 * Helper.density);
        }
    }

    public void setImage(String imageURL, final Drawable lowResImage) {
        if (imageURL == null || imageURL.length() == 0)
            return;

        ((ImageView) findViewById(R.id.low_res_image)).setImageDrawable(lowResImage);

        Glide.with(mContext).load(Constants.IMG_ORIGINAL_BASE_URL + imageURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        return false;
                    }
                })
                .into((ImageView) findViewById(R.id.high_res_image));
        show();
    }
}
