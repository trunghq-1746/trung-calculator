package com.example.trungcalculator.util;

import android.view.View;
import android.widget.Button;

public final class ControlNumber extends CalControl {
    private int mNumber;

    public ControlNumber(Button button, final Display display, int number) {
        super(button);
        mNumber = number;
        mControlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.number(mNumber);
            }
        });
    }
}
