package com.healthedge.codeloaders.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

    private static final String MULTIPLE_SPACES = "\\\\s{2,}";
    private static final String SINGLE_SPACE = " ";

    public static String replaceMultipleSpacesWithSingleSpace (String input) {
        String output = input;
        if (!isStringEmptyOrBlank(input)) {
            String temp = input.trim();
            output = StringUtils.replace(temp, MULTIPLE_SPACES, SINGLE_SPACE);
        }
        return output;
    }

    public static String restrictLengthOfInput (String input, int limit) {
        String output = input;
        if (!isStringEmptyOrBlank(input)) {
            String temp = input.trim();
            if (StringUtils.isNotBlank(temp) && temp.length() > limit) {
                output = StringUtils.substring(temp, 0, limit);
            }
        }
        return output;
    }

    public static boolean isStringEmptyOrBlank (String input) {
        boolean isEmptyOrBlank = true;
        if (StringUtils.isNotEmpty(input) && StringUtils.isNotBlank(input.trim())) {
            isEmptyOrBlank = false;
        }
        return isEmptyOrBlank;
    }
}
