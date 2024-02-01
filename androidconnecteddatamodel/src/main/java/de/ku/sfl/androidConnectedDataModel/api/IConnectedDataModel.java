package de.ku.sfl.androidConnectedDataModel.api;

import de.ku.sfl.connection.api.IMessageReceiver;
import de.ku.sfl.connection.api.IMessageSender;

public interface IConnectedDataModel {
    IMessageReceiver getMessageReceiver();

    void setMessageSender(IMessageSender messageSender);
}
