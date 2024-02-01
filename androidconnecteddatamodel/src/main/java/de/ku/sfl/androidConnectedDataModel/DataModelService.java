package de.ku.sfl.androidConnectedDataModel;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Set;

import de.ku.sfl.androidConnectedDataModel.api.IConnectedDataModel;
import de.ku.sfl.androidConnectedDataModel.api.IConnectionSettings;
import de.ku.sfl.connection.Connection;
import de.ku.sfl.connection.api.IConnectionStateListener;
import de.ku.sfl.connection.api.ILog;

public class DataModelService extends Service implements IConnectionSettings {
    public static final String DATA_MODEL_TYPE_NAME_ID = "TypeName";

    private final DataModelServiceBinder binder;
    private IConnectedDataModel dataModel;

    private Connection connection;

    private ConnectionSettingsPersistency settings;

    public DataModelService() {
        binder = new DataModelServiceBinder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "onCreate");

        settings = new ConnectionSettingsPersistency(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceDemo", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "onBind");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d("ServiceDemo", "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ServiceDemo", "onStartCommand");

        String dataModelTypeName = intent.getStringExtra(DATA_MODEL_TYPE_NAME_ID);
        if(dataModelTypeName != null) {
            try {
                Class dataModelType = Class.forName(dataModelTypeName);
                dataModel = (IConnectedDataModel) dataModelType.getConstructor(Context.class).newInstance(new Object[]{getApplicationContext()});

                ILog log = new LogCatLog();
                connection = new Connection(new MessageDispatcher(getApplicationContext(), dataModel.getMessageReceiver(), log), log);
                dataModel.setMessageSender(new MessageSender(connection, log));
                connection.setDeviceName(settings.getDeviceName());
                connection.setServerAddress(settings.getServerAddress());
                connection.connectToServer();
            } catch (ClassNotFoundException e) {
                Log.e("Connected data model", "Data-model type " + dataModelTypeName + " not found.");
            } catch (IllegalAccessException e) {
                Log.e("Connected data model", "Problem when instantiating type " + dataModelTypeName + ": " + e.toString());
            } catch (InstantiationException e) {
                Log.e("Connected data model", "Problem when instantiating type " + dataModelTypeName + ": " + e.toString());
            } catch (InvocationTargetException e) {
                Log.e("Connected data model", "Type " + dataModelTypeName + " does not provide a constructor taking a Context parameter.");
            } catch (NoSuchMethodException e) {
                Log.e("Connected data model", "Type " + dataModelTypeName + " does not provide a constructor taking a Context parameter.");
            }
        }
        else
        {
            Log.e("Connected data model", "No data-model type specified. Please add String Extra in intent with name " + DATA_MODEL_TYPE_NAME_ID);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void setConnection(InetSocketAddress serverAddress) {
        connection.setServerAddress(serverAddress);
    }

    public Object getDataModel() {
        return dataModel;
    }

    public void registerConnectionStateListeners(Set<IConnectionStateListener> listeners) {
        if(connection != null) {
            connection.registerConnectionStateListeners(listeners);
        }
    }

    public void registerConnectionStateListener(IConnectionStateListener listener) {
        if(connection != null) {
            connection.registerConnectionStateListener(listener);
        }
    }

    public void unregisterConnectionStateListeners(Set<IConnectionStateListener> listeners) {
        if(connection != null) {
            connection.unregisterConnectionStateListeners(listeners);
        }
    }

    public void unregisterConnectionStateListener(IConnectionStateListener listener) {
        if(connection != null) {
            connection.unregisterConnectionStateListener(listener);
        }
    }

    @Override
    public String getDeviceName() {
        return settings.getDeviceName();
    }

    @Override
    public InetSocketAddress getServerAddress() {
        return settings.getServerAddress();
    }

    @Override
    public void setSettings(String deviceName, InetSocketAddress serverAddress) {
        if(deviceName != settings.getDeviceName()
            || serverAddress.getHostName() != settings.getServerAddress().getHostName()
            || serverAddress.getPort() != settings.getServerAddress().getPort()) {
            settings.setDeviceName(deviceName);
            settings.setServerAddress(serverAddress);

            connection.disconnectFromServer();
            connection.setDeviceName(deviceName);
            connection.setServerAddress(serverAddress);
            connection.connectToServer();
        }
    }
}