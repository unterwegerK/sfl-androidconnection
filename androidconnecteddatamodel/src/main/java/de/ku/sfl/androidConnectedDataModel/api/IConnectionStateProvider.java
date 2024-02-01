package de.ku.sfl.androidConnectedDataModel.api;

import de.ku.sfl.connection.api.IConnectionStateListener;

public interface IConnectionStateProvider {

    public void registerConnectionStateListener(IConnectionStateListener listener);

    public void unregisterConnectionStateListener(IConnectionStateListener listener);

    public boolean isConnected();
}
