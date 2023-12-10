package location

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.view.View
import androidx.core.app.ActivityCompat
import com.eathemeat.mocklocation.R
import com.google.android.material.snackbar.Snackbar


/**
 * author:PeterX
 * time:2023/12/6 0006
 */
const val TAG = "MockLocationMgr"
class MockLocationMgr {

    lateinit var sysLocationMgr:LocationManager
    lateinit var mAppCtx:Application


    constructor(appCtx:Application) {
        sysLocationMgr = appCtx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (checkPermission(appCtx)) {
            sysLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            requestLocationPermissions();
        }
    }

    fun checkPermission(ctx:Context): Boolean {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


    /**
     * 请求位置权限
     */
    private fun requestLocationPermissions(activity:Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            || ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Snackbar.make(
                layoutLocation, R.string.app_location_permission_demonstrate_access,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.app_ok, View.OnClickListener {
                    ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_LOCATION, REQUEST_LOCATION
                    )
                }).show()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_LOCATION, REQUEST_LOCATION
            )
        }
    }

}