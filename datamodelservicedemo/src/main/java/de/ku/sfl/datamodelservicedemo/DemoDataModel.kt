package de.ku.sfl.datamodelservicedemo

import android.content.Context
import de.ku.sfl.androidConnectedDataModel.api.IConnectedDataModel
import de.ku.sfl.connection.api.IMessageReceiver
import de.ku.sfl.connection.api.IMessageSender
import de.ku.sfl.connection.objects.NamedBooleanValue
import de.ku.sfl.connection.objects.NamedIntegerValue
import de.ku.sfl.connection.objects.Report


class DemoDataModel(val context: Context) : IConnectedDataModel {

    fun reportDiscovered(reportKey: String, discovered: Boolean) {
        TODO("Not yet implemented")
    }

    fun namedBooleanValueChanged(name: String?, value: Boolean) {
        TODO("Not yet implemented")
    }

    fun namedIntegerValueChanged(name: String?, value: Int) {
        TODO("Not yet implemented")
    }

    fun reinitialize(convertReports: MutableList<Report>?, convertNamedBooleanValues: MutableList<NamedBooleanValue>?, convertNamedIntegerValues: MutableList<NamedIntegerValue>?) {
        TODO("Not yet implemented")
    }

    override fun getMessageReceiver(): IMessageReceiver {
        return DataModelMessageHandler(this)
    }

    override fun setMessageSender(messageSender: IMessageSender?) {
        TODO("Not yet implemented")
    }
}