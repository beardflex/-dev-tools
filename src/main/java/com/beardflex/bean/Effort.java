package com.beardflex.bean;

import com.beardflex.db.converter.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 09/03/2017.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Effort implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private long id;
    @Column(name="name", length=255)
    private String name;
    @Column(name="type")
    @Enumerated(EnumType.ORDINAL)
    private EffortType type;

    @Column(name="startDate")
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate startDate;
    @Column(name="dueDate")
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate dueDate;
    @Column(name="completedDate")
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate completedDate;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="parent_id", referencedColumnName="id", nullable=true)
    private Effort parent;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="parent", cascade=CascadeType.ALL)
    private List<Effort> children;
    @OneToOne
    @JoinColumn(name="id")
    private HtmlContent description;

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
    @Column()
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HtmlContent getDescription() {
        return description;
    }

    public void setDescription(HtmlContent description) {
        this.description = description;
    }
}
