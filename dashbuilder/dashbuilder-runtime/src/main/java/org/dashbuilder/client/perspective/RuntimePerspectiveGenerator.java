package org.dashbuilder.client.perspective;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.mvp.Activity;
import org.uberfire.client.mvp.ActivityBeansCache;
import org.uberfire.client.mvp.PerspectiveActivity;
import org.uberfire.client.mvp.WorkbenchScreenActivity;
import org.uberfire.ext.layout.editor.api.editor.LayoutTemplate;
import org.uberfire.ext.layout.editor.client.generator.LayoutGenerator;
import org.uberfire.ext.plugin.client.perspective.editor.generator.PerspectiveEditorActivity;
import org.uberfire.ext.plugin.client.perspective.editor.generator.PerspectiveEditorScreenActivity;
import org.uberfire.jsbridge.client.cdi.SingletonBeanDefinition;

import static org.jboss.errai.ioc.client.QualifierUtil.DEFAULT_QUALIFIERS;

@ApplicationScoped
public class RuntimePerspectiveGenerator {

    @Inject
    private SyncBeanManager beanManager;

    @Inject
    private ActivityBeansCache activityBeansCache;

    @Inject
    private LayoutGenerator layoutGenerator;

    public PerspectiveEditorActivity generatePerspective(LayoutTemplate layoutTemplate) {
        PerspectiveEditorScreenActivity screen = createNewScreen(layoutTemplate);
        return createNewPerspective(layoutTemplate, screen);
    }

    private PerspectiveEditorScreenActivity createNewScreen(LayoutTemplate perspective) {
        PerspectiveEditorScreenActivity activity = new PerspectiveEditorScreenActivity(perspective,
                                                                                       layoutGenerator);

        final Set<Annotation> qualifiers = new HashSet<>(Arrays.asList(DEFAULT_QUALIFIERS));
        final SingletonBeanDefinition<PerspectiveEditorScreenActivity, PerspectiveEditorScreenActivity> beanDef =
                new SingletonBeanDefinition<>(
                                              activity,
                                              PerspectiveEditorScreenActivity.class,
                                              qualifiers,
                                              activity.getIdentifier(),
                                              true,
                                              WorkbenchScreenActivity.class,
                                              Activity.class);

        beanManager.registerBean(beanDef);
        beanManager.registerBeanTypeAlias(beanDef,
                                          Activity.class);
        beanManager.registerBeanTypeAlias(beanDef,
                                          WorkbenchScreenActivity.class);

        activityBeansCache.addNewScreenActivity(beanManager.lookupBeans(activity.getIdentifier()).iterator().next());
        return activity;
    }

    private PerspectiveEditorActivity createNewPerspective(LayoutTemplate perspective,
                                                           PerspectiveEditorScreenActivity screen) {
        final PerspectiveEditorActivity activity = new RuntimePerspectiveEditorActivity(perspective,
                                                                                        screen);

        beanManager.registerBean(new SingletonBeanDefinition<>(activity,
                                                               PerspectiveActivity.class,
                                                               new HashSet<>(Arrays.asList(DEFAULT_QUALIFIERS)),
                                                               perspective.getName(),
                                                               true));

        activityBeansCache.addNewPerspectiveActivity(beanManager.lookupBeans(perspective.getName()).iterator().next());
        return activity;
    }

}
