package com.app.manoranjan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.app.manoranjan.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.app.manoranjan.utils.Constants.IMG_W500_BASE_URL;

public class Helper {
    //static fields
    public static final float density = Resources.getSystem().getDisplayMetrics().density;
    public static final AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);

    static {
        alphaAnimation.setDuration(1000);
    }

    public static float getDensity() {
        return density;
    }

    public static boolean isScreenSmallerThan6(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels <= 1920;
    }

    public static Random mRandom = new Random();

    public static int randomInt(int i) {
        return mRandom.nextInt(i);
    }

    public static String getFirstNonNullString(String s1, String s2) {
        if (s1 != null && s1.length() != 0)
            return s1;
        if (s2 != null && s2.length() != 0)
            return s2;
        return "null";
    }

    public static void setText(String text, TextView textView, boolean setNA) {
        if (text != null && text.length() != 0)
            textView.setText(text);
        else if (setNA)
            textView.setText(R.string.na);
    }

    public static void setText(String text1, String text2, TextView textView, boolean setNA) {
        if (text1 != null && text1.length() != 0)
            textView.setText(text1);
        else if (text2 != null && text2.length() != 0)
            textView.setText(text2);
        else if (setNA)
            textView.setText(R.string.na);
    }

    public static void setTextInParentheses(String text, TextView textView, boolean setNA) {
        if (text != null && text.length() != 0)
            textView.setText("(" + text + ")");
        else if (setNA)
            textView.setText(R.string.na);
    }

    public static void setVotePercentage(float voteAverage, TextView votePercentageTxtView) {
        if (voteAverage > 0)
            votePercentageTxtView.setText((int) (voteAverage * 10) + "%");
        else
            votePercentageTxtView.setText(R.string.na);
    }


    public static void appendText(String text, TextView textView, boolean startsWithSpace) {
        if (text != null && text.length() != 0) {
            if (startsWithSpace)
                textView.append(" ");
            textView.append(text);
        }
    }

    public static void appendTextInParentheses(String text, TextView textView, boolean startsWithSpace) {
        if (text != null && text.length() != 0) {
            if (startsWithSpace)
                textView.append(" ");
            textView.append("(" + text + ")");
        }
    }

    public static void setOriginalLanguage(String originalLanguage, TextView textView, boolean setNA) {
        if (originalLanguage == null || originalLanguage.length() == 0)
            return;
        switch (originalLanguage) {
            case "hi":
                textView.setText(R.string.hindi);
                break;
            case "mr":
                textView.setText(R.string.marathi);
                break;
            case "te":
                textView.setText(R.string.telugu);
                break;
            case "ta":
                textView.setText(R.string.tamil);
                break;
            case "ml":
                textView.setText(R.string.malayalam);
                break;
            case "bn":
                textView.setText(R.string.bengali);
                break;
            case "gu":
                textView.setText(R.string.gujarati);
                break;
            case "pa":
                textView.setText(R.string.panjabi);
                break;
            case "en":
                textView.setText(R.string.english);
                break;
            case "ja":
                textView.setText(R.string.japanese);
                break;
            case "da":
                textView.setText(R.string.danish);
                break;
            case "fr":
                textView.setText(R.string.french);
                break;
            case "ko":
                textView.setText(R.string.korean);
                break;
            case "de":
                textView.setText(R.string.german);
                break;
            case "pt":
                textView.setText(R.string.portuguese);
                break;
            case "he":
                textView.setText(R.string.hebrew);
                break;
            case "ar":
                textView.setText(R.string.arabic);
                break;
            case "it":
                textView.setText(R.string.italian);
                break;
            case "tl":
                textView.setText(R.string.tagalog);
                break;
            case "cn":
                textView.setText(R.string.cantonese);
                break;
            case "es":
                textView.setText(R.string.spanish_castilian);
                break;
            case "ur":
                textView.setText(R.string.urdu);
                break;
        }
    }

    public static void setW500Image(String imagePath, ImageView imageView) {
        if (imagePath != null && imagePath.length() != 0)
            Glide.with(imageView).load(IMG_W500_BASE_URL + imagePath)
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder_image_loading).error(R.drawable.image_not_available))
                    .into(imageView);
        else
            imageView.setImageResource(R.drawable.image_not_available);
    }

    public static void chooseUserScoreProgress(Context context, float voteAverage, ProgressBar progressBar, boolean setProgress) {
        if (setProgress)
            progressBar.setProgress((int) (voteAverage * 10));

        if (voteAverage >= 7.0)
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_bar_green));

        else if (voteAverage >= 4.0)
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_bar_yellow));

        else if (voteAverage > 0)
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_bar_red));
        else
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_progress_bar_gray));
    }

    public static void setTimeDuration(int timeDurationInMinutes, TextView textView) {
        if (timeDurationInMinutes == 0) {
            textView.setText(R.string.na_in_brackets);
            return;
        }
        int hrs = timeDurationInMinutes / 60;
        int min = timeDurationInMinutes - (hrs * 60);
        if (hrs == 0)
            textView.setText(min + "m");
        else
            textView.setText(hrs + "h " + min + "m");
    }

    public static void setDate(String releaseDate, String first_air_date, TextView textView) {
        String mdate = releaseDate;
        if (mdate == null || mdate.length() == 0)
            mdate = first_air_date;

        if (mdate != null && mdate.length() != 0) {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(mdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                textView.setText(outputFormat.format(date));
            }
        } else
            textView.setText(R.string.na);
    }

    public static void setAmount(Context context, long amount, String countryCode, TextView textView, boolean setNA) {
        if (amount == 0) {
            if (setNA)
                textView.setText(R.string.na);
            return;
        }

        if (countryCode.matches(context.getString(R.string.in))) {
            textView.setText(context.getString(R.string.symbol_rupee));
            amount *= 70;                                                                           // converting into INR
            String amountInTxt;
            double crore = amount / Math.pow(10, 7);                                                // divided by 1 crore
            if (crore < 1) {
                amountInTxt = String.valueOf((int) (crore * 100));                                  // converting crore to lakh
                textView.append(amountInTxt + " " + context.getString(R.string.lakh));
            } else {
                crore = (double) Math.round(crore * 10) / 10;                                        // setting precision after decimal to 1 digits
                amountInTxt = String.valueOf(crore);
                if (crore % 1 == 0)
                    amountInTxt = String.valueOf((int) crore);                                      // crore does not have decimal part

                amountInTxt = convertInIndianNumberFormat(amountInTxt);                             // setting commas in Indian style
                textView.append(amountInTxt + " " + context.getString(R.string.crore));
            }
        } else {
            textView.setText(context.getString(R.string.symbol_dollar));
            double dollars;
            String suffix;
            if (amount / Math.pow(10, 9) >= 1) {
                dollars = amount / Math.pow(10, 9);
                suffix = context.getString(R.string.billion);
            } else if (amount / Math.pow(10, 6) >= 1) {
                dollars = amount / Math.pow(10, 6);
                suffix = context.getString(R.string.million);
            } else {
                dollars = amount / Math.pow(10, 3);
                suffix = context.getString(R.string.k);
            }
            dollars = (double) Math.round(dollars * 10) / 10;                                       // setting precision after decimal to 1 digits
            if (dollars % 1 == 0)
                textView.append(((int) dollars) + " " + suffix);                                          // dollars does not have decimal part
            else
                textView.append(dollars + " " + suffix);
        }

    }

    private static String convertInIndianNumberFormat(String amountInString) {
        int indexOfDecimal = amountInString.indexOf(".");
        StringBuilder amountInIndianFormat;
        if (indexOfDecimal == -1) // no decimal part
            amountInIndianFormat = new StringBuilder(amountInString);
        else
            amountInIndianFormat = new StringBuilder(amountInString.substring(0, indexOfDecimal));

        if (amountInIndianFormat.length() <= 3)
            return amountInString;

        amountInIndianFormat.insert(amountInIndianFormat.length() - 3, ",");                   // inserting comma after 3 digits in the number
        int indexOfLastComma = amountInIndianFormat.indexOf(",");

        for (int i = 5; i < amountInIndianFormat.length(); i += 2) {
            amountInIndianFormat.insert(indexOfLastComma - 2, ",");
            indexOfLastComma = amountInIndianFormat.indexOf(",");
        }
        if (indexOfDecimal == -1)
            return amountInIndianFormat.toString();
        else
            return (amountInIndianFormat.toString() + amountInString.substring(indexOfDecimal));
    }

    public static boolean isFieldEmpty(EditText field) {
        if (field.getText().toString().trim().length() == 0) {
            field.getText().clear();
            field.setError("Field can't be empty");
            return true;
        }
        return false;
    }

    public static boolean isTextOutOfLimits(EditText field, String fieldName, int minLimit, int maxLimit) {
        if (field.getText().length() < minLimit || field.getText().length() > maxLimit) {
            field.setError(fieldName + " must be within " + minLimit + "-" + maxLimit + " characters");
            return true;
        }
        return false;
    }

    public static AlphaAnimation getAlphaAnimation(int durationInMilli) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(durationInMilli);
        return anim;
    }

    public static AlphaAnimation getAlphaAnimation500() {
        return alphaAnimation;
    }

    public static Animation getSlideInAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.anim_slide_in);
    }

}
