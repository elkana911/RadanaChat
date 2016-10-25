package id.co.ppu.radanachat.rest;

import android.util.Base64;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import id.co.ppu.radanachat.util.Utility;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eric on 19-Aug-16.
 */
public class ServiceGenerator {
    public static final String SERVER_USERNAME = "admin";
    public static final String SERVER_PWD = "4dminMobil3";

//    public static final String API_BASE_URL = "http://3r1c-e3e6e83ce9:8090/"; ga bisa ga tau knp
//    public static final String API_BASE_URL = "http://192.168.0.5:8090/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass, HttpUrl host) {
        return createService(serviceClass, host, SERVER_USERNAME, SERVER_PWD);
    }

    public static <S> S createService(Class<S> serviceClass, HttpUrl host, String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    Response resp = chain.proceed(request);

//                    String rawJson = resp.body().string(); //dont delete this, able to see the raw. once you copy the value, quickly disable again to avoid error like "closed"
//                    Log.e("rawJson", rawJson);

                    return resp;
                    /*
                    return resp.newBuilder()
                            .body(ResponseBody.create(resp.body().contentType(), rawJson)).build();
                            */

                }
            });
        }

        OkHttpClient client = httpClient.connectTimeout(Utility.NETWORK_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .readTimeout(Utility.NETWORK_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .build();

        /*
        date pattern tolong disamakan dgn yg di server
         */
        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy HH:mm:ss")   //"yyyy-MM-dd'T'HH:mm:ssZ"
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")   //"yyyy-MM-dd'T'HH:mm:ssZ"
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        final Expose expose = f.getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        final Expose expose = f.getAnnotation(Expose.class);
                        return expose != null && !expose.serialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                /*
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return json == null ? null : new Date(json.getAsLong());
                    }
                })*/
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                        return src == null ? null : new JsonPrimitive(src.getTime());
                    }
                })
                .create();

        /*
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .port()
                .build();
        */
        String fixUrl = host.toString();

        if(fixUrl.charAt(fixUrl.length()-1)!= '/'){
            fixUrl += '/';
        }

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(fixUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(new ToStringConverterFactory())
//                    .addConverterFactory(ScalarsConverterFactory.create());
                ;

        Retrofit retrofit = builder.client(client)
                .build();
        return retrofit.create(serviceClass);
    }
}
