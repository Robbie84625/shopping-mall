package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.dao.UsersRepository;
import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.exceptions.UserAlreadyExistsException;
import com.robbie.shoppingmall.model.Users;
import com.robbie.shoppingmall.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class UsersServiceImpl implements UsersService {
    Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Integer register(UsersRequest usersRequest) {
        Boolean emailExist = usersRepository.existsByEmail(usersRequest.getEmail());

        if (emailExist) {
            log.warn("該 email {} 已經被註冊",usersRequest.getEmail());
            throw new UserAlreadyExistsException("Email 已經被註冊");
        }

        PasswordInfo passwordInfo = hashPassword(usersRequest.getPassword());

        LocalDateTime currentTime = LocalDateTime.now();

        Timestamp timestamp = covertTimestamp(currentTime);


        Users newUser = Users.builder()
                .email(usersRequest.getEmail())
                .passwordHash(passwordInfo.passwordHash)
                .passwordSalt(passwordInfo.passwordSalt)
                .createdDate(timestamp)
                .lastModifiedDate(timestamp)
                .build();

        System.out.println(newUser);

        Users saveUser = usersRepository.save(newUser);

        return saveUser.getId();
    }

    @Override
    public Users findUserById(Integer userId) {
        return usersRepository.findById(userId).orElse(null);
    }

    private PasswordInfo hashPassword(String password){
        // 生成隨機鹽值
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String saltStr = Base64.getEncoder().encodeToString(salt);

        // 使用 BCrypt 進行哈希
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12) + saltStr);
        return new PasswordInfo(hashedPassword, saltStr);
    }

    @Getter
    @RequiredArgsConstructor
    private static class PasswordInfo {
        private final String passwordHash;
        private final String passwordSalt;
    }

    private Timestamp covertTimestamp(LocalDateTime currentTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
        // 格式化當前時間
        String formattedDateTime = currentTime.format(formatter);

        // 將格式化的時間轉換為 Timestamp
        return Timestamp.valueOf(formattedDateTime);
    }
}
