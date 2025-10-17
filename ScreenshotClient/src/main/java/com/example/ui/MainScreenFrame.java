package com.example.ui;

import com.example.api.ScreenshotClient;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenFrame extends JFrame {
    private final ScreenshotClient client;
    private final JLabel imageLabel;
    private final Timer timer;

    public MainScreenFrame(ScreenshotClient client) {
        this.client = client;
        setTitle("Screenshot Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel("Загрузка скриншота...");
        add(imageLabel);

        //обновлять каждые 1000 мс (1 секунда)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAndDisplayScreenshot();
            }
        });
        pack();
        setVisible(true);
        timer.start();
    }

    private void loadAndDisplayScreenshot() {
        try {
            byte[] imageData = client.getScreenshot();
            ImageIcon icon = new ImageIcon(imageData);
            imageLabel.setIcon(icon);
            pack();
        } catch (SecurityException e) {
            imageLabel.setText("Ошибка авторизации: " + e.getMessage());
            timer.stop();
        } catch (Exception e) {
            imageLabel.setText("Ошибка загрузки: " + e.getMessage());
            timer.stop();
        }
    }
}