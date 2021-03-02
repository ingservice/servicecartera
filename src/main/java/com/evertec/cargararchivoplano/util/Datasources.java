package com.evertec.cargararchivoplano.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class Datasources implements java.io.Serializable {

      public static DataSource getDataSourceApp() throws Exception {
        try {
            Properties prop = new Properties();
            String ruta = "" + new java.io.File("").getAbsolutePath() + "\\config.properties";
            java.io.File doc = new java.io.File(ruta);
            if (doc.exists() == true) {
                prop.load(new FileInputStream(ruta));
                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setUrl("jdbc:postgresql://" + prop.getProperty("db.host.app").trim() + ":" + prop.getProperty("db.port.app").trim() + "/" + prop.getProperty("db.name.app").trim());
                dataSource.setUsername(prop.getProperty("db.usr.app").trim());
                dataSource.setPassword(prop.getProperty("db.psw.app").trim());
                return dataSource;
            }
            return null;
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
            throw new Exception(errors.toString());
        }
    }
}
