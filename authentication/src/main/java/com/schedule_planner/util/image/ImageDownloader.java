package com.schedule_planner.util.image;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {

    public byte[] downloadImageAsBytes(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        try (InputStream inputStream = url.openStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        }
    }
}
