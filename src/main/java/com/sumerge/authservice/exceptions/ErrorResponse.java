package com.sumerge.authservice.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private Integer status;
    private String message;
    private String errorClass;
    private Date timeStamp;

}
