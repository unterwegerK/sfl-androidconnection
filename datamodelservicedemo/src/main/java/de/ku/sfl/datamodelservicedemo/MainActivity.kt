package de.ku.sfl.datamodelservicedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import de.ku.sfl.androidConnectedDataModel.DataModelService
import de.ku.sfl.androidConnectedDataModel.api.DataModelAnchor
import de.ku.sfl.androidConnectedDataModel.api.DataModelProvider

class MainActivity : AppCompatActivity() {

    private var dataModelService: DataModelService/*<DemoDataModel>*/? = null
    private val dataModelAnchor = DataModelAnchor(this, DemoDataModel::class.java)
    private val dataModelProvider = DataModelProvider<DemoDataModel>(this, DemoDataModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataModelAnchor.ownerCreated()

        val nextButton = findViewById<Button>(R.id.button)
        nextButton.setOnClickListener (View.OnClickListener { view ->
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        })
        Log.d("ServiceDemo", "MainActivity.onCreate")
    }

    override fun onDestroy() {
        dataModelAnchor.ownerDestroyed()
        super.onDestroy()
        Log.d("ServiceDemo", "MainActivity.onDestroy")
    }

    override fun onStart() {
        super.onStart()

        dataModelProvider.onStarted()

        Log.d("ServiceDemo", "MainActivity.onStart")
    }

    override fun onStop() {
        dataModelProvider.onStopped()
        super.onStop()
        Log.d("ServiceDemo", "MainActivity.onStop")
    }
}