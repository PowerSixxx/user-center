package com.mango.usercenter.service;

import java.util.Date;

import com.mango.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 *
 * @author Baowen
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("testMango");
        user.setUserAccount("testMango");
        user.setAvatarUrl("https://www.baowenliu.com/assets/game_mc.jpg");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(true, result);
    }

    @Test
    void userRegister() {
        String userAccount = "mango";
        String userPassword = "";
        String checkPassword = "123456";
        String noteCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertEquals(-1, result);

        userAccount = "ma";
        result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertEquals(-1, result);

        userAccount = "mango";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertEquals(-1, result);

        userAccount = "man go";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertEquals(-1, result);

        userAccount = "testMango";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertEquals(-1, result);

        userAccount = "mango";
        result = userService.userRegister(userAccount, userPassword, checkPassword, noteCode, null);
        Assertions.assertTrue(result > 0);
    }
}