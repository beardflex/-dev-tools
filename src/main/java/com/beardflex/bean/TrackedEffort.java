package com.beardflex.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created by David on 09/03/2017.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class TrackedEffort extends Effort{
    @Column(name="issueNumber")
    private String issueNumber;

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }
}
