package com.pknu.caloriepay.domain.file.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "image_file")
public class ImageFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_file_id")
    private Long id;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    private ImageCategory imageCategory;

    @Enumerated(EnumType.STRING)
    private FileStatus fileStatus;

    @Builder
    public ImageFile(String imageUrl, ImageCategory imageCategory, FileStatus fileStatus) {
        this.imageUrl = imageUrl;
        this.imageCategory = imageCategory;
        this.fileStatus = fileStatus;
        this.uploadedAt = LocalDateTime.now();
    }


}
