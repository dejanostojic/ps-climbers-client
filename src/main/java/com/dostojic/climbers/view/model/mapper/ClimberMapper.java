/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model.mapper;

import com.dostojic.climbers.common.dto.ClimberDto;
import com.dostojic.climbers.view.model.Climber;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author planina
 */
@Mapper
public interface ClimberMapper {
    
    public static ClimberMapper INSTANCE = Mappers.getMapper(ClimberMapper.class);

    ClimberDto toDto(Climber climber);
    
    Climber fromDto(ClimberDto dto);
    
    List<Climber> fromDto(Collection<ClimberDto> dto);
    
    
}
