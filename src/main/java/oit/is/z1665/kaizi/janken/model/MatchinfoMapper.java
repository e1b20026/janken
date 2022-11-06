package oit.is.z1665.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchinfoMapper {

  @Select("SELECT * from matchinfo where isActive is true;")
  ArrayList<Matchinfo> selectTrueByMatchinfo();

  @Select("SELECT * from matchinfo where isActive is true and user1 = #{user1} and user2 = #{user2};")
  Matchinfo selectCheckUserByMatchinfo(int user1, int user2);

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},${isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchinfo(Matchinfo matchinfo);

  @Update("UPDATE matchinfo SET isActive = false")
  void updateByisActive(Matchinfo matchinfo);
}
