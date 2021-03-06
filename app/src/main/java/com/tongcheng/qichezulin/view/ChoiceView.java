package com.tongcheng.qichezulin.view;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tongcheng.qichezulin.R;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ChoiceView extends FrameLayout implements Checkable {

    private TextView mTextView;
    private RadioButton mRadioButton;

    public ChoiceView(Context context) {
        super(context);
        View.inflate(context, R.layout.listview_item_car_type, this);
        mTextView = (TextView) findViewById(R.id.tv_show_car_type_name);
        mRadioButton = (RadioButton) findViewById(R.id.rb_is_chose);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mRadioButton.isChecked();
    }

    @Override
    public void toggle() {
        mRadioButton.toggle();
    }
}