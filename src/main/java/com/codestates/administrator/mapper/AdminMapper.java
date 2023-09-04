package com.codestates.administrator.mapper;

import com.codestates.administrator.dto.AdminPostDto;
import com.codestates.administrator.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin adminPostDtoToAdmin(AdminPostDto adminPostDto);
}
