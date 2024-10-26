package com.schedule_planner.util.email;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@Setter
public class EmailData {
    private String to;
    private String subject;
    private String body;
}
