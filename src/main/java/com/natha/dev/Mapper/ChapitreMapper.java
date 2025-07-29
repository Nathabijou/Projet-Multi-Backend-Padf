package com.natha.dev.Mapper;

import com.natha.dev.Dto.ChapitreDto;
import com.natha.dev.Model.Chapitre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChapitreMapper {
    
    ChapitreMapper INSTANCE = Mappers.getMapper(ChapitreMapper.class);
    
    @Mappings({
        @Mapping(target = "formationId", source = "formation.idFormation")
    })
    ChapitreDto toDto(Chapitre chapitre);
    
    @Mapping(target = "formation", ignore = true)
    @Mapping(target = "modules", ignore = true)
    Chapitre toEntity(ChapitreDto chapitreDto);
}
