package id.co.ppu.radanachat.pojo.chats;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eric on 25-Oct-16.
 */

public class TrnChatLog extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("uid")
    private String uid;

    @SerializedName("fromColl")
    private String fromColl;

    @SerializedName("toColl")
    private String toColl;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    @SerializedName("createdDate")
    private Date createdDate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFromColl() {
        return fromColl;
    }

    public void setFromColl(String fromColl) {
        this.fromColl = fromColl;
    }

    public String getToColl() {
        return toColl;
    }

    public void setToColl(String toColl) {
        this.toColl = toColl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TrnChatLog{" +
                "uid='" + uid + '\'' +
                ", fromColl='" + fromColl + '\'' +
                ", toColl='" + toColl + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
