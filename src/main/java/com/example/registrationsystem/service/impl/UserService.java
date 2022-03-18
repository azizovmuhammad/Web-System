package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.dto.LoginDto;
import com.example.registrationsystem.dto.RegUserDto;
import com.example.registrationsystem.dto.response.Response;
import com.example.registrationsystem.entity.Role;
import com.example.registrationsystem.entity.User;
import com.example.registrationsystem.repository.RoleRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    public static User currentUser;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }


    public HttpEntity<?> register(RegUserDto regUserDto) {

        Boolean emailExists = null;
        if(regUserDto != null && regUserDto.getEmail() != null){
            emailExists = emailExists(regUserDto.getEmail());
        }
        if(emailExists!= null && emailExists)
            return ResponseEntity.status(422).body(new Response(false, "Email is invalid or already taken", regUserDto.getEmail()));

        User user = new User();
        user.setFirstname(regUserDto.getFirstname());
        user.setLastname(regUserDto.getLastname());
        user.setEmail(regUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(regUserDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        for(Long roleId : regUserDto.getRoleIdSet()){
            Optional<Role> byId = roleRepository.findById(roleId);
            byId.ifPresent(roles::add);
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        Response response = new Response(true, "Successfully registered",savedUser.getEmail());
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    public HttpEntity<?> login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        currentUser = principal;
        String generatedToken = jwtProvider.generateToken(principal.getEmail(), principal.getRoles());
        Response response = new Response(true, "Token", generatedToken);

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    private Boolean emailExists(String email) {
        User user = findByEmail(email);
        return user != null;
    }

    private User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }



    /**
     * Return error code and default message set for the validation annotation
     * @param errors errors
     * @return map of error code as key, default message as value, e.g. :['Email': 'invalid email address']
     */
    public Map<String, String> getErrors(Errors errors) {
        Map<String, String> errorList = new HashMap<>();
        for (ObjectError error : errors.getAllErrors()) {
            String code = error.getCode();
            if(error.getCodes()!= null && error.getCodes().length > 0){
                code = error.getCodes()[0];
            }
            errorList.put(code, error.getDefaultMessage());
        }
        return errorList;
    }

}
