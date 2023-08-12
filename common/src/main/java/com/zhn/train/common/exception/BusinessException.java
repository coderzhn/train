package com.zhn.train.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    private BusinessExceptionEnum e;
    @Override
    public Throwable fillInStackTrace(){
        return this;
    }
}
