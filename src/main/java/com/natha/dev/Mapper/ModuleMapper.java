package com.natha.dev.Mapper;

import com.natha.dev.Dto.ModuleDto;
import com.natha.dev.Model.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    
    ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);
    
    @Mapping(target = "chapitreId", source = "chapitre.idChapitre")
    ModuleDto toDto(Module module);
    
    @Mapping(target = "chapitre", ignore = true) // Anpeche sikilè referans
    Module toEntity(ModuleDto moduleDto);
    
    @Mapping(target = "idModule", ignore = true) // Pa mete ajou ID a
    @Mapping(target = "chapitre", ignore = true) // Anpeche sikilè referans
    void updateModuleFromDto(ModuleDto moduleDto, @MappingTarget Module module);
}
