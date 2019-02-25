package com.example.trungcalculator.util;

import android.view.View;
import android.widget.Button;

public final class ControlOperator extends CalControl {
    private String mOperator;

    public ControlOperator(Button button, final Display display, String operator) {
        super(button);
        mOperator = operator;
        mControlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.operator(mOperator);
            }
        });
    }
}
