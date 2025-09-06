package com.example.inventory.service;

import com.example.inventory.dto.request.LoginRequest;
import com.example.inventory.dto.request.SignUpRequest;
import com.example.inventory.dto.response.UserResponse;
import com.example.inventory.exception.ResourceExistException;
import com.example.inventory.model.Role;
import com.example.inventory.model.RoleEnum;
import com.example.inventory.model.User;
import com.example.inventory.repository.RoleRepository;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.security.TokenUtils;
import com.example.inventory.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;


    @Override
    public String registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ("Username is already in use");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ("Email is already in use");
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        Set<String> textRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if(textRoles.isEmpty()) {
            Role userRole = roleRepository.findByRoleName(RoleEnum.ROLE_USER)
                    .orElseThrow(()-> new ResourceExistException("Error: Role"));
            roles.add(userRole);
        }else{
            textRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                      Role adminRole = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                              .orElseThrow(()-> new ResourceExistException("Error: Role"));
                      roles.add(adminRole);
                      break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleEnum.ROLE_USER)
                                .orElseThrow(()-> new ResourceExistException("Error: Role"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/signin")
    public UserResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }catch(UsernameNotFoundException e){
            throw new BadCredentialsException("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie cookie = tokenUtils.generateResponseCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item-> item.getAuthority()).toList();

        UserResponse userResponse = new UserResponse(userDetails.getUserId(),userDetails.getUsername(),cookie,roles);

        return userResponse;
    }

    public String currentUserName(Authentication authentication){
        if(authentication != null){
            return authentication.getName();
        }else
            return "";
    }

    public UserResponse getUserDetails(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).toList();

        UserResponse response = new UserResponse(userDetails.getUserId(),userDetails.getUsername(),roles);
        return response;
    }

    public ResponseCookie signOut(){
        ResponseCookie cookie =tokenUtils.cleanResponseCookie();
        return cookie;
    }

}
