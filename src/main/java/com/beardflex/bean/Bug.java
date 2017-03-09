package com.beardflex.bean;

/**
 * Created by David on 09/03/2017.
 */
public class Bug extends TrackedEffort {
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
