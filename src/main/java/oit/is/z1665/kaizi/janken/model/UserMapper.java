package oit.is.z1665.kaizi.janken.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from users;")
  ArrayList<User> selectAllByUser();

  @Select("SELECT * from users where id = #{id};")
  User selectByName(int id);
}
