package org.dashbuilder.dsl.model;

import org.dashbuilder.navigation.NavGroup;

public class NavigationGroup extends NavigationItem {

    private NavGroup navGroup;

    private NavigationGroup(NavGroup group) {
        super(group);
        this.navGroup = group;
    }

    public static NavigationGroup of(NavGroup group) {
        return new NavigationGroup(group);
    }

    public NavGroup getNavGroup() {
        return navGroup;
    }

}