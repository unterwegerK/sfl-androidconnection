package de.ku.sfl.androidConnectedDataModel;

import android.content.Context;
import android.os.Handler;

import java.io.DataInputStream;
import java.io.IOException;

import de.ku.sfl.connection.api.ILog;
import de.ku.sfl.connection.api.IMessageReceiver;
import de.ku.sfl.connection.messages.MessageDescription;
import de.ku.sfl.connection.messages.NamedBooleanValueChangedMessage;
import de.ku.sfl.connection.messages.NamedIntegerValueChangedMessage;
import de.ku.sfl.connection.messages.RebuildDatabaseMessage;
import de.ku.sfl.connection.messages.ReportDiscoveredMessage;

public class MessageDispatcher {
    private final static String TAG = MessageDispatcher.class.getCanonicalName();

    private final IMessageReceiver messageHandler;
    private final ILog log;
    private final Handler mainHandler;

    public MessageDispatcher(Context context, IMessageReceiver messageHandler, ILog log) {
        mainHandler = new Handler(context.getMainLooper());
        this.messageHandler = messageHandler;
        this.log = log;
    }

    public void dispatchMessage(DataInputStream inputStream) throws IOException {
        MessageDescription messageDescription = MessageDescription.parseFrom(inputStream);

        switch(messageDescription.getMessageType()) {

            case RebuildDatabase:
                RebuildDatabaseMessage rebuildDatabaseMessage = RebuildDatabaseMessage.parseFrom(inputStream);
                mainHandler.post(() -> messageHandler.handleMessage(rebuildDatabaseMessage));
                break;
            case ReportDiscovered:
                ReportDiscoveredMessage reportDiscoveredMessage = ReportDiscoveredMessage.parseFrom(inputStream);
                mainHandler.post(() -> messageHandler.handleMessage(reportDiscoveredMessage));
                break;
            case NamedBooleanValueChanged:
                NamedBooleanValueChangedMessage namedBooleanValueChangedMessage = NamedBooleanValueChangedMessage.parseFrom(inputStream);
                mainHandler.post(() -> messageHandler.handleMessage(namedBooleanValueChangedMessage));
                break;
            case NamedIntegerValueChanged:
                NamedIntegerValueChangedMessage namedIntegerValueChangedMessage = NamedIntegerValueChangedMessage.parseFrom(inputStream);
                mainHandler.post(() -> messageHandler.handleMessage(namedIntegerValueChangedMessage));
                break;
            default:
                log.error(TAG, "Received unknown message type: " + messageDescription.getMessageType().name());
        }
    }
}
