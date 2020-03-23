package org.dashbuilder.client.cms.screen.transfer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.transfer.DataTransferAssets;
import org.dashbuilder.transfer.DataTransferExportModel;
import org.dashbuilder.transfer.DataTransferServices;
import org.jboss.errai.common.client.api.Caller;
import org.uberfire.client.mvp.UberElemental;
import org.uberfire.mvp.ParameterizedCommand;

@ApplicationScoped
public class DataTransferExportPopUp {

    @Inject
    private View view;

    @Inject
    private Caller<DataTransferServices> dataTransferServices;

    private ParameterizedCommand<DataTransferExportModel> dataTransferExportModelCallback;

    public interface View extends UberElemental<DataTransferExportPopUp> {

        void show();

        void setAssetsToExport(DataTransferAssets assetsToExport);

    }
    
    @PostConstruct
    public void init() {
        view.init(this);
    }

    public void setCallback(ParameterizedCommand<DataTransferExportModel> dataTransferExportModelCallback) {
        this.dataTransferExportModelCallback = dataTransferExportModelCallback;

    }

    public void show() {
        view.show();
        dataTransferServices.call((DataTransferAssets v) -> view.setAssetsToExport(v))
                            .assetsToExport();

    }

    public void receiveSelectedAssets(DataTransferExportModel dataTransferExportModel) {
        dataTransferExportModelCallback.execute(dataTransferExportModel);

    }
}
