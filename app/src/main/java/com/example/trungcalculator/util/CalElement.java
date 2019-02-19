package com.example.trungcalculator.util;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trungcalculator.R;

public final class CalElement {
    private TextView mNumView;
    private TextView mOperatorView;
    private String mOperator;
    private boolean mHasNum;
    private boolean mHasDot;
    private Context mContext;
    private ViewGroup mLinearInDisplay;

    CalElement(Context context, ViewGroup viewGroup) {
        mContext = context;
        mLinearInDisplay = viewGroup;
        View holder = LayoutInflater.from(mContext)
            .inflate(R.layout.item_cal_display, mLinearInDisplay, false);
        mLinearInDisplay.addView(holder);
        focusBottom();
        mNumView = holder.findViewById(R.id.text_number);
        mOperatorView = holder.findViewById(R.id.text_operator);
        mHasNum = false;
        mHasDot = false;
    }

    CalElement(Context context, ViewGroup viewGroup, double num) {
        this(context, viewGroup);
        if (Double.compare(num, 0) == 0) {
            setNum("0");
        } else {
            setNum(FormatHelper.formatDouble(num));
        }
    }

    public CalElement(Context context, ViewGroup viewGroup, String operator) {
        this(context, viewGroup);
        setOperator(operator);
    }

    public CalElement(Context context, ViewGroup viewGroup, double num, String operator) {
        this(context, viewGroup);
        setNum(FormatHelper.formatDouble(num));
        setOperator(operator);
    }

    public void setNum(String numString) {
        mHasNum = true;
        if (FormatHelper.getDouble(numString) % 1 != 0) mHasDot = true;
        mNumView.setText(numString);
    }

    public void setOperator(String mOperator) {
        this.mOperator = mOperator;
        mOperatorView.setText(mOperator);
    }

    public boolean isHasNum() {
        return mHasNum;
    }

    public double getNum() {
        return FormatHelper.getDouble(mNumView.getText().toString());
    }

    public String getOperator() {
        return mOperator;
    }

    void addNum(int number) {
        if (mNumView.getText().length() < 15) {
            if (!isHasNum() || (Double.compare(new Double(0), getNum()) == 0 && !mHasDot)) {
                setNum(String.valueOf(number));
            } else {
                mNumView.append(String.valueOf(number));
            }
        }
    }

    void addDot() {
        if (mNumView.getText().length() < 15 && !mHasDot && !checkOpEqual()) {
            mNumView.append(".");
            mHasDot = true;
        }
    }

    void del() {
        if (!mNumView.getText().toString().isEmpty() && !checkOpEqual()) {
            String numStr = mNumView.getText().toString();
            numStr = numStr.substring(0, numStr.length() - 1);
            if (!numStr.contains(".")) mHasDot = false;
            if (numStr.length() == 0) numStr = "0";
            mNumView.setText(numStr);
        }
    }

    void neg() {
        if (!mNumView.getText().toString().isEmpty() && !checkOpEqual()) {
            Double num = FormatHelper.getDouble(mNumView.getText().toString());
            num = -num;
            mNumView.setText(FormatHelper.formatDouble(num));
        }
    }

    void changeFocus(boolean focused) {
        if (focused) {
            float size = mContext.getResources().getDimension(R.dimen.cal_elem_focused);
            int color = mContext.getResources().getColor(R.color.colorBlack);
            mNumView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mNumView.setTextColor(color);
            mOperatorView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mOperatorView.setTextColor(color);
        } else {
            float size = mContext.getResources().getDimension(R.dimen.cal_elem_num);
            int color = mContext.getResources().getColor(R.color.colorGrey);
            mNumView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mNumView.setTextColor(color);
            mOperatorView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mOperatorView.setTextColor(color);
        }
    }

    private void focusBottom() {
        final NestedScrollView scrollView =
            (NestedScrollView) mLinearInDisplay.getParent();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    boolean checkOpEqual() {
        return (mOperatorView.getText().toString() != null &&
            mOperatorView.getText().toString().equals("="));
    }
}
