package com.natha.dev.Dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommuneDto {
    private Long id;
    private String nom;
    private Long departementId;
    private List<SectionCommunaleDto> sectionCommunales;
}