package ru.skypro.homework_avito_backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {

    private ImageServiceImpl imageService;

    @TempDir
    Path sharedTempDir;

    @BeforeEach
    void setUp() {
        imageService = new ImageServiceImpl();
        ReflectionTestUtils.setField(imageService, "baseImagesDir", sharedTempDir.toString());
    }

    @Test
    void uploadImage_Success() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "avatar.png", "image/png", "test image bytes".getBytes()
        );

        String resultFileName = imageService.uploadImage(mockFile, "users");

        assertNotNull(resultFileName);
        assertTrue(resultFileName.endsWith(".png"));

        Path expectedFilePath = sharedTempDir.resolve("users").resolve(resultFileName);
        assertTrue(Files.exists(expectedFilePath));
        assertArrayEquals("test image bytes".getBytes(), Files.readAllBytes(expectedFilePath));
    }

    @Test
    void uploadImage_ThrowsException_WhenFileIsEmpty() {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "image", "empty.jpg", "image/jpeg", new byte[0]
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                imageService.uploadImage(emptyFile, "ads")
        );
        assertEquals("Файл картинки не может быть пустым", exception.getMessage());
    }

    @Test
    void deleteImage_Success() throws IOException {
        Path usersDir = sharedTempDir.resolve("users");
        Files.createDirectories(usersDir);
        Path testFile = usersDir.resolve("test-uuid.jpg");
        Files.write(testFile, "bytes".getBytes());

        assertTrue(Files.exists(testFile));

        imageService.deleteImage("/users/test-uuid.jpg");

        assertFalse(Files.exists(testFile));
    }
}

