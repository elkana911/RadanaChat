package id.co.ppu.radanachat.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import id.co.ppu.radanachat.pojo.chats.TrnChatLog;

/**
 * Created by Eric on 25-Oct-16.
 */

public class ResponseChatHistory extends ResponseBasic implements Serializable {
    @SerializedName("data")
    private List<TrnChatLog> data;

    public List<TrnChatLog> getData() {
        return data;
    }

    public void setData(List<TrnChatLog> data) {
        this.data = data;
    }
}
