package com.scheduleplanner.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@Setter
public class EmailEntity {
    private String to;
    private String subject;
    private String body;
}
