package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<UserDTO> {
    default UserDTO selectByUsername(String username){
        return selectOne(new QueryWrapper<UserDTO>().eq("username", username));
    }

    default void updatePasswordByUsername(UserDTO userDTO) {
        update(userDTO, new UpdateWrapper<UserDTO>().eq("username", userDTO.getUsername()));
    }

    default void updatePasswordByUsername(String username, String password){
        update(null, new UpdateWrapper<UserDTO>().eq("username", username).set("password", password));
    }

    void updatePasswordById(@Param("id") Integer id, @Param("password") String password);

    default void deleteByCreateTime(Date time){
        delete(new QueryWrapper<UserDTO>().lt("create_at", time));
    }

    default List<UserDTO> selectAll(){
        return selectList(new QueryWrapper<UserDTO>());
    }

    default IPage<UserDTO> getPageByGtId(IPage<UserDTO> page) {
        return selectPage(page, new QueryWrapper<UserDTO>().orderByAsc("username"));
    }

    List<UserDTO> selectByIds(@Param("ids") Collection<Integer> ids);

    IPage<UserDTO> selectPageUser(IPage<UserDTO> page, @Param("deleted") Integer deleted);
//    # user-mapper.xml
//    <select id="selectPageUser" parameterType="INTEGER" resultType="UserDTO">
//        SELECT * FROM user WHERE deleted = #{deleted}
//    </select>

}
