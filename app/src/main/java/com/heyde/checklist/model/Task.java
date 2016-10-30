package com.heyde.checklist.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.R.color.transparent;
import static android.R.drawable.checkbox_off_background;
import static android.R.drawable.checkbox_on_background;
import static android.R.drawable.ic_delete;
import static android.R.drawable.ic_menu_delete;

/**
 * Created by Daniel on 10/8/2016.
 */

public class Task {
    private String mTaskText;
    private boolean mIsChecked;
    private Context mContext;
    private TextView mTextView;
    private ImageButton mCheckButton;
    private boolean mIsDeletable;
    private boolean mFlaggedForDeletion;

    public Task(String taskText, boolean isChecked, Context context, TextView referenceTextView, ImageButton referenceImageButton) {
        mTaskText = taskText;
        mIsChecked = isChecked;
        mContext = context;
        mFlaggedForDeletion = false;
        createTextView(referenceTextView);
        createImageButton(referenceImageButton);
    }


    public TextView getTextView() {
        return mTextView;
    }

    public boolean isFlaggedForDeletion() {
        return mFlaggedForDeletion;
    }

    private void createImageButton(ImageButton referenceImageButton) {
        final ImageButton newCheckButton = new ImageButton(mContext);
        newCheckButton.setLayoutParams(referenceImageButton.getLayoutParams());
        Drawable checkbox;
        if (Build.VERSION.SDK_INT >= 21) {
            if (!isChecked()) {
                checkbox = mContext.getDrawable(checkbox_off_background);
            } else {
                checkbox = mContext.getDrawable(checkbox_on_background);
            }
        } else { // for older devices
            if (!isChecked()) {
                checkbox = mContext.getResources().getDrawable(checkbox_off_background);
            } else {
                checkbox = mContext.getResources().getDrawable(checkbox_on_background);
            }
        }
        newCheckButton.setImageDrawable(checkbox);
        newCheckButton.setPadding(0, 2, 0, 0);// initial one had to be padded 2dp, so these will follow to make it uniform
        int color;
        if (Build.VERSION.SDK_INT >= 23) {
            color = ContextCompat.getColor(mContext, transparent);
        } else {
            color = mContext.getResources().getColor(transparent);
        }
        newCheckButton.setBackgroundColor(color);

        newCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsDeletable) {
                    mFlaggedForDeletion = true;
                    Drawable delete;
                    if (Build.VERSION.SDK_INT >= 21) {
                        delete = mContext.getDrawable(ic_delete);
                    } else { // for older devices
                        delete = mContext.getResources().getDrawable(ic_delete);
                    }
                    mCheckButton.setImageDrawable(delete);
                } else {
                    toggleCheckButton();
                }
            }
        });
        newCheckButton.setAdjustViewBounds(true);
        mCheckButton = newCheckButton;
    }

    public void restoreCheckButton() {

        Drawable checkbox;
        if (Build.VERSION.SDK_INT >= 21) {
            if (!isChecked()) {
                checkbox = mContext.getDrawable(checkbox_off_background);
            } else {
                checkbox = mContext.getDrawable(checkbox_on_background);
            }
        } else { // for older devices
            if (!isChecked()) {
                checkbox = mContext.getResources().getDrawable(checkbox_off_background);
            } else {
                checkbox = mContext.getResources().getDrawable(checkbox_on_background);
            }
        }
        mIsDeletable = false;
        mCheckButton.setImageDrawable(checkbox);
    }

    public void toggleCheckButton() {

        Drawable checkbox;
        if (Build.VERSION.SDK_INT >= 21) {
            if (!isChecked()) {
                checkbox = mContext.getDrawable(checkbox_on_background);
                setChecked(true);
            } else {
                checkbox = mContext.getDrawable(checkbox_off_background);
                setChecked(false);
            }
        } else { // for older devices
            if (!isChecked()) {
                checkbox = mContext.getResources().getDrawable(checkbox_on_background);
                setChecked(true);
            } else {
                checkbox = mContext.getResources().getDrawable(checkbox_off_background);
                setChecked(false);
            }
        }
        mCheckButton.setImageDrawable(checkbox);
    }

    private void createTextView(TextView referenceTextView) {

        TextView newTaskText = new TextView(mContext);
        newTaskText.setLayoutParams(referenceTextView.getLayoutParams());
        newTaskText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        newTaskText.setText(mTaskText);
        mTextView = newTaskText;

    }


    public void prepForDelete() {
        mIsDeletable = true;
        Drawable image;
        if (Build.VERSION.SDK_INT >= 21) {
            image = mContext.getDrawable(ic_menu_delete);
        } else { // for older devices
            image = mContext.getResources().getDrawable(ic_menu_delete);
        }
        mCheckButton.setImageDrawable(image);
    }

    public ImageButton getCheckButton() {
        return mCheckButton;
    }

    public void setCheckButton(ImageButton checkButton) {
        mCheckButton = checkButton;
    }

    public String getTaskText() {
        return mTaskText;
    }

    public void setTaskText(String taskText) { // might be used later for an edit task?
        mTaskText = taskText;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    private void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
