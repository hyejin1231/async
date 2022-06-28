package com.spring.async.ctl;

import com.spring.async.ex.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Map<String, String> handleException(Exception e) {

        Map<String, String> map = new HashMap<>();

        map.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        map.put("message", e.getMessage());

        return map;
    }

}
