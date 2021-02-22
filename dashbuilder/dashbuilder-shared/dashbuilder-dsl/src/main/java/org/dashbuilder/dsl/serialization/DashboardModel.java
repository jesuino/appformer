package org.dashbuilder.dsl.serialization;

import java.io.OutputStream;

public class DashboardModel {

    private OutputStream modelOutputStream;

    private DashboardModel(OutputStream os) {
        modelOutputStream = os;
    }

    public static DashboardModel of(OutputStream os) {
        return new DashboardModel(os);
    }

    public OutputStream getModelOutputStream() {
        return modelOutputStream;
    }

}