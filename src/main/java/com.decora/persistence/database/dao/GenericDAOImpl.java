package com.decora.persistence.database.dao;


import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

class GenericDAOImpl<T> extends BasicDAO<T, ObjectId> implements GenericDAO<T> {

    private final Class entityClass;

    GenericDAOImpl(final Class<T> theEntityClass, final Datastore ds) {
        super(theEntityClass, ds);
        entityClass = theEntityClass;
    }

    @Override
    public final T create(final T entity) {
        this.getDatastore().save(entity);
        return entity;
    }

    @Override
    public final UpdateResults update(final T entity, final UpdateOperations<T> theOperations) {
        return this.getDatastore().update(entity, theOperations);
    }

    @Override
    public final T retrieve(final ObjectId theId) {
        return this.getDatastore().get((Class<T>) entityClass, theId);
    }

    @Override
    public final WriteResult delete(final T entity) {
        return this.getDatastore().delete(entity);
    }

    @Override
    public final List<T> retrieveAll() {
        return this.getDatastore().find(entityClass).asList();
    }

}
