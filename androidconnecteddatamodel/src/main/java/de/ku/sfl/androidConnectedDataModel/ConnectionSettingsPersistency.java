package de.ku.sfl.androidConnectedDataModel;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.InetSocketAddress;

public class ConnectionSettingsPersistency {

    public static final String PREFERENCES_KEY = "ConnectionSettings";
    public static final String SERVER_ADDRESS_KEY = "ServerAddress";
    public static final String SERVER_PORT_KEY = "ServerPort";
    public static final String DEVICE_NAME_KEY = "DeviceName";
    private final SharedPreferences preferences;

    public ConnectionSettingsPersistency(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void setServerAddress(InetSocketAddress serverAddress) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SERVER_ADDRESS_KEY, serverAddress.getHostName());
        editor.putInt(SERVER_PORT_KEY, serverAddress.getPort());
        editor.commit();
    }

    public InetSocketAddress getServerAddress() {
        String host = preferences.getString(SERVER_ADDRESS_KEY, "");
        int port = preferences.getInt(SERVER_PORT_KEY, 0);
        return new InetSocketAddress(host, port);
    }

    public void setDeviceName(String deviceName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DEVICE_NAME_KEY, deviceName);
        editor.commit();
    }

    public String getDeviceName(){
        return preferences.getString(DEVICE_NAME_KEY, "");
    }
}
