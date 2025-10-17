package com.example;

import com.example.ui.LoginFrame;
import javax.swing.SwingUtilities;

public class ScreenshotClientApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}