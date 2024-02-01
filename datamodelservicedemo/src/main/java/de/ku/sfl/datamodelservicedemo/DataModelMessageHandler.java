package de.ku.sfl.datamodelservicedemo;

import java.util.List;
import java.util.Vector;

import de.ku.sfl.connection.api.IMessageReceiver;
import de.ku.sfl.connection.messages.NamedBooleanValue;
import de.ku.sfl.connection.messages.NamedBooleanValueChangedMessage;
import de.ku.sfl.connection.messages.NamedIntegerValue;
import de.ku.sfl.connection.messages.NamedIntegerValueChangedMessage;
import de.ku.sfl.connection.messages.RebuildDatabaseMessage;
import de.ku.sfl.connection.messages.Report;
import de.ku.sfl.connection.messages.ReportVariant;
import de.ku.sfl.connection.messages.ReportDiscoveredMessage;
import de.ku.sfl.connection.objects.ObjectConverter;

/**
 * This class implements a convenience message handler that converts the received messages to concrete objects.
 */
public class DataModelMessageHandler implements IMessageReceiver {

    private final DemoDataModel dataModel;

    public DataModelMessageHandler(DemoDataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public void handleMessage(RebuildDatabaseMessage message) {
        dataModel.reinitialize(
                ObjectConverter.convertReports(message.getReportsList()),
                ObjectConverter.convertNamedBooleanValues(message.getNamedBooleanValuesList()),
                ObjectConverter.convertNamedIntegerValues(message.getNamedIntegerValuesList()));
    }

    @Override
    public void handleMessage(ReportDiscoveredMessage message) {
        dataModel.reportDiscovered(message.getKey(), message.getDiscovered());
    }

    @Override
    public void handleMessage(NamedBooleanValueChangedMessage message) {
        dataModel.namedBooleanValueChanged(message.getName(), message.getValue());
    }

    @Override
    public void handleMessage(NamedIntegerValueChangedMessage message) {
        dataModel.namedIntegerValueChanged(message.getName(), message.getValue());
    }
}
