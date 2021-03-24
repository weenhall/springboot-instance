package com.ween.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author weenhall
 */
@Entity
@Table(name = "json_entity")
public class JsonEntity implements Serializable {

    @Id
    @GenericGenerator(name="idGenerator",strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String serveName;
    private Integer servePort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServeName() {
        return serveName;
    }

    public void setServeName(String serveName) {
        this.serveName = serveName;
    }

    public Integer getServePort() {
        return servePort;
    }

    public void setServePort(Integer servePort) {
        this.servePort = servePort;
    }
}
