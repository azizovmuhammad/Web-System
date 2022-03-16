package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.RoleDto;
import com.example.registrationsystem.entity.Role;
import com.example.registrationsystem.service.RoleService;
import com.example.registrationsystem.util.ApiPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/createRole")
    public HttpEntity<?> creatRole(@RequestBody RoleDto roleDto){
        return roleService.createRole(roleDto);
    }

    @PutMapping("/updateRole/{id}")
    public HttpEntity<?> updateRole(@RequestBody RoleDto roleDto, @PathVariable Long id){
        return roleService.updateRole(roleDto, id);
    }

    @GetMapping("/findBYid/{id}")
    public Role findById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @GetMapping("/findAll")
    @ApiPageable
    public HttpEntity<?> findAll(@ApiIgnore Pageable pageable){
        return roleService.findAll(pageable);
    }

    @DeleteMapping("/deleteById/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id){
        return roleService.deleteById(id);
    }
}
