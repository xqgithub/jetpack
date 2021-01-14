package com.example.jetpack.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * desc  : SD卡相关工具类
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 路径：/data/data/应用包名/files/
     */
    public static File getFileDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * 路径:/data/data/应用包名/cache/
     */
    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * 路径: SDCard/Android/data/应用包名/files/
     */
    public static File getExternalFilesDir(Context context, String packagename) {
        return context.getExternalFilesDir(packagename);
    }

    /**
     * 路径: SDCard/Android/data/应用包名/cache/
     */
    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 路径: SDCard/你设置的文件夹名字/
     */
    public static File getExternalStorageDirectory() {
        if (isSDCardEnable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }
}