package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.common.ErrorInfo;
import com.robbie.shoppingmall.dao.UsersRepository;
import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.exceptions.ValidException;
import com.robbie.shoppingmall.model.LoginTocken;
import com.robbie.shoppingmall.model.PasswordInfo;
import com.robbie.shoppingmall.model.Users;
import com.robbie.shoppingmall.service.interfaces.UsersService;
import com.robbie.shoppingmall.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {
  Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

  @Autowired private UsersRepository usersRepository;

  @Autowired private JWTUtil jwtUtil;

  @Override
  public Integer register(UsersRequest usersRequest) throws ValidException {
    Boolean emailExist = usersRepository.existsByEmail(usersRequest.getEmail());

    if (emailExist) {
      log.warn("該 email {} 已經被註冊", usersRequest.getEmail());
      throw new ValidException(ErrorCodeEnum.EMAIL_EXIST);
    }

    PasswordInfo passwordInfo = hashPassword(usersRequest.getPassword());

    Timestamp timestamp = covertTimestamp(LocalDateTime.now());

    Users newUser =
        Users.builder()
            .email(usersRequest.getEmail())
            .passwordHash(passwordInfo.getPasswordHash())
            .passwordSalt(passwordInfo.getPasswordSalt())
            .createdDate(timestamp)
            .lastModifiedDate(timestamp)
            .build();

    Users saveUser = usersRepository.save(newUser);

    return saveUser.getId();
  }

  @Override
  public Users findUserById(Integer userId) {
    return usersRepository.findById(userId).orElse(null);
  }

  @Override
  public LoginTocken login(UsersRequest usersRequest) throws ValidException {
    Users users = usersRepository.findByEmail(usersRequest.getEmail());

    String token = checkEmailAndPassword(users, usersRequest);

    LoginTocken loginTocken = LoginTocken.builder().token(token).email(users.getEmail()).build();

    return loginTocken;
  }

  private PasswordInfo hashPassword(String password) {
    // 生成隨機鹽值
    String salt = BCrypt.gensalt(12);

    // 使用 BCrypt 進行哈希
    String hashedPassword = BCrypt.hashpw(password, salt);

    PasswordInfo passwordInfo =
        PasswordInfo.builder().passwordHash(hashedPassword).passwordSalt(salt).build();

    return passwordInfo;
  }

  private Timestamp covertTimestamp(LocalDateTime currentTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
    // 格式化當前時間
    String formattedDateTime = currentTime.format(formatter);

    // 將格式化的時間轉換為 Timestamp
    return Timestamp.valueOf(formattedDateTime);
  }

  private String checkEmailAndPassword(Users users, UsersRequest usersRequest)
      throws ValidException {
    if (users == null) {
      throw new ValidException(ErrorCodeEnum.EMAIL_NOT_EXIST);
    }
    boolean passwordMatches = BCrypt.checkpw(usersRequest.getPassword(), users.getPasswordHash());

    if (!passwordMatches) {
      throw new ValidException(ErrorCodeEnum.EMAIL_PASSWORD_WRONG);
    }

    Map<String, Object> claims = new HashMap<>();
    claims.put("users", users);
    String token = jwtUtil.generateToken(claims, 3600000);

    return token;
  }

  @RequiredArgsConstructor
  private enum ErrorCodeEnum implements ErrorInfo {
    EMAIL_EXIST("Email 已經被註冊"),
    EMAIL_NOT_EXIST("EMAIL 不存在"),
    EMAIL_PASSWORD_WRONG("帳號密碼驗證錯誤");

    private final String errorMessage;

    @Override
    public String getErrorCode() {
      return this.name();
    }

    @Override
    public String getErrorMessage() {
      return this.errorMessage;
    }
  }
}
