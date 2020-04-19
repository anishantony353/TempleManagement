package com.saarit.temple_management.templemanagement.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.core.app.ActivityCompat;

public class Utility {

    private static String TAG = Utility.class.getSimpleName();

    private static boolean isLogEnabled = true;


    public static void log(String TAG,String msg){
        if(isLogEnabled){
            Log.i(TAG,msg);
        }
    }

    public static boolean hasPermission(Context context) {

        int MyVersion = Build.VERSION.SDK_INT;
        if(MyVersion >= Build.VERSION_CODES.M){

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                return false;
            }else{
                return true;
            }

        }else{
            return true;
        }

    }

    public static void requestForPermission(Activity context) {

        ActivityCompat.requestPermissions(context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constant.REQUEST_CODE_REQUEST_LOCATION_PERMISSION);

    }

    public static void showToast(String msg,int length,Context context){

        Toast.makeText(context,msg,length).show();

    }

    public static Uri getMediaFileUri(Context context, int requestType){

        return Uri.fromFile(getMediaFile(context,requestType));

    }

    public static File getMediaFile(Context context, int requestType) {
        Log.i(TAG,"About to create img_file");

        File imgDir=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"temple_management");

        if(!imgDir.exists()){
            if(!imgDir.mkdirs()){

                showToast("Could not create Image Directory",Toast.LENGTH_SHORT,context);

                return null;
            }
        }

        File imgFile;
        String fileName = "";
        switch(requestType){
            case Constant.REQUEST_CODE_TAKE_FRONT_IMAGE:
                fileName = "temp_"+Constant.IMAGE_TYPE_FRONT+".jpg";
                break;

            case Constant.REQUEST_CODE_TAKE_LEFT_IMAGE:
                fileName = "temp_"+Constant.IMAGE_TYPE_LEFT+".jpg";
                break;
            case Constant.REQUEST_CODE_TAKE_RIGHT_IMAGE:
                fileName = "temp_"+Constant.IMAGE_TYPE_RIGHT+".jpg";
                break;

            case Constant.REQUEST_CODE_TAKE_ENTRY_IMAGE:
                fileName = "temp_"+Constant.IMAGE_TYPE_ENTRY+".jpg";
                break;
            case Constant.REQUEST_CODE_TAKE_CUSTOM_1_IMAGE:
                fileName = "temp_"+Constant.IMAGE_TYPE_CUSTOM_1+".jpg";
                break;

            case Constant.REQUEST_CODE_TAKE_CUSTOM_2_IMAGE:
                fileName = "temp_"+Constant.IMAGE_TYPE_CUSTOM_2+".jpg";
                break;

        }

        imgFile = new File(imgDir,fileName);

        return imgFile;
    }

    public static Bitmap rotateImageWithPath(String path) throws IOException {

        Bitmap bitmap = BitmapFactory.decodeFile(path);


        ExifInterface ei = new ExifInterface(path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }


        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    public static boolean renameImage(String oldName,String newName,Context context) {
        boolean value = false;

        File imgDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temple_management");

        File tempFile = new File(imgDir, oldName);
        if (tempFile.exists()) {

            Utility.log(TAG, "TempFile Exists");
            File file = new File(imgDir, newName);
            if (file.exists()) {
                file.delete();
            }
            value = tempFile.renameTo(file);

        }else{
            value = true;
        }

        return value;
    }

    public static void deleteTempImages_Form1(Context context){

        File imgDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temple_management");

        File tempFront = new File(imgDir, "temp_"+Constant.IMAGE_TYPE_FRONT+".jpg");
        if (tempFront.exists()) {
            tempFront.delete();
        }

        File tempLeft = new File(imgDir, "temp_"+Constant.IMAGE_TYPE_LEFT+".jpg");
        if (tempLeft.exists()) {
            tempLeft.delete();
        }

        File tempRight = new File(imgDir, "temp_"+Constant.IMAGE_TYPE_RIGHT+".jpg");
        if (tempRight.exists()) {
            tempRight.delete();
        }

        File tempEntry = new File(imgDir, "temp_"+Constant.IMAGE_TYPE_ENTRY+".jpg");
        if (tempEntry.exists()) {
            tempEntry.delete();
        }

    }

    public static void deleteTempImages_Form2(Context context){

        File imgDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temple_management");

        File tempCustom1 = new File(imgDir, "temp_"+Constant.IMAGE_TYPE_CUSTOM_1+".jpg");
        if (tempCustom1.exists()) {
            tempCustom1.delete();
        }

        File tempCustom2 = new File(imgDir, "temp_"+Constant.IMAGE_TYPE_CUSTOM_2+".jpg");
        if (tempCustom2.exists()) {
            tempCustom2.delete();
        }

    }

    public static void deleteImagesById_Form1(Context context, int id){

        File imgDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temple_management");

        File imgFront = new File(imgDir, id+"_"+Constant.IMAGE_TYPE_FRONT+".jpg");
        if (imgFront.exists()) {
            imgFront.delete();
        }

        File imgLeft = new File(imgDir, id+"_"+Constant.IMAGE_TYPE_LEFT+".jpg");
        if (imgLeft.exists()) {
            imgLeft.delete();
        }

        File imgRight = new File(imgDir, id+"_"+Constant.IMAGE_TYPE_RIGHT+".jpg");
        if (imgRight.exists()) {
            imgRight.delete();
        }

        File imgEntry = new File(imgDir, id+"_"+Constant.IMAGE_TYPE_ENTRY+".jpg");
        if (imgEntry.exists()) {
            imgEntry.delete();
        }

    }

    public static void deleteImagesById_Form2(Context context, int id){

        File imgDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temple_management");

        File imgCustom1 = new File(imgDir, id+"_"+Constant.IMAGE_TYPE_CUSTOM_1+".jpg");
        if (imgCustom1.exists()) {
            imgCustom1.delete();
        }

        File imgCustom2 = new File(imgDir, id+"_"+Constant.IMAGE_TYPE_CUSTOM_2+".jpg");
        if (imgCustom2.exists()) {
            imgCustom2.delete();
        }


    }

    public static Uri getLocalImageUri(Context context,String type,int id) throws FileNotFoundException {

        File imgDir=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"temple_management");

        if(!imgDir.exists()){

            throw new FileNotFoundException("Image Directory missing");

        }

        File imgFile = new File(imgDir,id+"_"+type+".jpg");
        if(!imgFile.exists()){

            throw new FileNotFoundException(id+"_"+type+".jpg missing");

        }


        return Uri.fromFile(imgFile);
    }
    public static File getLocalImageFile(Context context,String type,int id) throws FileNotFoundException {

        File imgDir=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"temple_management");

        if(!imgDir.exists()){

            throw new FileNotFoundException("Image Directory missing");

        }

        File imgFile = new File(imgDir,id+"_"+type+".jpg");
        if(!imgFile.exists()){

            throw new FileNotFoundException(id+"_"+type+".jpg missing");

        }


        return imgFile;
    }

    public static String formatDate(int year,int month,int day){
        String date = "";

        String monthString = String.valueOf(month);
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }

        String dayString = String.valueOf(day);
        if (dayString.length() == 1) {
            dayString = "0" + dayString;
        }

        date = year+"-"+monthString+"-"+dayString;

        return date;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
