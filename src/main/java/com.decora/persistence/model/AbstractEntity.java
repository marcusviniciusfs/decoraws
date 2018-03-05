package com.decora.persistence.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

public abstract class AbstractEntity {

    @Id
    @Property("id")
    private ObjectId id;

    @Version
    @Property("version")
    private Long version;

    public AbstractEntity() {
        super();
    }

    public final ObjectId getId() {
        return id;
    }

    public final void setId(final ObjectId theId) {
        this.id = theId;
    }

    public final Long getVersion() {
        return version;
    }

    public final void setVersion(final Long theVersion) {
        this.version = theVersion;
    }
}
