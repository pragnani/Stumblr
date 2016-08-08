package com.rnsolutions.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Base Hibernate implementation for hibernate implemented DAOs.
 *
 * It is probably kind of odd, but we use the JPA annotations for configuration,
 * however, we still use the underlying hibernate SessionFactory to do
 * persistence.
 *
 * @author bsneade
 * @param <T> The Type of Entity that this DAO will be dealing with.
 * @param <PK> The Type of Primary Key this entity uses.
 * @version $Id: $
 */
public abstract class HibernateGenericDao<T extends Serializable, PK extends Serializable>
        extends HibernateDaoSupport implements GenericDao<T, PK> {

    /**
     * Because Generics are erased at runtime we have to determine the type of
     * Entity this DAO implementation is dealing with on initialization
     */
    protected final Class<? extends T> type;

    /**
     * <p>Constructor for HibernateGenericDao.</p>
     *
     * Uses a bunch of reflection magic to get the class type the generic
     * represents since they are erased at run time.
     *
     * @param <T> The Type of Entity that this DAO will be dealing with.
     * @param <PK> The Type of Primary Key this entity uses.
     */
    public HibernateGenericDao() {
        super();

        // Use reflection to determine which entity class this service deals
        // with
        // Note: we have to handle the weird case of this being a cglib proxy
        Type thisType = getClass().getGenericSuperclass();
        while (thisType instanceof Class) {
            //the while loops up the heirarchy in the case that a class extends a genericised class.
            thisType = ((Class) thisType).getGenericSuperclass();
        } //end while

        final Type localType;
        if (thisType instanceof ParameterizedType) {
            localType = ((ParameterizedType) thisType).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Problem handling type construction for " + getClass());
        }

        // handled nested generics (i.e. the entity is
        // genericised)
        if (localType instanceof Class) {
            this.type = (Class<T>) localType;
        } else if (localType instanceof ParameterizedType) {
            this.type = (Class<T>) ((ParameterizedType) localType).getRawType();
        } else {
            throw new IllegalArgumentException("Problem determining the class of the generic for " + getClass());
        }
    }

    /**
     * <p>Constructor for HibernateGenericDao.</p>
     *
     * Bypasses the Generics type detection and explicitly sets the type.
     *
     * @param type a {@link java.lang.Class} object to explicitly set as the type.
     */
    protected HibernateGenericDao(final Class<? extends T> type) {
        super();
        this.type = type;
    }

    /** {@inheritDoc} */
    @Override
    public void saveOrUpdate(final T transientInstance) {
        getHibernateTemplate().saveOrUpdate(transientInstance);
    }

    /** {@inheritDoc} */
    @Override
    public T findById(final PK id) {
        return (T) getHibernateTemplate().get(type, id);
    }

    /** {@inheritDoc} */
    @Override
    public List<T> search(final T example) {
        return search(example, type);
    }

    /** {@inheritDoc}
    Enables 'like' (with MatchMode.ANYWHERE), and 'ignoreCase'.
     */
    @Override
    public List<T> search(final T example, final Class<? extends T> searchType) {
        final Criteria criteria = getSession().createCriteria(searchType);

        final Example pExample = Example.create(example);

        pExample.enableLike();
        pExample.ignoreCase();
        pExample.enableLike(MatchMode.ANYWHERE);

        criteria.add(pExample);

        final List resultList = criteria.list();
        return resultList;
    }

    /** {@inheritDoc} */
    @Override
    public void delete(final T persistentInstance) {
        getHibernateTemplate().delete(persistentInstance);
    }
}
