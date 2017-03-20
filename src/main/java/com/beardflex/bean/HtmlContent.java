package com.beardflex.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by David on 20/03/2017.
 */
@Entity
public class HtmlContent implements Serializable {
    private static final long serialVersionUid = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private long id;
    @Column(name="html", length=(10000))
    private String html;
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<HtmlImage> images;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<HtmlImage> getImages() {
        return images;
    }

    public void setImages(List<HtmlImage> images) {
        this.images = images;
    }
}
