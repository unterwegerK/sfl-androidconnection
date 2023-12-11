package de.ku.sfl.androidConnectedDataModel.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import de.ku.sfl.androidConnectedDataModel.DataModelService;
import de.ku.sfl.androidConnectedDataModel.DataModelServiceBinder;
import de.ku.sfl.connection.api.IConnectionStateListener;

public class DataModelProvider<TDataModel> {
    private final Context context;
    private final Class<?> dataModelType;
    private DataModelService dataModelService;

    private final DataModelServiceConnection dataModelServiceConnection = new DataModelServiceConnection();

    private final Set<IConnectionStateListener> listeners = new HashSet<>();

    private class DataModelServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("ServiceDemo", "Service connected");
            if(service instanceof DataModelServiceBinder<?>) {
                dataModelService = ((DataModelServiceBinder<?>)service).getConnectionService();

                if(dataModelService != null) {
                    dataModelService.registerConnectionStateListeners(listeners);
                }
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d("ServiceDemo", "Service disconnected");
            dataModelService = null;
        }
    }

    public DataModelProvider(Context context, Class<?> dataModelType) {
        this.context = context;
        this.dataModelType = dataModelType;
    }

    public void onStarted() {
        Intent serviceIntent = new Intent(context, DataModelService.class);
        context.bindService(serviceIntent, dataModelServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onStopped() {
        if(dataModelService != null) {
            dataModelService.unregisterConnectionStateListeners(listeners);
        }
        context.unbindService(dataModelServiceConnection);
    }

    public TDataModel getDataModel() {
        if(dataModelService == null){
            return null;
        }

        Object dataModel = dataModelService.getDataModel();

        if(!dataModelType.isInstance(dataModel)) {
            Log.e("ServiceDemo", "Data model is of incorrect type.");
            return null;
        }

        return (TDataModel)dataModel;
    }

    public void registerConnectionStateListener(IConnectionStateListener listener) {
        boolean isUnknownListener = listeners.add(listener);
        if(dataModelService != null && isUnknownListener) {
            dataModelService.registerConnectionStateListener(listener);
        }
    }

    public void unregisterConnectionStateListener(IConnectionStateListener listener) {
        boolean isKnownListener = listeners.remove(listener);
        if(dataModelService != null && isKnownListener) {
            dataModelService.unregisterConnectionStateListener(listener);
        }
    }
}
