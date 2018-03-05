package com.decora.persistence.database.dao;

import com.decora.persistence.FieldNameDefault;
import com.decora.persistence.model.systemuser.SystemUser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import javax.ejb.LocalBean;
import java.util.List;

@LocalBean
public class SystemUserDAO extends GenericDAOImpl<SystemUser> {

    private static final String DEFAULTSKIP = "0";
    private static final String DEFAUTLLIMIT = "100";

    public SystemUserDAO(final Class<SystemUser> entityClass, final Datastore ds) {
        super(entityClass, ds);
    }

    public final List<SystemUser> filterByEmail(final String theEmail) {
        return filterSystemUser(null, theEmail, null, null, null, null, null, null);
    }

    public final List<SystemUser> filterSystemUser(final String theName, final String theEmail,
                                                   final String thePhone, final String theTown, final String theState,
                                                   final String theOrderBy, final String theSkip, final String theLimit) {

        FindOptions findOptions = new FindOptions();
        if (theSkip == null || theSkip.isEmpty()) {
            findOptions.skip(Integer.parseInt(DEFAULTSKIP));
        } else {
            findOptions.skip(Integer.parseInt(theSkip));
        }
        if (theLimit == null || theLimit.isEmpty()) {
            findOptions.limit(Integer.parseInt(DEFAUTLLIMIT));
        } else {
            findOptions.limit(Integer.parseInt(theLimit));
        }

        return buildQuery(theName, theEmail, thePhone, theTown, theState, theOrderBy)
                .asList(findOptions);
    }

    private Query<SystemUser> buildQuery(final String theName, final String theEmail, final String thePhone,
                                         final String theTown, final String theState, final String theOrderBy) {

        Query<SystemUser> userQuery = this.getDatastore().createQuery(SystemUser.class);

        if (theName != null && !theName.isEmpty()) {
            userQuery.criteria(FieldNameDefault.NAME).containsIgnoreCase(theName);
        }
        if (theEmail != null && !theEmail.isEmpty()) {
            userQuery.criteria(FieldNameDefault.EMAIL).containsIgnoreCase(theEmail);
        }
        if (thePhone != null && !thePhone.isEmpty()) {
            userQuery.criteria(FieldNameDefault.PHONE).containsIgnoreCase(thePhone);
        }
        if (theTown != null && !theTown.isEmpty()) {
            userQuery.criteria(FieldNameDefault.TOWN).containsIgnoreCase(theTown);
        }
        if (theState != null && !theState.isEmpty()) {
            userQuery.criteria(FieldNameDefault.STATE).containsIgnoreCase(theState);
        }
        if (theOrderBy != null && !theOrderBy.isEmpty()) {
            userQuery.order(theOrderBy);
        }
        return userQuery;
    }
}
