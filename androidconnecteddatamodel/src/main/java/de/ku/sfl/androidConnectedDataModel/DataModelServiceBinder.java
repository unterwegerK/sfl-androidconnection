package de.ku.sfl.androidConnectedDataModel;

import android.os.Binder;

import de.ku.sfl.androidConnectedDataModel.api.IConnectedDataModel;

public class DataModelServiceBinder<TDataModel extends IConnectedDataModel> extends Binder {

    private DataModelService dataModelService;

    public DataModelServiceBinder(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    public DataModelService getConnectionService() {
        return dataModelService;
    }
}
