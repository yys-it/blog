package lhb.blog.com.mapper;

import lhb.blog.com.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id,username,password,avatars from user where id=#{id}")
    User selectUser(Integer id);

    @Select("select count(1) from user where username=#{name}")
    int selectUserByName(String name);

    @Select("select count(1) from user where username=#{username} and password=#{password}")
    int selectByNameAndPass(String username, String password);

    @Select("select id,username,password,avatars from user where username=#{username} and password=#{password}")
    User selectUserByNameAndPass(String username, String password);

    @Insert("insert into user (id,username,password,avatars) values(#{id},#{username},#{password},#{avatars})")
    int insert(Integer id,String username, String password, String avatars);

    @Select("select * from user where password = #{name}")
    User selectUserByPass(String name);

    @Select("select count(id) from user where id=#{id}")
    int countSelectById(Integer id);
}
