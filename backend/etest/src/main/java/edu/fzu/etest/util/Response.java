package edu.fzu.etest.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    public static int SUCCESS = 200;
    public static int FAILURE = 400;
    private int code;
    private String message;
    private Object data;

}
