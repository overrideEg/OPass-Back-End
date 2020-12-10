package com.overrideeg.apps.opass.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexUtils {
    private static String pattern = "<(.+?)>";

    public static List<String> findCodingFormula(String formula) {
        List<String> returnValue = new ArrayList<>();
        try {
            Pattern pattern = Pattern.compile(regexUtils.pattern);
            Matcher matcher = pattern.matcher(formula);
            while (matcher.find()) {
                returnValue.add(matcher.group().substring(1, matcher.group().length() - 1));
            }
        } catch (Exception e) {

        }
        return returnValue;
    }
}
