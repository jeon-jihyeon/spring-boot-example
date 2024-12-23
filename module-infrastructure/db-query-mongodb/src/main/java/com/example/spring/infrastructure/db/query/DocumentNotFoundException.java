package com.example.spring.infrastructure.db.query;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class DocumentNotFoundException extends BaseException {
    public DocumentNotFoundException() {
        super(ErrorCode.NOT_FOUND, null);
    }
}
