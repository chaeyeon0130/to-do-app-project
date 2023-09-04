package com.codestates.administrator.service;

import com.codestates.administrator.entity.Admin;
import com.codestates.administrator.repository.AdminRepository;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin createAdmin(Admin admin) {
        // 이미 등록된 이름인지 확인 -> 관리자의 이름이 중복이면 안 됨
        verifyExistsName(admin.getName());

        return adminRepository.save(admin);
    }

    public Admin findVerifiedAdmin(long adminId) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        Admin findAdmin = optionalAdmin.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ADMIN_NOT_FOUND));

        return findAdmin;
    }

    private void verifyExistsName(String name) {
        Optional<Admin> admin = adminRepository.findByName(name);
        if (admin.isPresent())
            throw new BusinessLogicException(ExceptionCode.ADMIN_EXISTS);
    }
}
