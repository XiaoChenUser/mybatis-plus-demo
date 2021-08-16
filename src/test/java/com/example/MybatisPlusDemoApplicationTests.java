package com.example;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.UserDTO;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MybatisPlusDemoApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MybatisPlusDemoApplicationTests.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void logTest(){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(context);
    }

    @Test
    void testInsert(){
        for (int i = 0; i < 1; i++) {
            UserDTO user = new UserDTO().setUsername(UUID.randomUUID().toString()).setPassword("123456");
            userMapper.insert(user);
        }
    }

    @Test
    void testSelectById(){
        UserDTO userDTO = userMapper.selectById(4);
        System.out.println(userDTO);
    }

    @Test
    void selectByUsernameTest(){
        UserDTO userDTO = userMapper.selectByUsername("momo");
        System.out.println(userDTO);
    }

    @Test
    void selectAllTest(){
        List<UserDTO> userDTOS = userMapper.selectAll();
        userDTOS.forEach(userDTO -> System.out.println(userDTO));
    }

    @Test
    void updatePasswordByUsernameTest(){
        UserDTO userDTO = userMapper.selectById(19);
        userDTO.setPassword("0000");
        userMapper.updatePasswordByUsername(userDTO);
    }

    @Test
    void updateByIdTest(){
        UserDTO userDTO = userMapper.selectById(19);
        if (userDTO != null) {
            userDTO.setUsername("administrator");
            userMapper.updateById(userDTO);
        }

    }

    @Test
    void updatePasswordByIdTest(){
        userMapper.updatePasswordById(20, "root");
    }

    @Test
    void deleteByCreateTimeTest(){
        userMapper.deleteByCreateTime(new Date());
    }

    @Test
    void pageSelectTest(){
        IPage<UserDTO> page = new Page<>(1,10);
        page = userMapper.getPageByGtId(page);

        List<UserDTO> records = page.getRecords();
        records.forEach(userDTO -> System.out.println(userDTO));
    }

    @Test
    void selectByIds(){
        List<Integer> ids = Arrays.asList(1, 5, 9, 10);
        List<UserDTO> userDTOS = userMapper.selectByIds(ids);
        for (int i = 0; i < userDTOS.size(); i++) {
            logger.info("index:{},UserDTO{}",userDTOS.get(i).getId(), userDTOS.get(i));
        }
    }

    @Test
    void selectPageUserTest(){
        IPage<UserDTO> userDTOIPage = userMapper.selectPageUser(new Page<>(2, 5), 0);
        if(userDTOIPage.getTotal()>0){
            userDTOIPage.getRecords().forEach(userDTO -> System.out.println(userDTO));
        }
    }

}
