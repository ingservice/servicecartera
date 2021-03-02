package com.evertec.cargararchivoplano.main;

import com.evertec.cargararchivoplano.business.ServiceApp;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class MainClass implements java.io.Serializable {

    public static Timer runner1 = null;
    public static ServiceApp srv = null;
    private static final Lock seccionCritica = new ReentrantLock();

    ////start service
    public static void start(String[] args) {
        try {
            long delay = 1000L;
            long period = com.evertec.cargararchivoplano.util.Helper.obtenerTiempoEjecucion();
            runner1 = new Timer();
            runner1.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        //inicio    
                        seccionCritica.lock();
                        if (srv == null) {
                            srv = new ServiceApp();
                            srv.executeProcess();
                            srv = null;
                        }
                    } catch (Exception ex) {
                        StringWriter errors = new StringWriter();
                        ex.printStackTrace(new PrintWriter(errors));
                        com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
                        srv = null;
                    } finally {
                        seccionCritica.unlock();
                    }
                }//fin run
            }//fin timertask
                    ,
                     delay, period);
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
        }
    }

    ///detener el servicio
    public static void stop(String[] args) {
        try {
            if (runner1 != null) {
                if (srv != null) {
                    srv.stopProcess();
                    srv = null;
                }
                runner1.cancel();
                runner1.purge();
                runner1 = null;
            }
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
        }
    }
}
