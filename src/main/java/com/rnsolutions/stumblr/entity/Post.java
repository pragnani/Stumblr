package com.rnsolutions.stumblr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * This is the base for a Post.  Perhaps this should be abstract?
 *
 * @author bsneade
 * @version $Id: $
 */
//@Entity
@MappedSuperclass
//@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Post implements Serializable {

	 
    private Long id;

    private String author;

    @Temporal(TemporalType.DATE) 
    private Date createDate;

    /**
     * The ID for the class.  Since we use an Oracle Database, normally we have
     * a sequence here.  Also, because Oracle can be annoying, there is some
     * additional stuff we have to do here to register the Oracle Sequnce.
     *
     * @return a {@link java.lang.Long} object.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Maintain a field for optimistic locking.  Yes, I know Date is not JPA supported, but
     * Hibernate seems to be happy with it.
     *
     * @return a {@link java.util.Date} object.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Version
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>Setter for the field <code>createDate</code>.</p>
     *
     * @param createDate a {@link java.util.Date} object.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = new Date();
    }

    @Column(length=60)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
