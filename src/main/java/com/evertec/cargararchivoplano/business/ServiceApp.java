package com.evertec.cargararchivoplano.business;

import com.evertec.cargararchivoplano.entity.Cartera;
import com.evertec.cargararchivoplano.repo.CarteraService;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class ServiceApp implements java.io.Serializable {

    private CarteraService service;
    private List<Cartera> lcartera;

    public ServiceApp() {
    }

    public void executeProcess() throws Exception {
        try {
            service = new CarteraService();
            File folder = new File(com.evertec.cargararchivoplano.util.Helper.rutaFolder());
            if (folder.isDirectory()) {//existe directorio definido para leer archivos
                File[] files = folder.listFiles(filefilterCustom);
                if(files.length>0){
                    for (File f : files) {
                    lcartera = processFile(f, service);
                    if (!lcartera.isEmpty() && lcartera.size() > 0) {
                        for (Cartera obj : lcartera) {
                            Cartera tmp = service.findCarteraByIdCliente(obj.getIdcliente());
                            if (tmp == null) {//nuevo registro
                                int rst = service.insertCarteraCliente(obj);
                                if (rst > 0) {
                                    com.evertec.cargararchivoplano.util.Helper.writelog("Registro procesado exitosamente para el cliente con ID:" + obj.getIdcliente() + ",IdDeuda:" + obj.getIddeuda() + ",Vencimiento:" + obj.getVencimiento());
                                } else {
                                    com.evertec.cargararchivoplano.util.Helper.writelog("error procesando registro del cliente con ID:" + obj.getIdcliente() + ",IdDeuda:" + obj.getIddeuda() + ",Vencimiento:" + obj.getVencimiento() + ", archivo:" + f.getName());
                                }
                            } else {//Valida para actualizar
                                if (tmp.getIdcliente() == obj.getIdcliente() && tmp.getIddeuda() == obj.getIddeuda()) {//actualiza
                                    service.updateCarteraCliente(obj);
                                    com.evertec.cargararchivoplano.util.Helper.writelog("Registro actualizado exitosamente para el cliente con ID:" + obj.getIdcliente() + ",IdDeuda:" + obj.getIddeuda() + ",Vencimiento:" + obj.getVencimiento());
                                } else {//otra deuda
                                    int rst = service.insertCarteraCliente(obj);
                                    if (rst > 0) {
                                        com.evertec.cargararchivoplano.util.Helper.writelog("Registro procesado exitosamente para el cliente con ID:" + obj.getIdcliente() + ",IdDeuda:" + obj.getIddeuda() + ",Vencimiento:" + obj.getVencimiento());
                                    } else {
                                        com.evertec.cargararchivoplano.util.Helper.writelog("error procesando registro del cliente con ID:" + obj.getIdcliente() + ",IdDeuda:" + obj.getIddeuda() + ",Vencimiento:" + obj.getVencimiento() + ", archivo:" + f.getName());
                                    }
                                }
                            }
                        }
                    }
                  }
                }else{
                    com.evertec.cargararchivoplano.util.Helper.writelog("No existen archivos a procesar en directorio: "+ com.evertec.cargararchivoplano.util.Helper.rutaFolder());
                }
            }else{
                com.evertec.cargararchivoplano.util.Helper.writelog("No existe el directorio: "+ com.evertec.cargararchivoplano.util.Helper.rutaFolder()+", declarado en archivo .properties" );
            }
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
            throw new Exception(errors.toString());
        }
    }

    public void stopProcess() throws Exception {
        try {
            ///inicia objetos
            service = null;
            lcartera = null;
            System.gc();
            System.runFinalization();
            ///fin inicializacion de objetos
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
            throw new Exception(errors.toString());
        }
    }

    FileFilter filefilterCustom = new FileFilter() {
        public boolean accept(File file) {
            return ((file.getName().toLowerCase().endsWith(".log")
                    || file.getName().toLowerCase().endsWith(".dat")
                    || file.getName().toLowerCase().endsWith(".txt")) && !file.getName().toLowerCase().contains("procesado"));
        }
    };

    private List<Cartera> processFile(File file, CarteraService srv) throws Exception {
        try {
            List<Cartera> list = new ArrayList();
            List<String> registros = new ArrayList();
            registros = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.forName("UTF-8"));
            String newname = file.getName().substring(0, file.getName().length() - 4) + "-procesado" + file.getName().substring(file.getName().length() - 4, file.getName().length());
            int conteo = 1;
            for (String obj : registros) {
                String[] reg = obj.trim().split(Pattern.quote(";"));
                if (reg.length == 6) {
                    if (com.evertec.cargararchivoplano.util.Helper.ValidarFormatoFecha(reg[5]) == false) {
                        com.evertec.cargararchivoplano.util.Helper.writelog("Registro en linea " + conteo + ", del archivo " + newname + ", formato de fecha invalido, se esperaba dd-MM-yyyy");
                    } else {
                        String[] fech = reg[5].trim().split(Pattern.quote("-"));
                        list.add(new Cartera(0, reg[0].trim(), reg[1].trim(), reg[2].trim(), java.math.BigDecimal.valueOf(Double.parseDouble(reg[3].trim())), reg[4].trim(), java.sql.Date.valueOf(fech[2].trim() + "-" + fech[1].trim() + "-" + fech[0].trim())));
                    }
                } else {
                    com.evertec.cargararchivoplano.util.Helper.writelog("Registro en linea " + conteo + ", del archivo " + newname + ", no cumple con longitud esperada");
                }
                conteo++;
            }
            Files.write(Paths.get(file.getAbsolutePath().substring(0, (file.getAbsoluteFile().toString().length() - file.getName().length())) + newname), registros, Charset.forName("UTF-8"));
            Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            return list;
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
            throw new Exception(errors.toString());
        }
    }
}
