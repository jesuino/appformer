package org.dashbuilder.dsl.factory.navigation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.dashbuilder.dsl.model.NavigationGroup;
import org.dashbuilder.dsl.model.NavigationItem;
import org.dashbuilder.navigation.NavGroup;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.impl.NavGroupImpl;

public class NavigationGroupBuilder {

    private NavGroup navGroup;

    private NavigationGroupBuilder(NavGroup navGroup) {
        this.navGroup = navGroup;
    }

    public static NavigationGroupBuilder newBuilder(String name, NavigationItem... items) {
        NavGroup group = new NavGroupImpl();
        List<NavItem> navItems = Arrays.stream(items).map(NavigationItem::getNavItem).collect(Collectors.toList());
        group.setName(name);
        group.setDescription("Some Group");
        group.setId(System.currentTimeMillis() + "");
        group.setChildren(navItems);
        group.setModifiable(false);
        return new NavigationGroupBuilder(group);
    }

    public NavigationGroupBuilder name(String name) {
        this.navGroup.setName(name);
        return this;
    }

    public NavigationGroupBuilder item(NavigationItem item) {
        navGroup.getChildren().add(item.getNavItem());
        return this;
    }

    public NavigationGroup build() {
        return NavigationGroup.of(this.navGroup);
    }

}