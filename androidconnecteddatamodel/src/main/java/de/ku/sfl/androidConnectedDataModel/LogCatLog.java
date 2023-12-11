package de.ku.sfl.androidConnectedDataModel;

import android.util.Log;

import de.ku.sfl.connection.ILog;

public class LogCatLog implements ILog {
    @Override
    public void trace(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void warning(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void error(String tag, String message, Exception exception) {
        Log.e(tag, message, exception);
    }
}
