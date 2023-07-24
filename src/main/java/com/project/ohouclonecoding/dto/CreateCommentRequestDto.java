package com.project.ohouclonecoding.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Date;

@Getter
public class CreateCommentRequestDto {

    @NotNull()
    private String comment;
//    private Date createdAt;

}
