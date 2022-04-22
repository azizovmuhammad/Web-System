package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.dto.RoleDto;
import com.example.registrationsystem.dto.response.Response;
import com.example.registrationsystem.entity.Order;
import com.example.registrationsystem.entity.Role;
import com.example.registrationsystem.repository.RoleRepository;
import com.example.registrationsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.registrationsystem.service.impl.ProductServiceImpl.response;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public HttpEntity<?> createRole(RoleDto roleDto) {
        Role role = new Role(
                roleDto.getName()
        );
        roleRepository.save(role);
        Response response = new Response(true, "Successfully created", role);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> updateRole(RoleDto roleDto, Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Response response;
        if (optionalRole.isPresent()){
            Role role = optionalRole.get();
            if (!role.getName().equals(roleDto.getName())){
                role.setName(roleDto.getName());
            }
            roleRepository.save(role);
            response = new Response(true,"Successfully updated", role);
        }
        else {
            response = new Response(false, "Role Not Found With id " + id);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> findById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()){
            Role role = optionalRole.get();
            response = new Response(true, "Product", role);
        }
        else {
            response = new Response(false, "Order Not Found With Id [ " + id + " ]");
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> findAll(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        List<Role> roleList = roles.getContent();
        Response response = new Response(true, "Role List", roleList);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        Response response;
        if (role.isPresent()){
            roleRepository.deleteById(id);
            response = new Response(true, "Successfully deleted");
        }
        else {
            response = new Response(false, "Role Not Found With Id " + id);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
