package id.co.ppu.radanachat.pojo.chats;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eric on 25-Oct-16.
 * This table should one-on-one with mc_mst_user
 */

public class TrnChatContact extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("uid")
    private String uid;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("collCode")
    private String collCode;

    @SerializedName("photoUrl")
    private String photoUrl;

    /**
     * BOT, HUMAN
     */
    @SerializedName("contactType")
    private String contactType;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCollCode() {
        return collCode;
    }

    public void setCollCode(String collCode) {
        this.collCode = collCode;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @Override
    public String toString() {
        return "TrnChatContact{" +
                "uid='" + uid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", collCode='" + collCode + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", contactType='" + contactType + '\'' +
                '}';
    }
}
