package com.example.trungcalculator.util;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class FormatHelper {
    public static String formatDouble(double num) {
        MessageFormat format = new MessageFormat(
            "{0,choice,0#{0,number,'#,##0.####'}|99999<{0,number,'000000.####E0'}}",
            Locale.ENGLISH);
        return format.format(new Object[] {num});
    }

    public static Double getDouble(String numString) {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        Number number = null;
        try {
            number = format.parse(numString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return number.doubleValue();
    }
}
