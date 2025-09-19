package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor

@Getter
@Setter
@Entity
@Table(name = "documentaire")
public class Documentaire {

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
    private DocumentaireType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    @JsonBackReference
    private Projet projet;

    // Enumerate pou diferan kalite dokiman
    public enum DocumentaireType {
        AVANT,      // Faktì
        PENDANT,        // Devis
        APRES,      // Kontra
        TEMOIGNAGE,      // Rapò
        AUTRE;        // Lòt kalite dokiman
        
        /**
         * Retounen yon tab valè enum yo kòm chèn karaktè
         */
        public static String[] getNames() {
            DocumentaireType[] types = values();
            String[] names = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                names[i] = types[i].name();
            }
            return names;
        }
    }

    // Enumerate pou diferan kalite foto
    public enum PhotoType {
        AVANT,       // Foto avan travay la kòmanse
        PENDANT,     // Foto pandan travay la ap fèt
        APRES,       // Foto apre travay la fini
        TEMOIGNAGE   // Temwayaj sou travay la
    }

    // Konstriktè, getter, setter, ak lòt metòd

    public Documentaire() {
        this.uploadDate = LocalDateTime.now();
    }

    // Getters ak Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrlDocumentaire() {
        return urlDocumentaire;
    }

    public void setUrlDocumentaire(String urlDocumentaire) {
        this.urlDocumentaire = urlDocumentaire;
    }

    public DocumentaireType getType() {
        return type;
    }

    public void setType(DocumentaireType type) {
        this.type = type;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
