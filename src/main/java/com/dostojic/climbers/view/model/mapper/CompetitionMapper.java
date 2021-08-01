/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model.mapper;

import com.dostojic.climbers.common.dto.CompetitionDto;
import com.dostojic.climbers.common.dto.RegistrationFeeDto;
import com.dostojic.climbers.common.dto.RouteDto;
import com.dostojic.climbers.view.model.Competition;
import com.dostojic.climbers.view.model.RegistrationFee;
import com.dostojic.climbers.view.model.Route;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Dejan.Ostojic
 */
@Mapper
public interface CompetitionMapper {
    
    public static CompetitionMapper INSTANCE = Mappers.getMapper(CompetitionMapper.class);

    Competition fromDto(CompetitionDto competitionDto);
    CompetitionDto toDto(Competition competition);

    List<Competition> toCompetitions(List<CompetitionDto> competitionDtoList);
    
    Route fromDto(RouteDto routeDto);
    RouteDto toDto(Route route);
    
    RegistrationFee fromDto(RegistrationFeeDto registrationFeeDto);
    RegistrationFeeDto toDto(RegistrationFee registrationFee);


}
