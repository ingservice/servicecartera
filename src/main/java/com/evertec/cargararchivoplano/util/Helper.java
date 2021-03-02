package com.evertec.cargararchivoplano.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class Helper {

    public static void writelog(String info) {
        try {
            java.util.List<String> list = new java.util.ArrayList<>();
            java.util.Date now = new java.util.Date();
            java.text.SimpleDateFormat fr = new java.text.SimpleDateFormat("yyyyMMdd");
            java.text.SimpleDateFormat frtm = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm a");
            String ruta = "" + new java.io.File("").getAbsolutePath() + "\\logs\\" + fr.format(now).trim() + ".log";
            java.io.File doc = new java.io.File(ruta);
            if (doc.exists()) {
                list = Files.readAllLines(Paths.get(ruta), Charset.forName("UTF-8"));
                list.add(frtm.format(now) + ": " + info);
            } else {
                list.add(frtm.format(now) + ": " + info);
            }
            Files.write(Paths.get(ruta), list, Charset.forName("UTF-8"));
        } catch (Exception ex) {
        }
    }
    
    public static String rutaFolder()throws Exception{
         try{
            Properties prop = new Properties();
            String ruta = "" + new java.io.File("").getAbsolutePath() + "\\config.properties";
            java.io.File doc = new java.io.File(ruta);
            if(doc.exists()==true){
                prop.load(new FileInputStream(ruta));                
                return prop.getProperty("srv.folder.read").trim().length()>0?prop.getProperty("srv.folder.read").trim():"";
            }
            return "";
         } catch (Exception ex) {
            StringWriter errores = new StringWriter();
            ex.printStackTrace(new PrintWriter(errores));
            writelog(errores.toString());
            throw new Exception(errores.toString());
        }
    }
    
    public static long obtenerTiempoEjecucion()throws Exception{
        try{
            Properties prop = new Properties();
            String ruta = "" + new java.io.File("").getAbsolutePath() + "\\config.properties";
            java.io.File doc = new java.io.File(ruta);
            if(doc.exists()==true){
                prop.load(new FileInputStream(ruta));
                long value = Long.parseLong( (prop.getProperty("srv.time.exec").trim().length()>0)?prop.getProperty("srv.time.exec").trim():"1" ) * 1000L;
                return value;
            }
            return 1000L;
         } catch (Exception ex) {
            StringWriter errores = new StringWriter();
            ex.printStackTrace(new PrintWriter(errores));
            writelog(errores.toString());
            throw new Exception(errores.toString());
        }
    }
    
    public static boolean ValidarFormatoFecha(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            java.util.Date fch = sdf.parse(date);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
