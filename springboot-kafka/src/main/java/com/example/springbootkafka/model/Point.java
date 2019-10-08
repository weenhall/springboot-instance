package com.example.springbootkafka.model;

/**
 * Created by ween on 2019/3/22
 */
public class Point {
    private String id;
    private Integer bf;
    private Integer af;

    public Point(String id,Integer bf,Integer af){
        this.id=id;
        this.bf=bf;
        this.af=af;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBf() {
        return bf;
    }

    public void setBf(Integer bf) {
        this.bf = bf;
    }

    public Integer getAf() {
        return af;
    }

    public void setAf(Integer af) {
        this.af = af;
    }
}
