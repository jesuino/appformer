package org.dashbuilder.backend;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.security.shared.service.AuthenticationService;
import org.uberfire.annotations.Customizable;
import org.uberfire.annotations.FallbackImplementation;
import org.uberfire.preferences.client.store.PreferenceBeanStoreClientImpl;
import org.uberfire.preferences.shared.PreferenceScopeResolutionStrategy;
import org.uberfire.preferences.shared.UsernameProvider;
import org.uberfire.preferences.shared.bean.PreferenceBeanStore;
import org.uberfire.preferences.shared.impl.DefaultPreferenceScopeResolutionStrategy;
import org.uberfire.rpc.SessionInfo;
import org.uberfire.rpc.impl.SessionInfoImpl;

@ApplicationScoped
public class ServerSideProducers {

    @Inject
    @FallbackImplementation
    DefaultPreferenceScopeResolutionStrategy defaultPreferenceScopeResolutionStrategy;

    @Produces
    @ApplicationScoped
    public User produceUser() {
        return User.ANONYMOUS;
    }

    @Produces
    @ApplicationScoped
    public SessionInfo produceSessionInfo() {
        return new SessionInfoImpl();
    }

    @Produces
    @ApplicationScoped
    public PreferenceBeanStore producePreferenceBeanStore() {
        return new PreferenceBeanStoreClientImpl();
    }

    @Produces
    @Customizable
    @ApplicationScoped
    public PreferenceScopeResolutionStrategy producePreferenceScopeResolutionStrategy() {
        return defaultPreferenceScopeResolutionStrategy;
    }

    @Produces
    @ApplicationScoped
    public UsernameProvider produceUsernameProvider() {
        return new UsernameProvider() {

            @Override
            public String get() {
                return User.ANONYMOUS.getIdentifier();
            }
        };
    }

    @Produces
    @ApplicationScoped
    public AuthenticationService produceAuthenticationService() {
        return new AuthenticationService() {

            @Override
            public void logout() {
            }

            @Override
            public User login(String username, String password) {
                return null;
            }

            @Override
            public boolean isLoggedIn() {
                return false;
            }

            @Override
            public User getUser() {
                return User.ANONYMOUS;
            }
        };
    }

}
