package id.co.ppu.radanachat.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import id.co.ppu.radanachat.pojo.chats.TrnChatContact;

/**
 * Created by Eric on 25-Oct-16.
 */

public class ResponseChatContacts extends ResponseBasic implements Serializable{
    @SerializedName("data")
    private List<TrnChatContact> data;

    public List<TrnChatContact> getData() {
        return data;
    }

    public void setData(List<TrnChatContact> data) {
        this.data = data;
    }
}
