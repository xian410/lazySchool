package com.example.demo.management.entity.enums;

import lombok.Getter;

/**
 * @author liangkeyu
 * @since 2021-01-28
 */
public enum GenderEnum {

    /**
     * 男
     */
    MALE(1, "男"),

    /**
     * 女
     */
    FEMALE(0, "女"),

    /**
     * 未知
     */
    UNKNOWN(-1, ""),
    ;

    @Getter
    private int gender;

    @Getter
    private String message;

    GenderEnum(int gender, String message) {
        this.gender = gender;
        this.message = message;
    }

    public static String getMessageByGender(int gender) {
        String message = UNKNOWN.getMessage();
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getGender() == gender) {
                message = genderEnum.getMessage();
                break;
            }
        }
        return message;
    }
}
