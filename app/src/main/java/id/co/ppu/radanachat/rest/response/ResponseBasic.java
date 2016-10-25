package id.co.ppu.radanachat.rest.response;

import com.google.gson.annotations.SerializedName;

public class ResponseBasic {
    @SerializedName("error")
    private Error error;
    @SerializedName("ip")
    private String ip;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
