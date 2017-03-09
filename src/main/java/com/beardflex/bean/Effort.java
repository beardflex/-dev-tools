package com.beardflex.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 09/03/2017.
 */
public class Effort implements Serializable {
    private static final long serialVersionUID = 1l;

    private String name;
    private EffortType type;

    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime completedDate;

    private List<Effort> children;

    public Effort() {
        children = new ArrayList<Effort>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EffortType getType() {
        return type;
    }

    public void setType(EffortType type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public List<Effort> getChildren() {
        return children;
    }

    public void setChildren(List<Effort> children) {
        this.children = children;
    }
}
