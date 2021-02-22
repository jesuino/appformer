package org.dashbuilder.dsl.factory.navigation;

import org.dashbuilder.dsl.model.NavigationItem;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.impl.NavItemImpl;

public class NavigationItemBuilder {

    private NavItem navItem;

    private NavigationItemBuilder(NavItem navItem) {
        this.navItem = navItem;
    }

    public static NavigationItemBuilder newBuilder(Page page) {
        NavItem item = new NavItemImpl();
        String name = page.getLayoutTemplate().getName();
        item.setId(System.currentTimeMillis() + "");
        item.setName(name);
        item.setModifiable(false);
        item.setContext("resourceId=" + name + ";resourceType=PERSPECTIVE;");
        return new NavigationItemBuilder(item);
    }

    public NavigationItemBuilder name(String name) {
        this.navItem.setName(name);
        return this;
    }

    public NavigationItem build() {
        return NavigationItem.of(this.navItem);
    }

}