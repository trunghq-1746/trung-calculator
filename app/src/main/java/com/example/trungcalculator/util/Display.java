package com.example.trungcalculator.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trungcalculator.R;
import com.example.trungcalculator.ui.MainActivity;

import java.util.ArrayList;

public final class Display {
    private ArrayList<CalElement> mCalElements;
    private ViewGroup mInDisplay;
    private Context mContext;
    private CalElement mActiveElement;
    private boolean editMode;
    private double mResult;
    private ViewGroup mResultView;
    private ViewGroup mDisplay;

    public Display(Context context, ViewGroup inDisplay, ViewGroup display) {
        mContext = context;
        mInDisplay = inDisplay;
        mDisplay = display;
        mCalElements = new ArrayList<>();
        reset();
    }

    void reset() {
        mCalElements.clear();
        mInDisplay.removeAllViews();
        removeResultView();
        addElement(0);
    }

    void number(int number) {
        if (mActiveElement.getOperator() == MainActivity.OP_EQUAL) {
            endCalculation();
            addElement(number);
        } else {
            mActiveElement.addNum(number);
        }
        execute();
    }

    void operator(String operator) {
        if (mActiveElement.getOperator() != null && mActiveElement.getOperator().equals(
            MainActivity.OP_EQUAL)
            && !operator.equals(MainActivity.OP_EQUAL)) {
            endCalculation();
            addElement(mResult);
        }
        if (!(mActiveElement.getOperator() != null && mActiveElement.getOperator().equals(
            MainActivity.OP_EQUAL)
            && operator.equals(MainActivity.OP_EQUAL))) {
            if ((mActiveElement.isHasNum() && !editMode) ||
                operator.equals(MainActivity.OP_EQUAL)) {
                execute();
                if (operator.equals(MainActivity.OP_EQUAL)) {
                    addElement(mResult, operator);
                    removeResultView();
                } else {
                    addElement(operator);
                }
            } else {
                mActiveElement.setOperator(operator);
            }
        }
    }

    void addDot() {
        mActiveElement.addDot();
        if (!mActiveElement.checkOpEqual()) execute();
    }

    void del() {
        mActiveElement.del();
        if (!mActiveElement.checkOpEqual()) execute();
    }

    void neg() {
        mActiveElement.neg();
        if (!mActiveElement.checkOpEqual()) execute();
    }

    private void changeActiveElement(CalElement element) {
        if (mActiveElement != null) mActiveElement.changeFocus(false);
        mActiveElement = element;
        mActiveElement.changeFocus(true);
    }

    private void addElement(String operator) {
        CalElement element = new CalElement(mContext, mInDisplay, operator);
        mCalElements.add(element);
        changeActiveElement(element);
    }

    private void addElement(double num) {
        CalElement element = new CalElement(mContext, mInDisplay, num);
        mCalElements.add(element);
        changeActiveElement(element);
    }

    private void addElement(double num, String operator) {
        CalElement element = new CalElement(mContext, mInDisplay, num, operator);
        mCalElements.add(element);
        changeActiveElement(element);
    }

    private void endCalculation() {
        mCalElements.clear();
        View divider = LayoutInflater.from(mContext)
            .inflate(R.layout.item_seperator, mInDisplay, false);
        mInDisplay.addView(divider);
    }

    private void execute() {
        ArrayList<Double> factors = new ArrayList<>();
        Double groupFactor = 0.0;
        for (CalElement calElement : mCalElements) {
            String operator = calElement.getOperator();
            if (!calElement.isHasNum()) break;
            Double number = calElement.getNum();
            if (operator == null || operator.equals(MainActivity.OP_ADD) || operator.equals(
                MainActivity.OP_SUB) ||
                operator.equals(MainActivity.OP_EQUAL)) {
                Double lastFactor = number * ((operator != null && operator.equals(
                    MainActivity.OP_SUB)) ? -1 : 1);
                factors.add(lastFactor);
                groupFactor = lastFactor;
            } else {
                if (operator.equals(MainActivity.OP_MUL)) {
                    groupFactor *= number;
                } else if (operator.equals(MainActivity.OP_DIV)) {
                    groupFactor /= number;
                } else if (operator.equals(MainActivity.OP_MOD)) {
                    groupFactor = groupFactor % number;
                }
                factors.set(factors.size() - 1, groupFactor);
            }
        }
        mResult = 0.0;
        for (Double factor : factors) {
            mResult += factor;
        }
        drawResultView();
    }

    private void drawResultView() {
        if (mResultView == null) {
            mResultView = (ViewGroup) LayoutInflater.from(mContext)
                .inflate(R.layout.item_cal_display, mDisplay, false);
            mResultView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1
            ));
            mDisplay.addView(mResultView);
        }
        TextView operator = mResultView.findViewById(R.id.text_operator);
        TextView result = mResultView.findViewById(R.id.text_number);
        operator.setText("=");
        result.setText(FormatHelper.formatDouble(mResult));
    }

    private void removeResultView() {
        mDisplay.removeView(mResultView);
        mResultView = null;
    }
}
