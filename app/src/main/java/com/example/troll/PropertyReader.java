package com.example.troll;

import android.content.Context;
import android.os.Environment;
import android.renderscript.ScriptGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertyReader {
    File file;
    FileOutputStream fos = null;
    FileInputStream fis = null;
    public PropertyReader(Context context)
    {
        file = new File(Environment.getDataDirectory()+ "/data/" + context.getPackageName(), "Props.properties");
    }

    public void writeProps(String Text, String Numbers)
    {
        try
        {
            if(!file.exists()){
                file.createNewFile();
            }

            fos = new FileOutputStream(file);

            Properties props = new Properties();
            props.setProperty("text", Text);
            props.setProperty("numbers", Numbers);
            props.store(fos, "프로퍼티 파일 쓰기");
        }
        catch(Exception Ex)
        {
            Ex.printStackTrace();
        }
    }

    public String getLastText()
    {
        if(!file.exists()){ return ""; }
        String data="";
        try
        {
            fis = new FileInputStream(file);

            Properties props = new Properties();
            props.load(fis);
            data = props.getProperty("text", "");
        }
        catch(Exception Ex)
        {
            Ex.printStackTrace();
        }

        return data;
    }

    public String getLastNumbers()
    {
        if(!file.exists()){ return ""; }
        String data="";
        try
        {
            fis = new FileInputStream(file);

            Properties props = new Properties();
            props.load(fis);
            data = props.getProperty("numbers", "");
        }
        catch(Exception Ex)
        {
            Ex.printStackTrace();
        }

        return data;
    }
}
