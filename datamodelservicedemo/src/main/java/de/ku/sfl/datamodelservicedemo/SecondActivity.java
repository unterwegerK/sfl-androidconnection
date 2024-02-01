package de.ku.sfl.datamodelservicedemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import de.ku.sfl.androidConnectedDataModel.DataModelService;
import de.ku.sfl.androidConnectedDataModel.api.DataModelProvider;
import de.ku.sfl.androidConnectedDataModel.api.IDataModelProviderListener;

public class SecondActivity extends AppCompatActivity implements IDataModelProviderListener {

    private DataModelService dataModelService;
    private final DataModelProvider<DemoDataModel> dataModelProvider = new DataModelProvider<>(this, this, DemoDataModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("ServiceDemo", "SecondActivity.onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();

        dataModelProvider.onStarted();

        Log.d("ServiceDemo", "SecondActivity.onStart");
    }

    @Override
    public void onStop() {
        dataModelProvider.onStopped();
        super.onStop();
        Log.d("ServiceDemo", "SecondActivity.onStop");
    }

    @Override
    public void onDataModelAvailable() {

    }

    @Override
    public void onDataModelUnavailable() {

    }
}