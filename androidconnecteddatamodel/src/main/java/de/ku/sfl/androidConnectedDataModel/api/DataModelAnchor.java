package de.ku.sfl.androidConnectedDataModel.api;

import android.content.Context;
import android.content.Intent;

import de.ku.sfl.androidConnectedDataModel.DataModelService;

/**
 * Represents the ownership about the data model and defines its lifecycle.
 */
public class DataModelAnchor {

    private final Context context;
    private final Class<?> dataModelType;

    public DataModelAnchor(Context context, Class<?> dataModelType) {
        this.context = context;
        this.dataModelType = dataModelType;
    }

    public void ownerCreated() {
        Intent intent = new Intent(context, DataModelService.class);
        intent.putExtra(DataModelService.DATA_MODEL_TYPE_NAME_ID, dataModelType.getCanonicalName());
        context.startService(intent);
    }

    public void ownerDestroyed() {
        context.stopService(new Intent(context, DataModelService.class));
    }
}
