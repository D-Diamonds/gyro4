package com.example.gyro4;

import android.content.Context;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataSaver<Object> {

    private String savePath;

    private Object dataPointer;

    private Context context;

    private boolean saving;

    public DataSaver (Context context, String savePath, Object dataPointer) {
        this.savePath = savePath;
        this.dataPointer = dataPointer;
        this.context = context.getApplicationContext();
    }

    public DataSaver (Context context, String savePath) {
        this.savePath = savePath;
        this.context = context.getApplicationContext();
    }

    @SuppressWarnings("unchecked")
    public Object onStart() {
        try {
            File dataFile = new File(context.getFilesDir(), savePath);
            //dataFile.delete();
            if (!dataFile.exists() && dataPointer != null) {
                dataFile.createNewFile();
                System.out.println("Creating " + savePath);
                FileOutputStream fileOutputStream = context.openFileOutput(savePath, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(dataPointer);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            System.out.println(dataFile.getAbsolutePath());
            FileInputStream fileInputStream = context.openFileInput(savePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            dataPointer = (Object) objectInputStream.readObject();
            fileInputStream.close();
            return dataPointer;
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Load error: " + e.toString());
        }
        return null;
    }

    public Object getDataPointer() {
        return dataPointer;
    }

    // enables the serialization and saving process that runs every 30 seconds
    public void save() {
        try {
                System.out.println("Saving...");
                FileOutputStream fileOutputStream = context.openFileOutput(savePath, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(dataPointer);
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("Saving error: " + e.toString());
        }
    }
}