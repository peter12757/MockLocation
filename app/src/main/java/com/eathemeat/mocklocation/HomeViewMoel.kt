package com.eathemeat.mocklocation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import location.LocationInfo
import location.MockLocationMgr

/**
 * author:PeterX
 * time:2023/11/28 0028
 */
class HomeViewMoel : ViewModel() {

    private val _addrState = MutableStateFlow(LocationInfo())
    val addrState:StateFlow<LocationInfo> =_addrState.asStateFlow()

    var locationMgr: MockLocationMgr = MockLocationMgr()





}