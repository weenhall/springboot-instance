package com.ween.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Event")
@Table(name = "event")
public class Event extends BaseEntity implements Serializable {

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "location=" + location +
                '}';
    }
}
