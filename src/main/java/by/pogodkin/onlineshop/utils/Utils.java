package by.pogodkin.onlineshop.utils;

public class Utils {

    public static String moneyToDisplay(double amount) {
        String s = String.valueOf(Math.round(amount * 100) / 100d);
        int digitsBeforePoint = 0;

        for (int i = 0; ; i++) {
            if (s.charAt(i) == '.') break;
            digitsBeforePoint++;
        }

        while ((s.length() - digitsBeforePoint - 1 ) < 2) s = s + "0";
        return s;
    }
}
