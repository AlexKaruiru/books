package com.books.mapper;

import com.books.model.UserModel;
import com.books.vo.LoginForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int save(UserModel userModel);
    int nicknameCheck(String name);
    UserModel getByUserId(Long userId);
    UserModel getByNickname(String nickname);
    UserModel login(LoginForm loginForm);

    int update(UserModel userModel);  
    int delete(Long userId);
    List<UserModel> getAllUsers();
    int updateProfile(Long userId, UserModel userModel);  
}


