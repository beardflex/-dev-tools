package com.beardflex.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by David on 20/03/2017.
 */
@Entity
public class HtmlImage implements Serializable {
    private static final long defaultSerialUid = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private long id;
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="imageData")
    private byte[] imageData;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
