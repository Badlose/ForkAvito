//package ru.skypro.homework.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.multipart.MultipartFile;
//import ru.skypro.homework.controller.TestObjectsForControllerTestStorage.AdsControllerTestResources;
//import ru.skypro.homework.dto.give.Ad;
//import ru.skypro.homework.dto.give.Ads;
//import ru.skypro.homework.dto.accept.CreateOrUpdateAd;
//import ru.skypro.homework.dto.give.ExtendedAd;
//import ru.skypro.homework.service.AdsService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AdsControllerControllerTests extends AdsControllerTestResources {
//
//    @Mock
//    private AdsService adsService;
//    @InjectMocks
//    private AdsController controller;
//    private static final Integer id = 1;
//
//    @Test
//    void shouldGetAllAds() {
//
//        when(adsService.getAllAds()).thenReturn(getTestAds());
//
//        Ads actualAds = controller.getAllAds().getBody();
//
//        assertEquals(getTestAds(), actualAds);
//        verify(adsService, times(1)).getAllAds();
//    }
//
//    @Test
//    void shouldAddNewAd() {
//        CreateOrUpdateAd createOrUpdateAd = getTestCreatedAd();
//        Ad ad = getTestAd();
//        MultipartFile multipartFile = getTestMultipartFile();
//
//        when(adsService.createNewAd(createOrUpdateAd, multipartFile)).thenReturn(ad);
//
//        Ad actualAd = controller.addAd(createOrUpdateAd, multipartFile).getBody();
//
//        assertEquals(ad, actualAd);
//        verify(adsService, times(1)).createNewAd(createOrUpdateAd, multipartFile);
//    }
//
//    @Test
//    void shouldGetAdById() {
//
//        ExtendedAd extendedAd = getTestExtendedAd();
//
//        when(adsService.getAdById(id)).thenReturn(extendedAd);
//
//        ExtendedAd actualExtendedAd = controller.getAds(id).getBody();
//
//        assertEquals(extendedAd, actualExtendedAd);
//        verify(adsService, times(1)).getAdById(id);
//    }
//
//    @Test
//    void shouldRemoveAd() {
//
//        ResponseEntity<?> expectedResponseEntity = ResponseEntity.noContent().build();
//
//        doNothing().when(adsService).removeAd(id);
//
//        ResponseEntity<?> actualResponseEntity = controller.removeAd(id);
//
//
//        assertEquals(expectedResponseEntity, actualResponseEntity);
//        verify(adsService, times(1)).removeAd(id);
//    }
//
//    @Test
//    void shouldUpdateAds() {
//
//        CreateOrUpdateAd updateAd = getTestCreatedAd();
//        Ad expectedAd = getTestAd();
//
//        when(adsService.updateAd(id, updateAd)).thenReturn(expectedAd);
//
//        Ad actualAd = controller.updateAds(id, updateAd).getBody();
//
//        assertEquals(expectedAd, actualAd);
//        verify(adsService, times(1)).updateAd(id, updateAd);
//    }
//
//    @Test
//    void shouldGetAdsMe() {
//        Ads expectedAds = getTestAds();
//
//        when(adsService.getAdsMe()).thenReturn(expectedAds);
//
//        Ads actualAds = controller.getAdsMe().getBody();
//
//        assertEquals(expectedAds, actualAds);
//        verify(adsService, times(1)).getAdsMe();
//    }
//
//    @Test
//    void shouldUpdateImage() {
//
//        MultipartFile expectedMultipartFile = getTestMultipartFile();
//
//        when(adsService.updateImage(id, expectedMultipartFile)).thenReturn(expectedMultipartFile);
//
//        MultipartFile actualMultipartFile = controller.updateImage(id, expectedMultipartFile).getBody();
//
//        assertEquals(expectedMultipartFile, actualMultipartFile);
//        verify(adsService, times(1)).updateImage(id, expectedMultipartFile);
//    }
//
//}
