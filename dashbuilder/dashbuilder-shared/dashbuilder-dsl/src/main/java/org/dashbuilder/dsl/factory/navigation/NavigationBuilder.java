package org.dashbuilder.dsl.factory.navigation;

import org.dashbuilder.dsl.model.Navigation;
import org.dashbuilder.dsl.model.NavigationGroup;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.navigation.impl.NavTreeImpl;

public class NavigationBuilder {

    private NavTree navTree;

    private NavigationBuilder(NavTree navTree) {
        this.navTree = navTree;
    }

    public static NavigationBuilder newBuilder(NavigationGroup root) {
        
        NavTree tree = new NavTreeImpl(NavigationGroupBuilder.newBuilder("Top Group",root).build().getNavGroup());
        return new NavigationBuilder(tree);
    }

    public Navigation build() {
        return Navigation.of(this.navTree);
    }

    static NavigationBuilder newBuilder() {
        NavTree tree = new NavTreeImpl();
        return new NavigationBuilder(tree);
    }

}