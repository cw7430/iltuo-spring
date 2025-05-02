package kr.co.iltuo.service;

import jakarta.transaction.Transactional;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.provider.JwtProvider;
import kr.co.iltuo.repository.auth.AddressRepository;
import kr.co.iltuo.repository.auth.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import kr.co.iltuo.dto.response.auth.*;
import lombok.*;

import java.time.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        User user = userRepository.findByUserId(signInRequestDto.getUserId());
        if (user == null || !passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword()) || !user.isValid()) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }
        String token = jwtProvider.generateToken(user);
        return new SignInResponseDto(token);
    }

    @Override
    public UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
        int count = userRepository.countByUserId(userIdDuplicateCheckRequestDto.getUserId());
        if (count != 0) {
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
        }
        return new UserIdDuplicateCheckResponseDto(true);
    }

    @Override
    @Transactional
    public SignInResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        User user = new User();
        Address address = new Address();
        user.setUserId(signUpRequestDto.getUserId());
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setUserName(signUpRequestDto.getUserName());
        user.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        user.setEmail(signUpRequestDto.getEmail());
        user.setRegisterDate(LocalDateTime.now());
        userRepository.save(user);

        address.setUserId(signUpRequestDto.getUserId());
        address.setPostalCode(signUpRequestDto.getPostalCode());
        address.setDefaultAddress(signUpRequestDto.getDefaultAddress());
        address.setDetailAddress(signUpRequestDto.getDetailAddress());
        address.setExtraAddress(signUpRequestDto.getExtraAddress());
        address.setMain(true);
        addressRepository.save(address);

        String token = jwtProvider.generateToken(user);
        return new SignInResponseDto(token);
    }
}
