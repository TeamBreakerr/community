package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into user_info (name, account_id, token, gmt_create, gmt_modified, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    public void insert(User user);

    @Select("select * from user_info where token = #{token}")
    User getByToken(String token);

    @Select("select * from user_info where id = #{id}")
    User getById(Integer id);

    @Select("select * from user_info where account_id = #{accountId}")
    User getByAccountId(String accountId);

    @Update("update user_info set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
