package com.eathemeat.mocklocation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * author:PeterX
 * time:2023/11/28 0028
 */
class HomeViewMoel : ViewModel() {

    private val _addrState = MutableStateFlow(Location())
    val addrState:StateFlow<Location> =_addrState.asStateFlow()





}