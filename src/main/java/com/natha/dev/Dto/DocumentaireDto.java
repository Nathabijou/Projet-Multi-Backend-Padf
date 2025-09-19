package com.natha.dev.Dto;

import com.natha.dev.Model.Documentaire;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DocumentaireDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;  // URL Firebase Storage pou foto a

    @Column(name = "upload_date", nullable = false, updatable = false)
    private LocalDateTime uploadDate;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "url_documentaire")
    private String urlDocumentaire;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentaire_type", nullable = false)
    private Documentaire.DocumentaireType type;
}
