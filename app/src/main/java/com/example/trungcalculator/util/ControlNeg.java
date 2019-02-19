package com.example.trungcalculator.util;

import android.view.View;
import android.widget.Button;

public final class ControlNeg extends CalControl {
    public ControlNeg(Button button, final Display display) {
        super(button);
        mControlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.neg();
            }
        });
    }
}
