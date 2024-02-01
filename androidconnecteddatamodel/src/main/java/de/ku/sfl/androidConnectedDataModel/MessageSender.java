package de.ku.sfl.androidConnectedDataModel;

import org.json.JSONException;

import java.io.IOException;

import de.ku.sfl.connection.Connection;
import de.ku.sfl.connection.api.ILog;
import de.ku.sfl.connection.api.IMessageSender;
import de.ku.sfl.connection.messages.NamedBooleanValue;
import de.ku.sfl.connection.messages.NamedIntegerValueChangedMessage;
import de.ku.sfl.connection.messages.ReportDiscoveredMessage;

public class MessageSender implements IMessageSender {

    private static final String TAG = MessageSender.class.getCanonicalName();

    private final Connection connection;
    private final ILog log;

    public MessageSender(Connection connection, ILog log) {
        this.connection = connection;
        this.log = log;
    }

    @Override
    public void sendReportDiscoveredMessage(String reportKey, boolean discovered) {
        try {
            connection.sendMessage(ReportDiscoveredMessage.newBuilder().setKey(reportKey).setDiscovered(discovered).build().toByteArray(), true);
        } catch (JSONException e) {
            log.error(TAG, "Could not deliver ReportDiscoveredMessage.", e);
        } catch (IOException e) {
            log.error(TAG, "Could not deliver ReportDiscoveredMessage.", e);
        }
    }

    @Override
    public void sendNamedIntegerValueChangedMessage(String name, int value) {
        try {
            connection.sendMessage(NamedIntegerValueChangedMessage.newBuilder().setName(name).setValue(value).build().toByteArray(), true);
        } catch (JSONException e) {
            log.error(TAG, "Could not deliver NamedIntegerValueChangedMessage.", e);
        } catch (IOException e) {
            log.error(TAG, "Could not deliver NamedIntegerValueChangedMessage.", e);
        }
    }

    @Override
    public void sendNamedBooleanValueChangedMessage(String name, boolean value) {
        try {
            connection.sendMessage(NamedBooleanValue.newBuilder().setName(name).setValue(value).build().toByteArray(), true);
        } catch (JSONException e) {
            log.error(TAG, "Could not deliver NamedBooleanValue.", e);
        } catch (IOException e) {
            log.error(TAG, "Could not deliver NamedBooleanValue.", e);
        }
    }
}
