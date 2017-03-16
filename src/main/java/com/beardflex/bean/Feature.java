package com.beardflex.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by David on 09/03/2017.
 */
@Entity
@Table(name="feature")
public class Feature extends TrackedEffort {
    public Feature() {
        setType(EffortType.Feature);
    }
}
