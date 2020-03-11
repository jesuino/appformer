package org.dashbuilder.backend;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.jboss.errai.security.shared.api.identity.User;
import org.uberfire.commons.services.cdi.Startup;
import org.uberfire.commons.services.cdi.StartupType;

@ApplicationScoped
@Startup(value = StartupType.BOOTSTRAP)
public class ApplicationScopedProducer {

    @Produces
    @RequestScoped
    public User getIdentity() {
        return User.ANONYMOUS;
    }

    
}