package id.co.ppu.radanachat.rest.request;

/**
 * Created by Eric on 25-Oct-16.
 */

public class RequestChatBetween {

    private String coll1;
    private String coll2;

    public String getColl1() {
        return coll1;
    }

    public void setColl1(String coll1) {
        this.coll1 = coll1;
    }

    public String getColl2() {
        return coll2;
    }

    public void setColl2(String coll2) {
        this.coll2 = coll2;
    }

    @Override
    public String toString() {
        return "RequestChatBetween{" +
                "coll1='" + coll1 + '\'' +
                ", coll2='" + coll2 + '\'' +
                '}';
    }
}
