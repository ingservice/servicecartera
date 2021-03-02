package com.evertec.cargararchivoplano.repo;

import com.evertec.cargararchivoplano.entity.Cartera;
import com.evertec.cargararchivoplano.mapper.CarteraMapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class CarteraService implements java.io.Serializable {

    private SqlSession sqlsesion;
    private CarteraMapper mapper;

    public Cartera findCarteraByIdCliente(String idcliente) throws Exception {
        try {
            sqlsesion = SessionFactory.getInstanceSqlFactory().openSession();
            mapper = sqlsesion.getMapper(CarteraMapper.class);
            return mapper.findCarteraByIdCliente(idcliente);
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            throw new Exception(errors.toString());
        } finally {
            sqlsesion.close();
        }
    }

    public void updateCarteraCliente(Cartera cartera) throws Exception {
        try {
            sqlsesion = SessionFactory.getInstanceSqlFactory().openSession();
            mapper = sqlsesion.getMapper(CarteraMapper.class);
            mapper.updateCarteraCliente(cartera);
            sqlsesion.commit();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            throw new Exception(errors.toString());
        } finally {
            sqlsesion.close();
        }
    }

    public int insertCarteraCliente(Cartera cartera) throws Exception {
        try {
            sqlsesion = SessionFactory.getInstanceSqlFactory().openSession();
            mapper = sqlsesion.getMapper(CarteraMapper.class);
            mapper.insertCarteraCliente(cartera);
            sqlsesion.commit();
            Cartera tmp = this.findCarteraByIdCliente(cartera.getIdcliente());
            return tmp.getIdcartera();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            throw new Exception(errors.toString());
        } finally {
            sqlsesion.close();
        }
    }

    public List<Cartera> findAllCartera() throws Exception {
        try {
            sqlsesion = SessionFactory.getInstanceSqlFactory().openSession();
            mapper = sqlsesion.getMapper(CarteraMapper.class);
            return mapper.findAllCartera();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            throw new Exception(errors.toString());
        } finally {
            sqlsesion.close();
        }
    }
}
