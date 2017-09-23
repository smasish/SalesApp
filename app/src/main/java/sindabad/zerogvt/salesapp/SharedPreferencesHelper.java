/*
 * Copyright (C) 2010 Mathieu Favez - http://mfavez.com
 *
 *
 * This file is part of FeedGoal.
 * 
 * FeedGoal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeedGoal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FeedGoal.  If not, see <http://www.gnu.org/licenses/>.
 */

package sindabad.zerogvt.salesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public final class SharedPreferencesHelper {

    private static final String LOG_TAG = "SharedPreferencesHelper";

    // Dialogs Id
    public static final int DIALOG_ABOUT = 0;

    public static final int DIALOG_NO_CONNECTION = 1;

    public static final int DIALOG_UPDATE_PROGRESS = 2;

    // Menu Groups Id
    public static final int CHANNEL_SUBMENU_GROUP = 0;

    public static final String REMEMBER = "remember";

    // App Preferences
    private static final String PREFS_FILE_NAME = "AppPreferences";

    private static final String PREF_TAB_FEED_KEY = "tabFeed";

    private static final String USER = "user";
    private static final String NEWID = "0";

    private static final String PASS = "pass";
    private static final String PROD = "prod";
    
    private static final String CUST = "cust";
    private static final String MASTER = "mas";
    private static final String EmpReg = "empReg";
    private static final String CustVersion = "custversion";
    private static final String ProdVersion = "prodversion";
    
    private static final String DRAFT = "dr";
    private static final String SENT = "SE";



    public static long getPrefTabFeedId(final Context ctx, final long defaultTabFeedId) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getLong(SharedPreferencesHelper.PREF_TAB_FEED_KEY,
                defaultTabFeedId);
    }

    public static void setPrefTabFeedId(final Context ctx, final long feedId) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putLong(SharedPreferencesHelper.PREF_TAB_FEED_KEY, feedId);
        editor.commit();
    }

    public static boolean getRemember(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getBoolean(SharedPreferencesHelper.REMEMBER, false);
    }

    public static void setRemember(final Context ctx, final boolean flag) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putBoolean(SharedPreferencesHelper.REMEMBER, flag);
        editor.commit();
    }

    public static String getUser(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.USER, "");
    }

    public static void setUser(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.USER, user);
        editor.commit();
    }
    
    public static String getNewId(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.NEWID, "0");
    }

    public static void setNewId(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.NEWID, user);
        editor.commit();
    }
    
    public static String getMaster(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.MASTER, "");
    }

    public static void setMaster(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.MASTER, pass);
        editor.commit();
    }
    
    public static String getEmpReg(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.EmpReg, "");
    }

    public static void setEmpReg(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.EmpReg, pass);
        editor.commit();
    }
    
    public static String getCustVersion(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.CustVersion, "");
    }

    public static void setCustVersion(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.CustVersion, pass);
        editor.commit();
    }
    
    public static String getProdVersion(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.ProdVersion, "");
    }

    public static void setProdVersion(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.ProdVersion, pass);
        editor.commit();
    }
    
    public static String getDraft(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.DRAFT, "");
    }

    public static void setDraft(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.DRAFT, pass);
        editor.commit();
    }
    public static String getSent(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.SENT, "");
    }

    public static void setSent(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.SENT, pass);
        editor.commit();
    }

    public static String getProd(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.PROD, "");
    }

    public static void setProd(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.PROD, pass);
        editor.commit();
    }
    public static String getCust(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.CUST, "");
    }

    public static void setCust(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.CUST, pass);
        editor.commit();
    }
    
    public static String getPass(final Context ctx) {
        return ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME,
                Context.MODE_PRIVATE).getString(SharedPreferencesHelper.PASS, "");
    }

    public static void setPass(final Context ctx, final String pass) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(SharedPreferencesHelper.PASS, pass);
        editor.commit();
    }

    public static int getSplashDuration(final Context ctx) {
        int splashScreenDuration = 2000;
        try {
            final ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            splashScreenDuration = ai.metaData.getInt("splash_screen_duration");
        } catch (final NameNotFoundException nnfe) {
            Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
        }
        return splashScreenDuration;
    }

    public static String getFlurryAnalyticsApiKey(final Context ctx) {
        String flurryAnalyticsApiKey = null;
        try {
            final ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            flurryAnalyticsApiKey = ai.metaData.getString("FLURRY_API_KEY");
        } catch (final NameNotFoundException nnfe) {
            Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
        }
        return flurryAnalyticsApiKey;
    }

    public static String getGoogleAnalyticsProfileId(final Context ctx) {
        String googleAnalyticsProfileId = null;
        try {
            final ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            googleAnalyticsProfileId = ai.metaData.getString("GOOGLE_ANALYTICS_PROFILE_ID");
        } catch (final NameNotFoundException nnfe) {
            Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
        }
        return googleAnalyticsProfileId;
    }

    public static String getMobclixApplicationId(final Context ctx) {
        String mobclixApplicationId = null;
        try {
            final ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            mobclixApplicationId = ai.metaData.getString("com.mobclix.APPLICATION_ID");
        } catch (final NameNotFoundException nnfe) {
            Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
        }
        return mobclixApplicationId;
    }

    public static boolean trackFlurryAnalytics(final Context ctx) {
        boolean track = true;
        final String flurryAnalyticsApiKey = SharedPreferencesHelper.getFlurryAnalyticsApiKey(ctx);

        if (flurryAnalyticsApiKey == null
                || flurryAnalyticsApiKey.equalsIgnoreCase("xxxxxxxxxxxxxxxxxxxx")) {
            track = false;
        } else {
            track = true;
        }

        return track;
    }

    public static boolean trackGoogleAnalytics(final Context ctx) {
        boolean track = true;
        final String googleAnalyticsProfileId = SharedPreferencesHelper
                .getGoogleAnalyticsProfileId(ctx);

        if (googleAnalyticsProfileId == null
                || googleAnalyticsProfileId.equalsIgnoreCase("UA-xxxxx-xx")) {
            track = false;
        } else {
            track = true;
        }

        return track;
    }

    public static boolean trackMobclixSession(final Context ctx) {
        boolean track = true;
        final String mobclixApplicationId = SharedPreferencesHelper.getMobclixApplicationId(ctx);

        if (mobclixApplicationId == null
                || mobclixApplicationId.equalsIgnoreCase("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")) {
            track = false;
        } else {
            track = true;
        }

        return track;
    }

    // Shared getter util methods

    public static CharSequence getVersionName(final Context ctx) {
        CharSequence version_name = "";
        try {
            final PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            version_name = packageInfo.versionName;
        } catch (final NameNotFoundException nnfe) {
            Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
        }
        return version_name;
    }

    public static boolean isOnline(final Context ctx) {
        final ConnectivityManager cm = (ConnectivityManager)ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return ni.isConnectedOrConnecting();
        } else {
            return false;
        }
    }
}
