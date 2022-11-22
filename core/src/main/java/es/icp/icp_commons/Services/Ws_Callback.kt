package es.icp.icp_commons.Services


interface Ws_Callback {
    fun online (response: DefaultResult)
    fun offline()
}