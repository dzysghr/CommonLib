package com.dzy.commemlib.utils;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageInfoUtil {
    
    private static final String TAG = "PackageInfoUtil";

    private Context mContext;
    
    public PackageInfoUtil(Context context) {
        mContext = context;
    }

    public ArrayList<InfoObject> getInstalledApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> applist = pm.queryIntentActivities(mainIntent, 0);
        Collections.sort(applist, new ResolveInfo.DisplayNameComparator(pm));//按应用Label排序

        ArrayList<InfoObject> res = new ArrayList<InfoObject>();
        for (ResolveInfo resolveInfo : applist) {
            InfoObject newInfo = new InfoObject();
            newInfo.className = resolveInfo.activityInfo.name;
            newInfo.appName = resolveInfo.loadLabel(pm).toString();
            newInfo.pkgName = resolveInfo.activityInfo.packageName;
            newInfo.PkgclassName = newInfo.pkgName;
            newInfo.icon = resolveInfo.loadIcon(pm);
            res.add(newInfo);
        }
        return res;
    }

    /**
     * get the app information from its package name
     *
     * @param packageName
     * @return the appInfo, included app, package and class name
     */
    public InfoObject getAppInfoByPackage(String packageName) {
        InfoObject appInfo = new InfoObject();
        Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
        intentToResolve.addCategory(Intent.CATEGORY_LAUNCHER);
        intentToResolve.setPackage(packageName);
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> ris = pm.queryIntentActivities(intentToResolve, 0);
        if (ris == null || ris.size() <= 0) {
            return null;
        }
        appInfo.appName = ris.get(0).loadLabel(pm).toString();
        appInfo.pkgName = (ris.get(0).activityInfo.packageName);
        appInfo.PkgclassName = (ris.get(0).activityInfo.name);
        Log.d(TAG, "getAppInfoByPackage: " + appInfo.toString());
        return appInfo;
    }

    public InfoObject getInfoObjectByPackage(String pkg) {
        Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
        intentToResolve.addCategory(Intent.CATEGORY_LAUNCHER);
        intentToResolve.setPackage(pkg);
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> ris = pm.queryIntentActivities(intentToResolve, 0);
        if (ris.isEmpty()) {
            return null;
        }
        ResolveInfo resolveInfo = ris.get(0);
        InfoObject newInfo = new InfoObject();
        newInfo.className = resolveInfo.activityInfo.name;
        newInfo.appName = resolveInfo.loadLabel(pm).toString();
        newInfo.pkgName = resolveInfo.activityInfo.packageName;
        newInfo.PkgclassName = newInfo.pkgName;
        newInfo.icon = resolveInfo.loadIcon(pm);
        return newInfo;
    }

    /**
     * get the widget information from its package name
     *
     * @param packageName
     * @return the list of widgetInfo, included package and provider name
     */
    public List<InfoObject> getWidgetInfoByPackage(String packageName) {
        List<InfoObject> widgetInfoList = new ArrayList<>();
        List<AppWidgetProviderInfo> providers = AppWidgetManager.getInstance(mContext).getInstalledProviders();
        final int providerCount = providers.size();
        for (int i = 0; i < providerCount; i++) {
            ComponentName provider = providers.get(i).provider;
            if (provider.getPackageName().equals(packageName)) {
                InfoObject widgetInfo = new InfoObject();
                widgetInfo.pkgName = (packageName);
                widgetInfo.providerName = (provider.flattenToString());
                Log.d(TAG, "getWidgetInfoByPackage: " + widgetInfo.toString());
                widgetInfoList.add(widgetInfo);
            }
        }
        return widgetInfoList;
    }

    public static class InfoObject {
        public String className = "";
        public String appName = "";
        public String pkgName = "";
        public String PkgclassName = "";
        public String providerName = "";
        public String versionName = "";
        public int versionCode = 0;
        public Drawable icon;
    }
}

