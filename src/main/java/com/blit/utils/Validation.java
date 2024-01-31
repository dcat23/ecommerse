package com.blit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final Pattern emailPattern = Pattern.compile("^(\\w+)@(\\w+)\\.(\\w{2,10})$");

    public static boolean checkEmail(String email) {
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }
}
