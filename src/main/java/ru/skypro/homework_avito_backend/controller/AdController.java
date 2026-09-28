package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.AdsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;

import java.util.Collections;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Объявления", description = "Управление объявлениями на площадке")
public class AdController {

    private static final Integer DEFAULT_MOCK_ID = 1;
    private static final Integer EMPTY_COUNT = 0;

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto ads = new AdsDto();
        ads.setCount(EMPTY_COUNT);
        ads.setResults(Collections.emptyList());
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление объявления")
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") CreateOrUpdateAdDto properties,
                                       @RequestPart("image") MultipartFile image) {
        AdDto createdAd = new AdDto();
        createdAd.setPk(DEFAULT_MOCK_ID);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable Integer id) {
        ExtendedAdDto extendedAd = new ExtendedAdDto();
        extendedAd.setPk(id);
        return ResponseEntity.ok(extendedAd);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении")
    public ResponseEntity<AdDto> updateAds(@PathVariable Integer id,
                                           @RequestBody CreateOrUpdateAdDto properties) {
        AdDto updatedAd = new AdDto();
        updatedAd.setPk(id);
        return ResponseEntity.ok(updatedAd);
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public ResponseEntity<AdsDto> getAdsMe() {
        AdsDto myAds = new AdsDto();
        myAds.setCount(EMPTY_COUNT);
        myAds.setResults(Collections.emptyList());
        return ResponseEntity.ok(myAds);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление картинки объявления")
    public ResponseEntity<String[]> updateImage(@PathVariable Integer id,
                                                @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(new String[]{});
    }
}

