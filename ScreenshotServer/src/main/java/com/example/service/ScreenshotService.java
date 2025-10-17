package com.example.service;

import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ScreenshotService {

    public byte[] takeScreenshot() throws Exception {
        // 1. Определение размеров всего экрана
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        // 2. Использование класса Robot для захвата экрана
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(capture, "png", bos);

        return bos.toByteArray();
    }
}