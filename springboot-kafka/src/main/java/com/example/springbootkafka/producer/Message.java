package com.example.springbootkafka.producer;

import java.util.List;

/**
 * Created by ween on 2019/3/21
 */
public class Message {
    private int type;
    private List<?> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
