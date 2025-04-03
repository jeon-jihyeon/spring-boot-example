package com.example.spring.mongodb;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class DocumentNotFoundException extends BaseException {
    public DocumentNotFoundException() {
        super(ErrorCode.NOT_FOUND, null);
    }
}
