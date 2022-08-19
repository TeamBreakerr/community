package life.majiang.community.mapper;

import java.util.List;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    long countByExample(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int deleteByExample(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int insert(Question row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int insertSelective(Question row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    List<Question> selectByExample(QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    Question selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int updateByExampleSelective(@Param("row") Question row, @Param("example") QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int updateByExampleWithBLOBs(@Param("row") Question row, @Param("example") QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int updateByExample(@Param("row") Question row, @Param("example") QuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int updateByPrimaryKeySelective(Question row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int updateByPrimaryKeyWithBLOBs(Question row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Aug 19 22:10:42 CST 2022
     */
    int updateByPrimaryKey(Question row);
}