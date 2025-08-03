package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDto {
    private Long id;
    private String nom;
    private String description;
    private  String adresse;
    private String createBy;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
