package id.co.ppu.radanachat.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import id.co.ppu.radanachat.R;
import okhttp3.HttpUrl;

public class Utility {

    public final static String DATE_EXPIRED_YYYYMMDD = "20161104";
    public final static String[][] servers = {
//            {"local-server", "10.212.4.214", "8090"},
            {"local-server", "192.168.1.107", "8090"},
            {"fast-mobile", "cmobile.radanafinance.co.id", "7001"}
    };
    public final static boolean developerMode = true;

    public final static int NETWORK_TIMEOUT_MINUTES = 4;

//    public final static String[][] servers = {{"local-server", "10.100.100.77", "8090"}
//            ,{"fast-mobile", "cmobile.radanafinance.co.id", "7001"}
//    };
//
    public final static String DATE_DISPLAY_PATTERN = "dd MMM yyyy";
    public final static String DATE_DATA_PATTERN = "yyyyMMdd";

    public final static String COLUMN_CREATED_BY = "createdBy";

    public final static String LAST_UPDATE_BY = "MOBCOL";
    public final static String INFO = "Info";
    public final static String WARNING = "Warning";

    public static final int MAX_MONEY_DIGITS = 10;
    public static final int MAX_MONEY_LIMIT = 99999999;

    public static String getServerName(int serverId) {
        String[] s = servers[serverId];
        return s[0];
    }
    public static int getServerID(String serverName) {
        for (int i = 0; i < servers.length; i++) {
            if (servers[i][0].equalsIgnoreCase(serverName)) {
                return i;
            }
        }
        return -1;
    }

    public static HttpUrl buildUrl(int serverChoice) {

        if (serverChoice < 0)
            serverChoice = 0;
        if (serverChoice > servers.length-1)
            serverChoice = servers.length-1;

        String[] server = servers[serverChoice];   // just change this

        HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("http")
                .host(server[1])
                .port(Integer.parseInt(server[2]))
                ;

        String root = server[0];

        if (!root.toLowerCase().startsWith("local")) {
            url.addPathSegment(root);
        }

        return url.build();
    }

    public static void disableScreen(Activity act, boolean disable) {
        if (disable) {
            act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
    public static void showDialog(Context ctx, String title, String msg){

//        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AppTheme_Teal_Dialog))
        new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AppTheme))
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public static void showSettingsDialog(Context mContext) {
        final Context ctx = mContext;

//        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AppTheme_Teal_Dialog))
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AppTheme))
                .setTitle(INFO)
                .setMessage("GPS is not enabled. Do you want to go to settings menu ?")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        ctx.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

/*
    public static ArrayList<Order> getOrder(Context ctx){
        ArrayList<Order> orderData = null;

        try {
            //Get Order on shared pref
            SharedPreferences orderPrefs = ctx.getSharedPreferences(Utility.ORDERPREF, 0); // 0 - for private mode
            String sOrderId = orderPrefs.getString(Utility.ORDEROBJ, "");

            Gson gson = new GsonBuilder().create();
            GSONOrderList gsonRes = gson.fromJson(sOrderId, GSONOrderList.class);
            orderData = gsonRes.getData();
        }
        catch (Exception e){
            return null;
        }
        return orderData;
    }

    public static User getUser(Context ctx){
        User userData = null;

        try {
            //Get User on shared pref
            SharedPreferences userPrefs = ctx.getSharedPreferences(Utility.USERPREF, 0); // 0 - for private mode
            String sUser = userPrefs.getString(Utility.USEROBJ, "");

            Gson gson = new GsonBuilder().create();
            ResponseLogin gsonRes = gson.fromJson(sUser, ResponseLogin.class);
            userData = gsonRes.getData();
        }
        catch (Exception e){
            return null;
        }
        return userData;
    }
*/
    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void setViewGroupEnebled(ViewGroup view, boolean enabled)
    {
        int childern = view.getChildCount();

        for (int i = 0; i< childern ; i++)
        {
            View child = view.getChildAt(i);
            if (child instanceof ViewGroup)
            {
                setViewGroupEnebled((ViewGroup) child,enabled);
            }
            child.setEnabled(enabled);
        }
        view.setEnabled(enabled);
    }
    public static void setViewGroupFocusable(ViewGroup view, boolean enabled)
    {
        int childern = view.getChildCount();

        for (int i = 0; i< childern ; i++)
        {
            View child = view.getChildAt(i);
            if (child instanceof ViewGroup)
            {
                setViewGroupFocusable((ViewGroup) child,enabled);
            }
            child.setFocusable(enabled);
        }
        view.setFocusable(enabled);
    }

    public static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static String leftPad(int n, int padding) {
        return String.format("%0" + padding + "d", n);
    }

    public static String leftPad(long n, int padding) {
        return String.format("%0" + padding + "d", n);
    }

    public static String convertDateToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date convertStringToDate(String date, String pattern) {
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
        try {
            return sdf1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getYesterday(Date startFrom) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startFrom);
        cal.add(Calendar.DAY_OF_YEAR, -1); // <--
        return cal.getTime();
    }

    public static Date getTwoDaysAgo(Date startFrom) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startFrom);
        cal.add(Calendar.DAY_OF_YEAR, -2); // <--
        return cal.getTime();
    }

    /**
     * Usually used for sync location.
     * @param time between 8 AM to 5 PM
     * @return
     */
    public static boolean isWorkingHours(Date time) {
        Calendar cal = Calendar.getInstance(); //Create Calendar-Object
        cal.setTime(time);               //Set the Calendar to now
        int hour = cal.get(Calendar.HOUR_OF_DAY); //Get the hour from the calendar
        return hour <= 17 && hour >= 8;

    }

    public static boolean isWorkingHours() {
        return isWorkingHours(new Date());
    }

    public static String convertLongToRupiah(long amount) {
//        double harga = 250000000;

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setMinimumFractionDigits(0);  // sen minta diilangin

        kursIndonesia.setDecimalFormatSymbols(formatRp);
//        System.out.printf("Harga Rupiah: %s %n", kursIndonesia.format(amount));    //Harga Rupiah: Rp. 250.000.000,00

        return kursIndonesia.format(amount);
    }


    public static boolean isSameDay(Date date1, Date date2) {
        // sementara sengaja always true, will be controlled on production
        // will reset data on next day
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isNumeric(String str)
    {
        if (str == null)
            return false;

        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static boolean isValidMoney(String value) {
        if (TextUtils.isEmpty(value))
            return false;

        if (value.length() > 1 && value.startsWith("0"))
            return false;

        if (!isNumeric(value))
            return false;

        return Long.parseLong(value) >= 0;

    }

    public static void setSpinnerAsString(Spinner spinner, String value) {
        for(int i = 0; i < spinner.getAdapter().getCount(); i++) {
            if(value.equals(spinner.getItemAtPosition(i).toString())){
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static String generateUUID(){
        return java.util.UUID.randomUUID().toString();
    }

    //http://stackoverflow.com/questions/16078269/android-unique-serial-number/16929647#16929647
    public static String getDeviceId(Context context) {
        final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null) {
            return deviceId;
        } else {
            return android.os.Build.SERIAL;
        }
    }

    public static long longValue(Long value) {
        if (value == null)
            return 0;
        else
            return value.longValue();
    }
/*
    public static String getAndroidID(Context ctx){
        String _id = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
        return _id;
    }

    public static String getDeviceID(Context ctx){
        TelephonyManager tManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String _id = tManager.getDeviceId();
        return _id;
    }
*/

}
