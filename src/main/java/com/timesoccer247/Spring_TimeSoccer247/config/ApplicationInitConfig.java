package com.timesoccer247.Spring_TimeSoccer247.config;

import com.timesoccer247.Spring_TimeSoccer247.constants.GenderEnum;
import com.timesoccer247.Spring_TimeSoccer247.constants.RoleEnum;
import com.timesoccer247.Spring_TimeSoccer247.entity.Permission;
import com.timesoccer247.Spring_TimeSoccer247.entity.Role;
import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import com.timesoccer247.Spring_TimeSoccer247.exception.AppException;
import com.timesoccer247.Spring_TimeSoccer247.exception.ErrorCode;
import com.timesoccer247.Spring_TimeSoccer247.repository.PermissionRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.RoleRepository;
import com.timesoccer247.Spring_TimeSoccer247.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(){
        log.info("INIT APPLICATION....");
        return args ->{

            if(roleRepository.count() == 0){
                List<Permission> adminPermissions = permissionRepository.findAll();

                Role userRole = roleRepository.save(Role.builder()
                        .name(RoleEnum.USER.name())
                        .description("ROLE_USER")
                        .build());


                Role managerRole = roleRepository.save(Role.builder()
                        .name(RoleEnum.MANAGER.name())
                        .description("ROLE_MANAGER")
                        .build());

                List<Role> roleList = List.of(userRole, managerRole);
                roleRepository.saveAll(roleList);
            }

            if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
                Role managerRole = roleRepository.findByName(RoleEnum.MANAGER.name()).orElseThrow(
                        () -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

                User admin = User.builder()
                        .name("Admin")
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(Set.of(managerRole))
                        .gender(GenderEnum.MALE)
                        .build();

                userRepository.save(admin);
            }
        };
    }

}
