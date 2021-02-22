package org.dashbuilder.dsl.serialization;

import java.io.OutputStream;

import org.dashbuilder.dsl.model.Dashboard;

public interface DashboardSerializer {
    
    
    Dashboard deserialize(DashboardModel model);
    
    DashboardModel serialize(Dashboard dashboard, OutputStream os);
    

}