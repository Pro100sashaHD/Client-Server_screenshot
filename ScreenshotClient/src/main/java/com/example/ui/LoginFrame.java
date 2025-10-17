package com.example.ui;

import com.example.api.ScreenshotClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private final JTextField ipField = new JTextField("localhost", 20);
    private final JTextField usernameField = new JTextField("client", 20);
    private final JPasswordField passwordField = new JPasswordField("password123", 20);
    private final JButton loginButton = new JButton("Войти");
    private final JLabel statusLabel = new JLabel("Введите данные для входа:");

    public LoginFrame() {
        super("Вход в Screenshot Client");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("IP сервера:"));
        inputPanel.add(ipField);

        inputPanel.add(new JLabel("Логин:"));
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Пароль:"));
        inputPanel.add(passwordField);

        inputPanel.add(new JLabel(""));
        inputPanel.add(loginButton);

        add(inputPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Обработчик кнопки "Войти"
        loginButton.addActionListener(new LoginActionListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ip = ipField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (ip.isEmpty() || username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("<html><font color='red'>Заполните все поля.</font></html>");
                return;
            }

            statusLabel.setText("Попытка подключения...");
            loginButton.setEnabled(false);

            SwingWorker<ScreenshotClient, Void> worker = new SwingWorker<>() {
                @Override
                protected ScreenshotClient doInBackground() throws Exception {
                    ScreenshotClient client = new ScreenshotClient(ip, username, password);
                    client.getScreenshot();
                    return client;
                }
                @Override
                protected void done() {
                    loginButton.setEnabled(true);
                    try {
                        ScreenshotClient client = get();
                        dispose();
                        new MainScreenFrame(client);

                    } catch (Exception ex) {
                        String errorMsg = "Ошибка подключения: " + ex.getCause().getMessage();
                        if (ex.getCause() instanceof SecurityException) {
                            errorMsg = "Ошибка авторизации: Неверный логин или пароль.";
                        } else if (ex.getCause().getMessage().contains("refused")) {
                            errorMsg = "Соединение отклонено. Сервер недоступен.";
                        }
                        statusLabel.setText("<html><font color='red'>" + errorMsg + "</font></html>");
                    }
                }
            };
            worker.execute();
        }
    }
}