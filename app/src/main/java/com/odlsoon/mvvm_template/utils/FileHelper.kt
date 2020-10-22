package com.odlsoon.mvvm_template.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object FileHelper {

    private val permissions = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
    )

    fun openGallery(context: Context, requestCode: Int){
        GeneralHelper.checkSinglePermissions(context as Activity, permissions, requestCode) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            (context).startActivityForResult(intent, requestCode)
        }
    }

    fun saveImage(context: Context, uri: Uri, callback: (uri: Uri?, msg: String) -> Unit){
        saveImageBeforeQ(context, uri, callback)
//        saveImageAfterQ(context, uri, callback)
    }

    private fun getMainSourceImages(): File{
        return File("" + Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + RequestHelper.IMAGE_FOLDER_NAME)
    }

    private fun convertUriToBitmap(context: Context, uri: Uri): Bitmap{
        return if(Build.VERSION.SDK_INT < 28){
            val bit = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            Bitmap.createBitmap(bit.width, bit.height, Bitmap.Config.ARGB_8888)
        }else{
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val bit = ImageDecoder.decodeBitmap(source)
            Bitmap.createBitmap(bit.width, bit.height, Bitmap.Config.ARGB_8888)
        }
    }

    private fun convertUriToFile(uri: Uri): File{
        return File( uri.path!!) // with path, return original file name
    }

    private fun saveImageBeforeQ(context: Context, uri: Uri, callback: (uri: Uri?, msg: String)-> Unit){
        val pictureDirectory = getMainSourceImages()
        if(!pictureDirectory.exists()) pictureDirectory.mkdirs()

        try {
            val file = File(pictureDirectory, convertUriToFile(uri).name)
            val out = FileOutputStream(file)

            val bitmap = convertUriToBitmap(context, uri)
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                val path = file.absolutePath
                val newFile = File(path)
                val uri = Uri.fromFile(newFile)
                callback(uri, "Image Saved")
            }

            MediaScannerConnection.scanFile(context, arrayOf(pictureDirectory.path), arrayOf("*/*")){_, _ -> }

            out.flush()
            out.close()
        }catch(e: Exception){
            callback(null, "Failed to save image")
        }
    }

    private fun saveImageAfterQ(context: Context, imgUri: Uri, callback: (uri: Uri?, msg: String)-> Unit){
        var uri : Uri? = null
        var stream : OutputStream? = null
        var cursor: Cursor? = null
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val relativePath = DIRECTORY_PICTURES + File.pathSeparator + RequestHelper.IMAGE_FOLDER_NAME
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
            put(MediaStore.Images.ImageColumns.DISPLAY_NAME, convertUriToFile(imgUri).name)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                put(MediaStore.Images.ImageColumns.RELATIVE_PATH, relativePath)
            }
        }

        try {
            uri = context.contentResolver.insert(contentUri, contentValues)
            if(uri == null){
                throw IOException("Failed to create new MediaStore record.")
            }

            cursor = context.contentResolver.query(uri, null, null, null, null)
            DatabaseUtils.dumpCursor(cursor)
            cursor?.close()

            stream = context.contentResolver.openOutputStream(uri)
            if(stream == null){
                throw IOException("Failed to get output stream.")
            }

            val bitmap = convertUriToBitmap(context, uri)
            if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)){
                throw IOException("Failed to save bitmap.")
            }

            callback(uri, "Image Saved")
        }catch (e: Exception){
            callback(null, "Failed to save image")
            if(uri != null) context.contentResolver.delete(uri, null, null)
            throw IOException(e)
        }finally {
            stream?.close()
        }
    }
}