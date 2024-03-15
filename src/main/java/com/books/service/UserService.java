package com.books.service;

import com.books.config.AES256Util;
import com.books.config.JwtProvider;
import com.books.model.UserModel;
import com.books.mapper.UserMapper;
import com.books.vo.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private static final String PHONE_NUMBER_RULE =
      "^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$"; 

  private final UserMapper userMapper;
  private final JwtProvider jwtProvider;

  @Transactional
  public void signup(UserModel userModel) { 

    if (isDuplicatedNickname(userModel.getNickname())) {
      throw new IllegalStateException("Duplicate nickname.");
    }

    if (!userModel.getPhone().matches(PHONE_NUMBER_RULE)) { 
      throw new IllegalArgumentException("Invalid phone number format.");
    }

    UserModel encryptedUserModel = UserModel.builder() 
        .nickname(userModel.getNickname())
        .password(AES256Util.encrypt(userModel.getPassword()))
        .phone(userModel.getPhone()) 
        .isAdmin(userModel.isAdmin())
        .build();

    int saveCount = userMapper.save(encryptedUserModel); 

    if (saveCount != 1) {
      throw new IllegalStateException("Error occurred during signup.");
    }
  }

  public boolean isDuplicatedNickname(String nickname) {
    return userMapper.nicknameCheck(nickname) == 1;
  }

  public String login(LoginForm loginForm) {

    LoginForm newLoginForm = LoginForm.builder()
        .nickname(loginForm.getNickname())
        .password(AES256Util.encrypt(loginForm.getPassword())).build();

    UserModel userModel = userMapper.login(newLoginForm); 

    return jwtProvider.createToken(userModel.getNickname(), userModel.getUserId()); 
  }

  public void updateUser(UserModel userModel) {
    userMapper.update(userModel);
  }

  public void deleteUser(Long userId) {
    userMapper.delete(userId);
  }

  public List<UserModel> getAllUsers() {
    return userMapper.getAllUsers();
  }

  public void updateProfile(Long userId, UserModel userModel) {
    userMapper.updateProfile(userId, userModel);
}

}
