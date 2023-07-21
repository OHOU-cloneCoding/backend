package com.project.ohouclonecoding.dto;

import lombok.Getter;

@Getter
public class Result {
    private String msg;
    private int statusCode;

    public Result(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
