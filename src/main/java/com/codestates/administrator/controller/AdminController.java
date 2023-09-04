package com.codestates.administrator.controller;

import com.codestates.administrator.dto.AdminPostDto;
import com.codestates.administrator.entity.Admin;
import com.codestates.administrator.mapper.AdminMapper;
import com.codestates.administrator.service.AdminService;
import com.codestates.utils.UriCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v11/admins")
@Validated
public class AdminController {
    private final static String ADMIN_DEFAULT_URL = "/v11/admins";
    private final AdminService adminService;
    private final AdminMapper mapper;

    public AdminController(AdminService adminService, AdminMapper mapper) {
        this.adminService = adminService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postAdmin(@Valid @RequestBody AdminPostDto adminPostDto) {
        Admin admin = mapper.adminPostDtoToAdmin(adminPostDto);

        Admin createdAdmin = adminService.createAdmin(admin);
        URI location = UriCreator.createUri(ADMIN_DEFAULT_URL, createdAdmin.getAdminId());

        return ResponseEntity.created(location).build();
    }
}
