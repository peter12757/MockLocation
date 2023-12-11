package location

/**
 * author:PeterX
 * time:2023/12/11 0011
 */
interface MockLocApi {

    fun enableMockLocation(info:LocationInfo): Unit

    fun disableMockLocation(info:LocationInfo):Unit

}

