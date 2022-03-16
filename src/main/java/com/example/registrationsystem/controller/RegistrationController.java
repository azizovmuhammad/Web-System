package com.example.registrationsystem.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import com.example.registrationsystem.dto.RegUserDto;
import com.example.registrationsystem.service.RoleService;
import com.example.registrationsystem.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1/public")
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/auth/register")
    public HttpEntity<?> regiter(@Valid @RequestBody RegUserDto regUserDto, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(userService.getErrors(errors));
        }
        return userService.register(regUserDto);
    }

}
