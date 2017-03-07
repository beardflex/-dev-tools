package com.beardflex.bean;

import java.io.Serializable;

/**
 * Created by David on 07/03/2017.
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 1l;

    private long id;
    private String name;
    private String version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
