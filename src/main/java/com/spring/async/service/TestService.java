package com.spring.async.service;

import com.spring.async.ex.MyException;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void test() {
        throw new MyException("error!!");
    }
}
