//package com.project.ohouclonecoding.service;
//
//import com.project.ohouclonecoding.dto.SignupRequestDto;
//import com.project.ohouclonecoding.entity.User;
//import com.project.ohouclonecoding.entity.UserRoleEnum;
//import com.project.ohouclonecoding.jwt.JwtUtil;
//import com.project.ohouclonecoding.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
//    public void signup(SignupRequestDto requestDto) {
//        String nickname = requestDto.getNickname();
//        String password = passwordEncoder.encode(requestDto.getPassword());
//
//        // 회원명 중복
//        Optional<User> checkNickname = userRepository.findByNickname(nickname);
//        if(!(checkNickname.isEmpty())){
//            throw new IllegalArgumentException("중복된 회원명입니다.");
//        }
//
//        // email 중복확인
//        String email = requestDto.getEmail();
//        Optional<User> checkEmail = userRepository.findByEmail(email);
//        if(!(email.isEmpty())){
//            throw new IllegalArgumentException("중복된 이메일입니다.");
//        }
//
//        // 유저 ROLE 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if(requestDto.isAdmin()){
//        }
//    }
//}
