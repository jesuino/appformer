package org.dashbuilder.renderer.c3.client;

import org.dashbuilder.displayer.client.AbstractGwtDisplayerView;
import org.dashbuilder.renderer.c3.client.jsbinding.C3;
import org.dashbuilder.renderer.c3.client.jsbinding.C3ChartConf;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

public abstract class C3DisplayerView extends AbstractGwtDisplayerView<C3Displayer> implements C3Displayer.View {

    private Panel container = new FlowPanel();
    private Panel filterPanel = new HorizontalPanel();
    private Panel displayerPanel = new FlowPanel();
    private HTML titleHtml = new HTML();
    
    String uniqueId = Document.get().createUniqueId();

    @Override
    public void init(C3Displayer presenter) {
        super.setPresenter(presenter);
        super.setVisualization(container);
        container.add(titleHtml);
        container.add(filterPanel);
        container.add(displayerPanel);
        displayerPanel.getElement().setAttribute("id", uniqueId);
    }

    @Override
    public void clear() {
        super.clear();
        displayerPanel.clear();
    }

    @Override
    public void updateChart(C3ChartConf conf) {
        clear();
        conf.setBindto(displayerPanel.getElement());
        C3.generate(conf);
    }

    @Override
    public String getId() {
        return uniqueId;
    }
    

}
