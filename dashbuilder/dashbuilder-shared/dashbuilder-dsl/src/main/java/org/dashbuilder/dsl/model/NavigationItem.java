package org.dashbuilder.dsl.model;

import org.dashbuilder.navigation.NavItem;

public class NavigationItem {

    private NavItem navItem;

    public NavigationItem(NavItem navItem) {
        this.navItem = navItem;
    }

    public static NavigationItem of(NavItem navItem) {
        return new NavigationItem(navItem);
    }

    public NavItem getNavItem() {
        return navItem;
    }

}