package com.evertec.cargararchivoplano.repo;

import com.evertec.cargararchivoplano.mapper.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 *
 * @author Oswaldo Tuberquia
 */
public class SessionFactory implements java.io.Serializable {

    public static SqlSessionFactory sqlfactory = null;

    public static SqlSessionFactory getSqlSessionFactory() throws Exception {
        try {
            DataSource dataSource = com.evertec.cargararchivoplano.util.Datasources.getDataSourceApp();
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("production", transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(CarteraMapper.class);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);
            return factory;
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
            throw new Exception(errors.toString());
        }
    }

    public static SqlSessionFactory getInstanceSqlFactory() throws Exception {
        try {
            if (sqlfactory == null) {
                sqlfactory = getSqlSessionFactory();
            }
            return sqlfactory;
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            com.evertec.cargararchivoplano.util.Helper.writelog(errors.toString());
            throw new Exception(errors.toString());
        }
    }

}
