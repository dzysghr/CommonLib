package com.dzy.commemlib.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by dzysg on 2017/4/4 0004.
 */

public final class FileUtils {
    private FileUtils() {
    }

    public static void deleteFolder(File f) {
        if (f == null || !f.exists()) {
            return;
        }
        if (f.delete()) {
            return;
        }

        if (f.isDirectory()) {//如果此抽象路径指代的是目录
            String[] str = f.list();//得到目录下的所有路径
            if (str == null) {
                f.delete();//如果这个目录下面是空，则直接删除
            } else {//如果目录不为空，则遍历名字，得到此抽象路径的字符串形式。
                for (String st : str) {
                    deleteFolder(new File(f, st));
                }//遍历清楚所有当前文件夹里面的所有文件。
                f.delete();
            }
        }
    }

    public static void nioTransferCopy(File source, File target) throws IOException {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } finally {
            safeClose(inStream);
            safeClose(in);
            safeClose(outStream);
            safeClose(out);
        }
    }

    public static void safeNIOTransferCopy(File source, File target) {
        try {
            nioTransferCopy(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFolder(File dir){
        dir.mkdir();
    }

    public static void copyDirectory(File srcDir, File destDir) throws IOException{

        if (srcDir.isFile()||destDir.isFile()){
            return;
        }

        if (!srcDir.exists()){
            return;
        }
        if (destDir.exists()) {
            destDir.delete();
        } else {
            if (!destDir.mkdirs()) {
                LogUtils.e("tag","复制目录失败：创建目的目录失败！");
                return ;
            }
        }
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                // 复制文件
                if (files[i].isFile()) {
                    nioTransferCopy(files[i],new File(destDir,files[i].getName()));
                } else if (files[i].isDirectory()) {
                    copyDirectory(files[i],new File(destDir,files[i].getName()));
                }
            }catch (IOException e){
                destDir.delete();
                throw e;
            }
        }
    }


    public static void safeClose(Closeable c) {
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getAllSpace() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize, blockCount, availCount;
            if (Build.VERSION.SDK_INT < 18) {
                blockSize = sf.getBlockSize();
                blockCount = sf.getBlockCount();
            } else {
                blockSize = sf.getBlockSizeLong();
                blockCount = sf.getBlockCountLong();
            }
            Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize * blockCount / 1024 + "KB");
            return blockSize * blockCount / 1024;
        }
        return 0;
    }

    public static long getFreeSpace() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize, blockCount, availCount;
            if (Build.VERSION.SDK_INT < 18) {
                blockSize = sf.getBlockSize();
                availCount = sf.getAvailableBlocks();
            } else {
                blockSize = sf.getBlockSizeLong();
                availCount = sf.getAvailableBlocksLong();
            }
            Log.d("", "可用的block数目：:" + availCount + ",剩余空间:" + availCount * blockSize / 1024 + "KB");
            return availCount * blockSize / 1024;
        }
        return 0;
    }
}
