package de.ku.sfl.androidConnectedDataModel.api;

import java.net.InetSocketAddress;

public interface IConnectionSettings {

    public String getDeviceName();

    public InetSocketAddress getServerAddress();

    public void setSettings(String deviceName, InetSocketAddress serverAddress);
}
