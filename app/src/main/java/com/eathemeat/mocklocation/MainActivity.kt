package com.eathemeat.mocklocation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import com.eathemeat.mocklocation.ui.theme.MockLocationTheme


const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {

    lateinit var mMapView: MapView
    lateinit var mapApi:AMap

    var launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it->{
        it.entries.forEach{entry->
            Log.d(TAG, "权限:${entry.key} ->申请结果: ${entry.value}" )
        }
    }}

    /**
     * 位置许可
     */
    private val PERMISSIONS_LOCATION:Array<String> = arrayOf<String>(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcher.launch(PERMISSIONS_LOCATION)
        setContent {
            MockLocationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
        mMapView = findViewById(R.id.Amapview)
        mMapView.onCreate(savedInstanceState)
    }


    fun locationMySelf(){
        val myLocationStyle = MyLocationStyle().apply {
            interval(2000)
        }
        mapApi.setMyLocationStyle(myLocationStyle) //设置定位蓝点的Style
        mapApi.setMyLocationEnabled(true) // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(honmeviewmodel:HomeViewMoel = viewModel(), modifier: Modifier = Modifier) {
    Log.d(TAG, "Greeting: ${modifier}")
    Column {
        TextField(
            value = "asd",
            onValueChange = {
                Log.d(TAG, "onValueChange() called")
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface
            ),
            placeholder = {
                Text(text = stringResource(id = R.string.search))
                          },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
        )
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                MapView(it).apply {
                    id = R.id.Amapview
                }
            },
            update = {
                Log.d(TAG, "MapView:  update")
            }
        )

    }
    return
}


@Preview(showBackground = true )
@Composable
fun GreetingPreview() {
    MockLocationTheme {
        Greeting()
    }
}