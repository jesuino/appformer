package org.dashbuilder.dsl.factory.navigation;

import org.dashbuilder.dsl.model.Navigation;
import org.dashbuilder.dsl.model.NavigationGroup;
import org.dashbuilder.dsl.model.NavigationItem;
import org.dashbuilder.dsl.model.Page;

public class NavigationFactory {

    private NavigationFactory() {
        // empty
    }

    public static Navigation emptyNavigation() {
        return NavigationBuilder.newBuilder().build();
    }

    public static Navigation navigation(NavigationGroup root) {
        return NavigationBuilder.newBuilder(root).build();
    }

    public static NavigationGroup group(String name, NavigationItem... items) {
        return NavigationGroupBuilder.newBuilder(name, items).build();
    }

    public static NavigationItem item(Page page) {
        return NavigationItemBuilder.newBuilder(page).build();
    }

}