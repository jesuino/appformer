package org.dashbuilder.shared.model;

import java.util.List;

import org.dashbuilder.navigation.NavTree;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;

@Portable
public class RuntimeModel {

    NavTree navTree;

    List<LayoutTemplate> layoutTemplates;

    public RuntimeModel(@MapsTo("navTree") NavTree navTree,
                        @MapsTo("layoutTemplates") List<LayoutTemplate> layoutTemplates) {
        this.navTree = navTree;
        this.layoutTemplates = layoutTemplates;
    }

    public NavTree getNavTree() {
        return navTree;
    }

    public List<LayoutTemplate> getLayoutTemplates() {
        return layoutTemplates;
    }

}