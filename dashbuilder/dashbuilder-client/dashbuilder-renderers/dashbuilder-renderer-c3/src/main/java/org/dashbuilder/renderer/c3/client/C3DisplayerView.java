package org.dashbuilder.renderer.c3.client;

import org.dashbuilder.displayer.client.AbstractGwtDisplayerView;
import org.dashbuilder.renderer.c3.client.jsbinding.C3;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartConf;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class C3DisplayerView extends AbstractGwtDisplayerView<C3Displayer> implements C3Displayer.View {

    FlowPanel chartContainer = new FlowPanel();
    String uniqueId = Document.get().createUniqueId();

    @Override
    public void init(C3Displayer presenter) {
        chartContainer.getElement().setAttribute("id", uniqueId);
        super.setPresenter(presenter);
        super.setVisualization(chartContainer);
    }

    @Override
    public void clear() {
        super.clear();
        chartContainer.clear();
    }

    @Override
    public void updateChart(C3ChartConf conf) {
        chartContainer.clear();
        C3.generate(conf);
    }

    @Override
    public String getId() {
        return uniqueId;
    }

}
