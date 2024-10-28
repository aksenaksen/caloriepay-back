package com.pknu.caloriepay.domain.file.application;

import com.pknu.caloriepay.domain.file.domain.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final FileStorageService fileStorageService;
    private final ImageFileRepository imageFileRepository;


}
