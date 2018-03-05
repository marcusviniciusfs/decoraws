package com.decora.persistence.database;

import com.decora.main.RootUser;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ejb.Singleton;

@Singleton
public class MorphiaService {

    private Morphia morphia;
    private Datastore datastore;

    public MorphiaService() {

        MongoClient mongoClient = new MongoClient("localhost:27017");

        this.morphia = new Morphia();
        String databaseName = "decora";
        this.datastore = morphia.createDatastore(mongoClient, databaseName);

        //create a root system user
        RootUser rootUser = new RootUser();
        rootUser.createRootUser(this);
    }


    public final Morphia getMorphia() {
        return morphia;
    }

    public final void setMorphia(final Morphia theMorphia) {
        this.morphia = theMorphia;
    }

    public final Datastore getDatastore() {
        return datastore;
    }

    public final void setDatastore(final Datastore theDatastore) {
        this.datastore = theDatastore;
    }
}
