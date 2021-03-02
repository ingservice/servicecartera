package com.evertec.cargararchivoplano.mapper;
import com.evertec.cargararchivoplano.entity.*;
import org.apache.ibatis.annotations.*;
import java.util.*;
/**
 *
 * @author Oswaldo Tuberquia
 */
public interface CarteraMapper {    
    @Select("SELECT * FROM cartera WHERE idcliente=#{idcliente}")
    public Cartera findCarteraByIdCliente(@Param("idcliente") String idcliente);
    
    @Update("UPDATE cartera SET montodeuda=#{montodeuda},vencimiento=#{vencimiento},correo=#{correo} "
           +" WHERE idcliente=#{idcliente} AND iddeuda=#{iddeuda}")
    public void updateCarteraCliente(Cartera cartera);
    
    @Insert("INSERT INTO cartera (idcliente,nombrecliente,correo,montodeuda,iddeuda,vencimiento) "
          +" VALUES(#{idcliente},#{nombrecliente},#{correo},#{montodeuda},#{iddeuda},#{vencimiento})")
    public int insertCarteraCliente(Cartera cartera);
    
    @Select("SELECT * FROM cartera order by idcliente")
    public List<Cartera> findAllCartera();
    
}
