package id.co.ppu.radanachat.rest;

import id.co.ppu.radanachat.rest.request.RequestChatBetween;
import id.co.ppu.radanachat.rest.response.ResponseChatHistory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Eric on 19-Aug-16.
 */
public interface ApiInterface {
    @GET("fast/chat_hist")
    Call<ResponseChatHistory> getChatHistory(@Query("collCode") String collCode);

    @POST("fast/chat_between")
    Call<ResponseChatHistory> getChatBetween(@Body RequestChatBetween request);

//    @POST("fast/chat_last")
//    Call<ResponseChatLast> getChatLast(@Body RequestChatLast request);

    @GET("fast/chat_status")
    Call<ResponseBody> setChatHistory(@Query("uid") String uid);

    @GET("fast/chat_send")
    Call<ResponseBody> sendMessage(@Query("msg") String msg);
}
