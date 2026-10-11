package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserMessage {
    public static final int HELLO=0;
    public static final int GOODBYE=1;


    private String text;
    private int status;
    private Boolean isReceive=false;
}