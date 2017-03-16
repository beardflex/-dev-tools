package com.beardflex.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by David on 09/03/2017.
 */
@Entity
public class Version implements Serializable {

    private final static long defaultSerialID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private long id;
    @Column(name="major")
    private int major;
    @Column(name="minor")
    private int minor;
    @Column(name="patch")
    private int patch;

    public Version() {

    }

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s", major, minor, patch);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
