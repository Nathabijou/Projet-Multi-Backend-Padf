package com.natha.dev.config;

import com.natha.dev.Dto.EtatAvancementDto;
import com.natha.dev.Dto.PhotoSousEtatDto;
import com.natha.dev.Dto.SousEtatAvancementDto;
import com.natha.dev.Model.EtatAvancement;
import com.natha.dev.Model.PhotoSousEtat;
import com.natha.dev.Model.SousEtatAvancement;
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Base configuration
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setPropertyCondition(context -> context.getSource() != null);
                
        // Add converter for PersistentBag to List
        Converter<Collection<?>, List<?>> collectionToList = ctx -> {
            if (ctx.getSource() == null) {
                return null;
            }
            if (ctx.getSource() instanceof PersistentBag) {
                return new ArrayList<>((Collection<?>) ctx.getSource());
            }
            return new ArrayList<>((Collection<?>) ctx.getSource());
        };
        
        modelMapper.addConverter(collectionToList);

        // Configure EtatAvancement to EtatAvancementDto mapping
        modelMapper.createTypeMap(EtatAvancement.class, EtatAvancementDto.class)
                .addMappings(mapper -> {
                    mapper.map(EtatAvancement::getId, EtatAvancementDto::setId);
                    mapper.map(EtatAvancement::getNom, EtatAvancementDto::setNom);
                    mapper.map(EtatAvancement::getDescription, EtatAvancementDto::setDescription);
                    mapper.map(EtatAvancement::getPourcentageTotal, EtatAvancementDto::setPourcentageTotal);
                    mapper.map(EtatAvancement::getPourcentageRealise, EtatAvancementDto::setPourcentageRealise);
                    
                    // Map projet ID
                    mapper.using((MappingContext<EtatAvancement, String> context) -> 
                        context.getSource() != null && context.getSource().getProjet() != null 
                            ? context.getSource().getProjet().getIdProjet() 
                            : null
                    ).map(EtatAvancement::getProjet, EtatAvancementDto::setProjetId);
                    
                    // Map sousEtats collection
                    mapper.using((MappingContext<EtatAvancement, List<SousEtatAvancementDto>> context) -> {
                        if (context.getSource() == null || context.getSource().getSousEtats() == null) {
                            return null;
                        }
                        Collection<SousEtatAvancement> sousEtats = context.getSource().getSousEtats();
                        return sousEtats.stream()
                            .map(se -> modelMapper.map(se, SousEtatAvancementDto.class))
                            .collect(Collectors.toList());
                    }).map(EtatAvancement::getSousEtats, EtatAvancementDto::setSousEtats);
                });

        // Configure SousEtatAvancement to SousEtatAvancementDto mapping
        modelMapper.createTypeMap(SousEtatAvancement.class, SousEtatAvancementDto.class)
                .addMappings(mapper -> {
                    mapper.map(SousEtatAvancement::getId, SousEtatAvancementDto::setId);
                    mapper.map(SousEtatAvancement::getNom, SousEtatAvancementDto::setLibelle);
                    mapper.map(SousEtatAvancement::getDescription, SousEtatAvancementDto::setDescription);
                    mapper.map(SousEtatAvancement::getPourcentageRealise, SousEtatAvancementDto::setPourcentageRealise);
                    mapper.map(SousEtatAvancement::getPoids, SousEtatAvancementDto::setPoids);
                    
                    // Map etatAvancementId
                    mapper.using((MappingContext<SousEtatAvancement, String> context) -> 
                        context.getSource() != null && context.getSource().getEtatAvancement() != null 
                            ? context.getSource().getEtatAvancement().getId() 
                            : null
                    ).map(SousEtatAvancement::getEtatAvancement, SousEtatAvancementDto::setEtatAvancementId);
                    
                    // Map photos collection
                    mapper.using((MappingContext<SousEtatAvancement, List<PhotoSousEtatDto>> context) -> {
                        if (context.getSource() == null || context.getSource().getPhotos() == null) {
                            return null;
                        }
                        Collection<PhotoSousEtat> photos = context.getSource().getPhotos();
                        return photos.stream()
                            .map(photo -> modelMapper.map(photo, PhotoSousEtatDto.class))
                            .collect(Collectors.toList());
                    }).map(SousEtatAvancement::getPhotos, SousEtatAvancementDto::setPhotos);
                });

        // Configure PhotoSousEtat to PhotoSousEtatDto mapping
        modelMapper.createTypeMap(PhotoSousEtat.class, PhotoSousEtatDto.class)
                .addMappings(mapper -> {
                    mapper.map(PhotoSousEtat::getId, PhotoSousEtatDto::setId);
                    mapper.map(PhotoSousEtat::getNomFichier, PhotoSousEtatDto::setNomFichier);
                    mapper.map(PhotoSousEtat::getCheminFichier, PhotoSousEtatDto::setCheminFichier);
                    mapper.map(PhotoSousEtat::getDescription, PhotoSousEtatDto::setDescription);
                    mapper.map(PhotoSousEtat::getDateAjout, PhotoSousEtatDto::setDateAjout);
                    mapper.map(PhotoSousEtat::isEstPhotoPrincipale, PhotoSousEtatDto::setEstPhotoPrincipale);
                });

        return modelMapper;
    }
}
