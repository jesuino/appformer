package org.dashbuilder.client.navbar;

import javax.enterprise.context.ApplicationScoped;

import org.uberfire.client.workbench.widgets.menu.megamenu.brand.MegaMenuBrand;

@ApplicationScoped
public class RuntimeNavBrand implements MegaMenuBrand {

    @Override
    public String brandImageUrl() {
        return "/images/runtime_logo.png";
    }

    @Override
    public String brandImageLabel() {
        return "Dashbuilder Runtime";
    }

    @Override
    public String menuAccessorLabel() {
        return "Dashboards";
    }

}
