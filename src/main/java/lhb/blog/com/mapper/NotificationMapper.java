package lhb.blog.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lhb.blog.com.dto.NotificationDTO;
import lhb.blog.com.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper{

    @Insert("insert into notification (notifier,recevier,outerId,type,status,notifier_name,recevier_content) value(#{notifier},#{recevier},#{outerId},#{type},#{status},#{notifierName},#{content})")
    int  insert(Notification notification);

    List<NotificationDTO> selectById(Integer id, int page, Integer size);

    @Select("select count(1) from notification where recevier = #{id}")
    int count(Integer id);

    @Select("select count(1) from notification where recevier = #{id} and status=0")
    int navificationUnreadCount(Integer id);

    @Select("select * from notification where id = #{id}")
    NotificationDTO selectOneById(Integer id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateStatus(Integer status, Integer id);
}
