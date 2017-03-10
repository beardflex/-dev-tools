package com.beardflex.bean;

import java.io.Serializable;
import java.time.LocalDate;
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

    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completedDate;

    private Effort parent;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public List<Effort> getChildren() {
        return children;
    }

    public void setChildren(List<Effort> children) {
        this.children = children;
    }

    public Effort getParent() {
        return parent;
    }

    public void setParent(Effort parent) {
        this.parent = parent;
    }
}
