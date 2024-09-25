package com.developer.fillme.constant;

import org.apache.commons.lang3.StringUtils;

public enum EGender {
    MALE, FEMALE, OTHER;

    public static EGender fromString(String gender) {
        if (StringUtils.isBlank(gender)) {
            return OTHER;
        }
        return switch (gender.trim().toLowerCase()) {
            case "male" -> EGender.MALE;
            case "female" -> EGender.FEMALE;
            default -> EGender.OTHER;
        };
    }
}
