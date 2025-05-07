package kr.co.iltuo.service;

import jakarta.transaction.Transactional;
import kr.co.iltuo.common.code.ResponseCode;
import kr.co.iltuo.common.exception.CustomException;
import kr.co.iltuo.dto.request.auth.*;
import kr.co.iltuo.entity.auth.*;
import kr.co.iltuo.provider.JwtProvider;
import kr.co.iltuo.repository.auth.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import kr.co.iltuo.dto.response.auth.*;
import lombok.*;

import java.time.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final NativeAuthRepository nativeAuthRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public SignInResponseDto signInNative(NativeSignInRequestDto nativeSignInRequestDto) {
        User user = userRepository.findByUserId(nativeSignInRequestDto.getUserId());
        if (user == null || !user.isValid()) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(nativeSignInRequestDto.getPassword(), nativeAuth.getPassword())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        String token = jwtProvider.generateToken(user);
        return new SignInResponseDto(token);
    }


    @Override
    public UserIdDuplicateCheckResponseDto idDuplicateCheck(UserIdDuplicateCheckRequestDto userIdDuplicateCheckRequestDto) {
        int count = userRepository.countByUserIdAndIsValidTrue(userIdDuplicateCheckRequestDto.getUserId());
        if (count != 0) {
            throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
        }
        return new UserIdDuplicateCheckResponseDto(true);
    }

    @Override
    @Transactional
    public SignInResponseDto signUpNative(NativeSignUpRequestDto nativeSignUpRequestDto) {
        int count = userRepository.countByUserId(nativeSignUpRequestDto.getUserId());
        User user;
        if (count != 0) {
            user = userRepository.findByUserIdAndIsValidFalse(nativeSignUpRequestDto.getUserId());
            if(user == null){
                throw new CustomException(ResponseCode.DUPLICATE_RESOURCE);
            }
            user.updateInfo(
                    nativeSignUpRequestDto.getUserName(),
                    nativeSignUpRequestDto.getPhoneNumber(),
                    nativeSignUpRequestDto.getEmail(),
                    LocalDateTime.now()
            );
            user.updateUserValid(true);
            userRepository.save(user);

            NativeAuth nativeAuth = nativeAuthRepository.findById(user.getUserIdx())
                    .orElseThrow(() -> new CustomException(ResponseCode.CONFLICT));
            nativeAuth.changePassword(passwordEncoder.encode(nativeSignUpRequestDto.getPassword()));
            nativeAuthRepository.save(nativeAuth);

            Address address = addressRepository.findByUserIdxAndIsMainTrue(user.getUserIdx());
            if(address == null){
                throw new CustomException(ResponseCode.CONFLICT);
            }
            address.updateAddress(
                    nativeSignUpRequestDto.getPostalCode(),
                    nativeSignUpRequestDto.getDefaultAddress(),
                    nativeSignUpRequestDto.getDetailAddress(),
                    nativeSignUpRequestDto.getExtraAddress(),
                    true
            );
            addressRepository.save(address);

        } else {
            user = User.builder()
                    .userId(nativeSignUpRequestDto.getUserId())
                    .userName(nativeSignUpRequestDto.getUserName())
                    .phoneNumber(nativeSignUpRequestDto.getPhoneNumber())
                    .email(nativeSignUpRequestDto.getEmail())
                    .authMethodCode("AM001")
                    .registerDate(LocalDateTime.now())
                    .build();
            userRepository.save(user);

            NativeAuth nativeAuth = NativeAuth.builder()
                    .userIdx(user.getUserIdx())
                    .password(passwordEncoder.encode(nativeSignUpRequestDto.getPassword()))
                    .build();
            nativeAuthRepository.save(nativeAuth);

            Address address = Address.builder()
                    .userIdx(user.getUserIdx())
                    .postalCode(nativeSignUpRequestDto.getPostalCode())
                    .defaultAddress(nativeSignUpRequestDto.getDefaultAddress())
                    .detailAddress(nativeSignUpRequestDto.getDetailAddress())
                    .extraAddress(nativeSignUpRequestDto.getExtraAddress())
                    .isMain(true)
                    .build();
            addressRepository.save(address);
        }
        String token = jwtProvider.generateToken(user);
        return new SignInResponseDto(token);
    }
}
