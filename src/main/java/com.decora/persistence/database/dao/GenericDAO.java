package com.decora.persistence.database.dao;

import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

interface GenericDAO<T> extends DAO<T, ObjectId> {

    T create(final T entity);

    UpdateResults update(final T entity, final UpdateOperations<T> operations);

    T retrieve(final ObjectId id);

    WriteResult delete(final T entity);

    List<T> retrieveAll();
}
