package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartierDto {
    private Long id;
    private String name;
    private Long sectionCommunaleId;
    private List<ProjetDto> projets;
}