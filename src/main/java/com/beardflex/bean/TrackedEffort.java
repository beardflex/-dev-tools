package com.beardflex.bean;

/**
 * Created by David on 09/03/2017.
 */
public abstract class TrackedEffort extends Effort{
    private String issueNumber;

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }
}
