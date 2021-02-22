package org.dashbuilder.dsl.model;

import org.dashbuilder.navigation.NavTree;

public class Navigation {

    private NavTree navTree;

    private Navigation(NavTree navTree) {
        this.navTree = navTree;
    }

    public static Navigation of(NavTree navTree) {
        return new Navigation(navTree);
    }

    public NavTree getNavTree() {
        return navTree;
    }

}