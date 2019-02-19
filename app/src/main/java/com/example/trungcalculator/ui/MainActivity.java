package com.example.trungcalculator.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trungcalculator.R;
import com.example.trungcalculator.util.CalControl;
import com.example.trungcalculator.util.ControlDel;
import com.example.trungcalculator.util.ControlDot;
import com.example.trungcalculator.util.ControlNeg;
import com.example.trungcalculator.util.ControlNumber;
import com.example.trungcalculator.util.ControlOperator;
import com.example.trungcalculator.util.ControlReset;
import com.example.trungcalculator.util.Display;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String OP_ADD = "+";
    public static final String OP_SUB = "-";
    public static final String OP_DIV = "/";
    public static final String OP_MUL = "*";
    public static final String OP_EQUAL = "=";
    public static final String OP_MOD = "%";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            ArrayList<CalControl> mCalControls;
            Display mDisplay;
            mDisplay = new Display(this, (ViewGroup) findViewById(R.id.linear_inDisplay),
                (ViewGroup) findViewById(R.id.linear_display));
            mCalControls = new ArrayList<CalControl>();
            mCalControls.add(new ControlReset((Button) findViewById(R.id.button_reset),
                mDisplay));
            for (int i = 0; i <= 9; i++) {
                Button button = findViewById(getResources().
                    getIdentifier("button_" + i, "id",
                        getPackageName()));
                mCalControls.add(new ControlNumber(button, mDisplay, i));
            }
            mCalControls.add(new ControlOperator((Button) findViewById(R.id.button_add),
                mDisplay, MainActivity.OP_ADD));
            mCalControls.add(new ControlOperator((Button) findViewById(R.id.button_sub),
                mDisplay, MainActivity.OP_SUB));
            mCalControls.add(new ControlOperator((Button) findViewById(R.id.button_multi),
                mDisplay, MainActivity.OP_MUL));
            mCalControls.add(new ControlOperator((Button) findViewById(R.id.button_div),
                mDisplay, MainActivity.OP_DIV));
            mCalControls.add(new ControlOperator((Button) findViewById(R.id.button_cal),
                mDisplay, MainActivity.OP_EQUAL));
            mCalControls.add(new ControlOperator((Button) findViewById(R.id.button_mod),
                mDisplay, MainActivity.OP_MOD));
            mCalControls.add(new ControlDot((Button) findViewById(R.id.button_dot), mDisplay));
            mCalControls.add(new ControlDel((Button) findViewById(R.id.button_del), mDisplay));
            mCalControls.add(new ControlNeg((Button) findViewById(R.id.button_neg), mDisplay));
        }
    }
}
