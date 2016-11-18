package id.co.ppu.radanachat.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Eric on 18-Nov-16.
 */

public class NetUtil {
    public static boolean isConnected(Context ctx) {
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (connec.getActiveNetworkInfo() != null)
                && (connec.getActiveNetworkInfo().isAvailable())
                && (connec.getActiveNetworkInfo().isConnected());
    }

}
