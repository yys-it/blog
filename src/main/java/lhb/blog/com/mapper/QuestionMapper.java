package lhb.blog.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lhb.blog.com.dto.QuestionDTO;
import lhb.blog.com.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper{

    /**
     * 发布问题
     * @param title 问题标题
     * @param description 补充内容
     * @param tag 问题标签
     * @return
     */
    @Insert("insert into question (title,description,tag,creator) value(#{title},#{description},#{tag},#{id})")
    int insertQuestion(@Param("title")String title, @Param("description")String description, @Param("tag")String tag,@Param("id") Integer id);

    /**
     * 修改问题
     * @param title 问题标题
     * @param description 补充内容
     * @param tag 问题标签
     * @param id
     * @return
     */
    @Update("update question set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    int update(@Param("title") String title, @Param("description") String description,@Param("tag") String tag, @Param("id") Integer id);


    /**
     * 相关问题页面相关问题展示
     * @param question
     * @return
     */
    List<Question> selectInQuestion(Question question);


    /**
     * 首页问题展示
     * @return
     */
    List<QuestionDTO> list(Integer page, Integer size);

    /**
     * 获取问题记录总数
     * @return
     */
    Integer count();

    /**
     * 根据id获取问题信息
     * @param id
     * @return
     */
    QuestionDTO selectById(Integer id);

    /**
     * 增加评论数
     * @param id
     * @param viewConut
     * @return
     */
    @Update("update question set view_count = #{viewcount}+1 where id = #{id}")
    int inView(@Param("id") Integer id, @Param("viewcount") Integer viewConut);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    void inComment(Integer id);

    @Select("select * from question where id=#{id}")
    Question selectOne(Integer id);

    /**
     * 我的问题页面问题展示
     * @param id
     * @param page
     * @param size
     * @return
     */
    List<QuestionDTO> selectMyQuestion(Integer id, Integer page, Integer size);

    Integer myQuestionCount(Integer id);

    @Delete("delete from question where question.id =#{id}")
    void deleteById(Integer id);

    //List<QuestionDTO> selectLikeBySearch(String search, Integer page, Integer size);
    List<QuestionDTO> selectLikeBySearch(Map<String, Object> map);

    Integer countLikeBySearch(String search);
}
