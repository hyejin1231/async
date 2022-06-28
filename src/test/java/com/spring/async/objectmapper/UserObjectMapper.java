package com.spring.async.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class UserObjectMapper {

    private final ObjectMapper objectMapper;

    public UserObjectMapper() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Java -> Json")
    public void test1() throws JsonProcessingException {
        User user1 = new User("user1", 25);
        String userAsString = objectMapper.writeValueAsString(user1);

        System.out.println("userAsString = " + userAsString);
    }

    @Test
    @DisplayName("Json -> Java")
    public void test2() throws JsonProcessingException {
        String json = "{\"name\":\"user1\",\"age\":25}";
        User user = objectMapper.readValue(json, User.class);

        System.out.println("user.toString() = " + user.toString());
    }

    @Test
    @DisplayName("Json -> Java List")
    public void test3() throws JsonProcessingException {
        String userJson = "[{\"name\":\"user1\",\"age\":25}, {\"name\":\"user2\",\"age\":30}]";
        List<User> users = objectMapper.readValue(userJson, new TypeReference<List<User>>() {
        });

        for (User user : users) {
            System.out.println("user.toString() = " + user.toString());
        }
    }

    @Test
    @DisplayName("Json -> Map")
    public void test4() throws JsonProcessingException {
        String jsonMap = "{\"name\":\"user1\",\"age\":25}";
        Map<String, Object> map = objectMapper.readValue(jsonMap, new TypeReference<Map<String, Object>>() {});

        for (String s : map.keySet()) {
            System.out.println(s  + " : " + map.get(s));
        }
    }
}
