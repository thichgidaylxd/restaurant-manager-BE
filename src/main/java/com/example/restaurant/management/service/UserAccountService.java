package com.example.restaurant.management.service;


import com.example.restaurant.management.dto.UserAccount.*;
import com.example.restaurant.management.entity.Role;
import com.example.restaurant.management.entity.UserAccount;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.RoleRepo;
import com.example.restaurant.management.repository.UserAccountRepo;
import com.example.restaurant.management.util.Builder;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserAccountService {
    UserAccountRepo userAccountRepo;
    RoleRepo roleRepo;




    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;


    public List<UserAccountResponse> findAll(){
        return userAccountRepo.findAll().stream().map(Builder::buildUserAccountResponse).toList();
    }


    public UserAccountLoginResponse login(UserAccountLogin loginRequest) {
        UserAccount userAccount = userAccountRepo.findByAccount(loginRequest.getAccount())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        if(!userAccount.getPassword().equals(loginRequest.getPassword())) throw new AppException(ErrorCode.WRONG_PASSWORD);
        return UserAccountLoginResponse.builder()
                .authenticated(true)
                .token(generateToken(userAccount))
                .build();
    }

    private String generateToken(UserAccount userAccount) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userAccount.getAccountName())
                .claim("userAccountId", userAccount.getId())
                .claim("role", userAccount.getRole().getName())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (VALID_DURATION * 1000))) // Chuyển sang mili giây)
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }


    public UserAccountRegisterResponse register(UserAccountRegisterRequest request) {
        if (userAccountRepo.findByAccount(request.getAccount()).isPresent()) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }

        if(request.getAccount().length()!=10) throw new AppException(ErrorCode.ACCOUNT_MUST_BE_10_CHARACTERS);

        if(request.getAccountName().isBlank() || request.getAccountName().isEmpty()) throw new AppException(ErrorCode.ACCOUNT_NAME_IS_BLANK);

        if (request.getPassword() == null || request.getConfirmPassword() == null ||
                request.getPassword().isBlank() || !request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        Role role = request.getRoleId()!=null?
                roleRepo.findById(request.getRoleId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED))
                :roleRepo.findByName("Khách hàng")
                    .orElseThrow(() -> new AppException(ErrorCode.DEFAULT_ROLE_NOT_EXISTED));



        UserAccount newUser = UserAccount.builder()
                .role(role)
                .accountName(request.getAccountName())
                .account(request.getAccount())
                .password(request.getPassword())
                .build();

        userAccountRepo.save(newUser);

        return Builder.buildUserAccountRegisterResponse(newUser);
    }

    public void deleteById(UUID userAccountId){
        userAccountRepo.deleteById(userAccountId);
    }

}
