/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model.mapper;

import com.dostojic.climbers.common.dto.UserDto;
import com.dostojic.climbers.view.model.User;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author planina
 */
@Mapper
public interface UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User fromDto(UserDto dto);
    List<User> fromDto(Collection<UserDto> dto);
}
