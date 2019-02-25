package com.example.trungcalculator.util;

import android.view.View;
import android.widget.Button;

public final class ControlDel extends CalControl {
    public ControlDel(Button button, final Display display) {
        super(button);
        mControlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.del();
            }
        });
    }
}
