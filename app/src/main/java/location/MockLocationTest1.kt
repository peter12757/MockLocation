package location

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.location.Location
import android.location.LocationManager
import android.location.provider.ProviderProperties
import android.os.Build
import android.os.SystemClock
import android.util.Log
import java.lang.reflect.Method

/**
 * author:PeterX
 * time:2023/12/11 0011
 */
class MockLocationTest1 :MockLocApi{

    var isOpenMock = true
    val MockProviderName = LocationManager.GPS_PROVIDER

    lateinit var sysLocationMgr:LocationManager
    lateinit var mAcitivity: Activity

    constructor(act: Activity,loc: LocationManager){
        mAcitivity = act
        sysLocationMgr = loc
        writeMockLocation(mAcitivity.packageName)
    }

    /**
     * 需要系统权限才能正常反射调用
     * 设置模拟定位数据的APP
     * @param mockPackageName
     */
    fun writeMockLocation(packageName:String): Unit {
        try {
            var appsopsManager: AppOpsManager = mAcitivity.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            var appinfo: ApplicationInfo =  mAcitivity.applicationInfo
            var opsManagerClass = Class.forName("android.app.AppOpsManager")
            var setMode: Method = opsManagerClass.getMethod("setMode", Int::class.java,Int::class.java,String::class.java,Int::class.java)
            var params = arrayOf(58,appinfo.uid,packageName, AppOpsManager.MODE_ALLOWED)
            setMode.invoke(appsopsManager,params)
        } catch (e:Exception) {
            Log.e(TAG, "writeMockLocation: ", e)
        }
    }

    fun enableLocationMock(enable:Boolean): Unit {
        try {
            if (enable) {
                sysLocationMgr.addTestProvider(LocationManager.GPS_PROVIDER,"requiresNetwork" == "", "requiresSatellite" == "",
                    "requiresCell" == "", "hasMonetaryCost" == "",
                    "supportsAltitude" == "supportsAltitude",
                    "supportsSpeed" == "supportsSpeed",
                    "supportsBearing" == "supportsBearing",
                    ProviderProperties.POWER_USAGE_HIGH, ProviderProperties.ACCURACY_FINE)
                sysLocationMgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER,true)
            }else {
                sysLocationMgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER,false)
                sysLocationMgr.clearTestProviderEnabled(LocationManager.GPS_PROVIDER)   //过期
                sysLocationMgr.removeTestProvider(LocationManager.GPS_PROVIDER)
            }
        }catch (e:Exception) {
            Log.e(TAG, "enableLocationMock: ", e)
        }
    }

    private fun mockLocation(
        location: Boolean,
        latitude: Double,
        longitude: Double,
        speed: Float,
        bearing: Float,
        starCount: Double,
        altitude: Double
    ) {
        val loc: Location = Location(LocationManager.GPS_PROVIDER) // 这里是模拟的gps位置信息，当然也可以设置network位置信息了。

        loc.time = System.currentTimeMillis()
        loc.latitude = latitude
        loc.longitude = longitude
        loc.speed = (speed / 3.6).toFloat()
        loc.bearing = bearing
        loc.altitude = altitude

        if (location) {
            loc.accuracy = 10F
        } else {
            loc.accuracy = 100F
        }

        loc.altitude = starCount
        loc.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loc.setBearingAccuracyDegrees(0.1f)
            loc.setVerticalAccuracyMeters(0.1f)
            loc.setSpeedAccuracyMetersPerSecond(0.01f)
        }
        if (isOpenMock && sysLocationMgr != null) {
            try {
                sysLocationMgr.setTestProviderLocation(MockProviderName, loc)
//                sysLocationMgr.setTestProviderStatus(
//                    MockProviderName,
//                    LocationProvider.AVAILABLE, null,
//                    System.currentTimeMillis()
//                )
            } catch (e: SecurityException) {
                isOpenMock = false
                e.printStackTrace()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun mocklocation(location:Boolean, latitude: Double, longitude:Double, speed:Float, bearing:Float, starCount:Double): Unit {
        mockLocation(location
            ,latitude / 1000000.0
            ,longitude / 1000000.0
            ,speed
            ,bearing
            ,starCount
            , 0.0
        );
    }

    override fun enableMockLocation(info: LocationInfo) {
        TODO("Not yet implemented")
    }

    override fun disableMockLocation(info: LocationInfo) {
        TODO("Not yet implemented")
    }
}