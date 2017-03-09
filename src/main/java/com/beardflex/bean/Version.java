package com.beardflex.bean;

import java.io.Serializable;

/**
 * Created by David on 09/03/2017.
 */
public class Version implements Serializable {

    private final static long defaultSerialID = 1l;

    private int major;
    private int minor;
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
}
