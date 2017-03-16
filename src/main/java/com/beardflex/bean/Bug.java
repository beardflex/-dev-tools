package com.beardflex.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by David on 09/03/2017.
 */
@Entity
@Table(name="bug")
public class Bug extends TrackedEffort {
    @Column(name="povNumber")
    private String povNumber;
    public Bug() {
        setType(EffortType.Bug);
    }

    public String getPovNumber() {
        return povNumber;
    }

    public void setPovNumber(String povNumber) {
        this.povNumber = povNumber;
    }
}
