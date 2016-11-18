package id.co.ppu.radanachat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Eric on 16-Aug-16.
 */
public class Storage {
    public static final String PREF_APP = "RealmPref";

    public static final String KEY_SERVER_ID = "serverID";
//    public static final String KEY_SERVER_DATE = "server.date";
    public static final String KEY_USER = "user";
    public static final String KEY_USER_LAST_DAY = "user.lastMorning";
//    public static final String KEY_USER_NAME_LAST = "lastUsername";
    public static final String KEY_PASSWORD_REMEMBER = "password.remember";
    public static final String KEY_PASSWORD_LAST = "password.last";

    public static final String KEY_LOGOUT_DATE = "logout.date";

    /*
    public static String getPrefAsString(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    public static void setPrefAsString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
*/
    public static void savePreference(Context ctx, String key, String value) {
        SharedPreferences objPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode
        SharedPreferences.Editor prefsEditor = objPrefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply(); //asynkron
    }

    public static void savePreferenceAsInt(Context ctx, String key, int value) {
        SharedPreferences objPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode
        SharedPreferences.Editor prefsEditor = objPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply(); //asynkron
    }

    public static void savePreferenceAsBoolean(Context ctx, String key, boolean value) {
        SharedPreferences objPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode
        SharedPreferences.Editor prefsEditor = objPrefs.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply(); //asynkron
    }

    public static void saveObjPreference(Context ctx, String key, Object value) {

        if (value == null) return;

        SharedPreferences objPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode
        SharedPreferences.Editor prefsEditor = objPrefs.edit();

        String json = new Gson().toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.commit();   //synkron
    }

    public static Object getObjPreference(Context ctx, String key, Class cls) {
        String val = null;

        try {
            //Get Reg Token on shared pref
            SharedPreferences userPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode

            Gson gson = new Gson();
            String json = userPrefs.getString(key, "");

            return new Gson().fromJson(json, cls);

        } catch (Exception e) {
            return null;
        }
    }

    public static String getPreference(Context ctx, String key) {
        String val = null;

        try {
            //Get Reg Token on shared pref
            SharedPreferences userPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode
            val = userPrefs.getString(key, "");
        } catch (Exception e) {
            return null;
        }
        return val;
    }

    public static int getPreferenceAsInt(Context ctx, String key, int defaultValue) {
        int val;

        try {
            //Get Reg Token on shared pref
            SharedPreferences userPrefs = ctx.getSharedPreferences(PREF_APP, 0); // 0 - for private mode
            val = userPrefs.getInt(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
        return val;
    }

    public static void clearObjOnSharedPref(Context ctx, String ObjPref) {
        SharedPreferences objPrefs = ctx.getSharedPreferences(ObjPref, 0); // 0 - for private mode
        SharedPreferences.Editor prefsEditor = objPrefs.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

    public static File getCompressedImage(Context context, File rawFile, String photoId) throws IOException {
        InputStream in = new FileInputStream(rawFile);
        Bitmap bm2 = BitmapFactory.decodeStream(in);

        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile(photoId + "-", ".jpg", outputDir);

        OutputStream stream = new FileOutputStream(outputFile);
        bm2.compress(Bitmap.CompressFormat.JPEG, 10, stream);
//        bm2.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        stream.close();
        in.close();

        return outputFile;
    }
}
