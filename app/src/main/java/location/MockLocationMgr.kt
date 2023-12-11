package location

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.location.provider.ProviderProperties
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.eathemeat.mocklocation.R
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.Method


/**
 * author:PeterX
 * time:2023/12/6 0006
 */
const val TAG = "MockLocationMgr"
class MockLocationMgr {

    lateinit var sysLocationMgr:LocationManager
    lateinit var mAcitivity:Activity
    lateinit var test1:MockLocationTest1
    lateinit var test2: MockLocationTest1

    constructor(act:Activity){
        mAcitivity = act
        sysLocationMgr = mAcitivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        test1 = MockLocationTest1(mAcitivity,sysLocationMgr)
    }


}