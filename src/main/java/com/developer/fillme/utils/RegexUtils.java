package com.developer.fillme.utils;

public class RegexUtils {
    public static final String REGEX_DATE_YYYY_MM_DD = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String REGEX_EMAIL_REGEX = "^[\\w.\\-]+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";

    public static void main(String[] args) {
        String[] emails = {
                "user@test.com",
                "invalid_email@",
                "user123@domain.org",
                "user@navier.com",
        };

        for (String email : emails) {
            if (email.matches(REGEX_EMAIL_REGEX)) {
                System.out.println(email + " is a valid email.");
            } else {
                System.out.println(email + " is not a valid email.");
            }
        }
    }
}
