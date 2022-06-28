package com.spring.async.rest.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class TestVo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    private String body;

    public TestVo() {
    }

    public TestVo(int userId, int id, String title, boolean completed, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.body = body;
    }
}
