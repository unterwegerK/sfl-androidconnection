package de.ku.sfl.androidConnectedDataModel;

import java.util.HashSet;
import java.util.Set;

import de.ku.sfl.androidConnectedDataModel.api.IConnectionStateProvider;
import de.ku.sfl.connection.api.IConnectionStateListener;

public class ConnectionStateProvider implements IConnectionStateProvider {

    private final Set<IConnectionStateListener> listeners = new HashSet<>();
    private DataModelService dataModelService;

    @Override
    public void registerConnectionStateListener(IConnectionStateListener listener) {
        boolean isUnknownListener = listeners.add(listener);
        if(dataModelService != null && isUnknownListener) {
            dataModelService.registerConnectionStateListener(listener);
        }
    }

    @Override
    public void unregisterConnectionStateListener(IConnectionStateListener listener) {
        boolean isKnownListener = listeners.remove(listener);
        if(dataModelService != null && isKnownListener) {
            dataModelService.unregisterConnectionStateListener(listener);
        }
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    public void setDataModelService(DataModelService dataModelService) {
        if(this.dataModelService == dataModelService) {
            return;
        }

        if(this.dataModelService != null) {
            this.dataModelService.unregisterConnectionStateListeners(listeners);
        }

        this.dataModelService = dataModelService;

        if(dataModelService != null) {
            dataModelService.registerConnectionStateListeners(listeners);
        }
    }
}
