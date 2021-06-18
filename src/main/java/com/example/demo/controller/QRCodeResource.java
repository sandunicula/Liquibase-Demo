package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QRCodeResource {

    private static final String QR_CODE_IMAGE_PATH = "src/main/resources/qr-codes";

    @GetMapping(value = "/generateAndDownloadQRCode/{certificateId}/{width}/{height}")
    public void download(@PathVariable("certificateId") String certificateId,
                         @PathVariable("width") Integer width,
                         @PathVariable("height") Integer height) throws Exception {
        QRCodeGenerator.createQRImage(certificateId, width, height, QR_CODE_IMAGE_PATH);
    }

    @GetMapping(value = "/generateQRCode/{certificateId}/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable("certificateId") String certificateId,
                                                 @PathVariable("width") Integer width,
                                                 @PathVariable("height") Integer height) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(certificateId, width, height));
    }
}
