package id.co.ppu.radanachat.rest.request;

import java.util.Date;

/**
 * Created by Eric on 18-Nov-16.
 */

public class RequestChatStatus {
    private Long status; //-1 UNAVAILABLE, 0 - OFFLINE, 1 - ONLINE, 2 - INVISIBLE

    private Date date;

    private String message;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestChatStatus{" +
                "status=" + status +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
