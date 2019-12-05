package es.icp.icp_commons.Interfaces;

import es.icp.icp_commons.Objects.CheckRequestException;

@SuppressWarnings("unused")
public interface EnvioAccionesCallback {
    void onSuccess() throws CheckRequestException;
    void onFinish() throws CheckRequestException;
    void onOffline();
}
