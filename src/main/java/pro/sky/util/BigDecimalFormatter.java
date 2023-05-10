package pro.sky.util;

import java.math.BigDecimal;

public class BigDecimalFormatter {

    public static String format(long number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        String str = bigDecimal.toPlainString();

        int spaceIndex = (str.length() % 3 != 0) ? str.length() % 3 : 3;
        StringBuilder sb = new StringBuilder(str.substring(0, spaceIndex));
        while (spaceIndex <= str.length() - 3) {
            sb.append(' ');
            sb.append(str.charAt(spaceIndex));
            sb.append(str.charAt(spaceIndex + 1));
            sb.append(str.charAt(spaceIndex + 2));
            spaceIndex += 3;
        }
        return sb.toString();
    }
}
