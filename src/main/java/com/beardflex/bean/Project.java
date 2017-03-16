package com.beardflex.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by David on 07/03/2017.
 */
@Entity
@Table(name="project")
public class Project extends Effort {
    @Column(name="version")
    private Version version;
    @Column(name="codeName")
    private String codeName;

    public Project() {
    }

    public Project(Effort effort) {
        setParent(effort.getParent());
        setName(effort.getName());
        setDueDate(effort.getDueDate());
        setStartDate(effort.getStartDate());
        setCompletedDate(effort.getCompletedDate());
    }

    public Project(String name, String codeName, Version version) {
        setName(name);
        setType(EffortType.Project);
        setCodeName(codeName);
        setVersion(version);
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
