package rutimur.decryptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DecryptorGUI extends JFrame {
    public DecryptorGUI() {
        setTitle("Caesar Decryptor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 10, 10));
        JButton encryptBtn = new JButton("Зашифровать текст");
        JButton decryptBtn = new JButton("Расшифровать текст");
        JButton breakBtn = new JButton("Взломать русский текст");
        JButton exitBtn = new JButton("Выход");
        menuPanel.add(encryptBtn);
        menuPanel.add(decryptBtn);
        menuPanel.add(breakBtn);
        menuPanel.add(exitBtn);
        add(menuPanel, BorderLayout.CENTER);

        encryptBtn.addActionListener(e -> showEncryptPanel());
        decryptBtn.addActionListener(e -> showDecryptPanel());
        breakBtn.addActionListener(e -> showBreakPanel());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void showEncryptPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextArea inputArea = new JTextArea(5, 30);
        JTextField keyField = new JTextField(5);
        JButton encryptBtn = new JButton("Зашифровать");
        JTextArea resultArea = new JTextArea(3, 30);
        resultArea.setEditable(false);
        resultArea.setForeground(Color.BLACK);
        panel.add(new JLabel("Введите текст для шифрования:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(new JLabel("Ключ:"));
        south.add(keyField);
        south.add(encryptBtn);
        panel.add(south, BorderLayout.SOUTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.EAST);

        JFrame frame = new JFrame("Шифрование");
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        encryptBtn.addActionListener((ActionEvent e) -> {
            String input = inputArea.getText();
            if (!isValidInput(input)) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: текст должен содержать только буквы кириллицы или латиницы.");
                return;
            }
            String clean = TextPreprocessor.preprocess(input);
            if (clean.isEmpty()) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: текст не содержит букв.");
                return;
            }
            int key;
            try {
                key = Integer.parseInt(keyField.getText().trim());
            } catch (NumberFormatException ex) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: введите целое число для ключа.");
                return;
            }
            boolean cyrillic = isCyrillic(clean);
            int normKey = cyrillic ? KeyNormalizer.normalizeCyrillicKey(key) : KeyNormalizer.normalizeLatinKey(key);
            String cipher = CaesarCipher.encrypt(clean, normKey);
            String formatted = TextFormatter.formatByFive(cipher);
            resultArea.setForeground(Color.BLACK);
            resultArea.setText(formatted);
        });
    }

    private void showDecryptPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextArea inputArea = new JTextArea(5, 30);
        JTextField keyField = new JTextField(5);
        JButton decryptBtn = new JButton("Расшифровать");
        JTextArea resultArea = new JTextArea(3, 30);
        resultArea.setEditable(false);
        resultArea.setForeground(Color.BLACK);
        panel.add(new JLabel("Введите текст для расшифровки:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(new JLabel("Ключ:"));
        south.add(keyField);
        south.add(decryptBtn);
        panel.add(south, BorderLayout.SOUTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.EAST);

        JFrame frame = new JFrame("Дешифрование");
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        decryptBtn.addActionListener((ActionEvent e) -> {
            String input = inputArea.getText();
            if (!isValidInput(input)) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: текст должен содержать только буквы кириллицы или латиницы.");
                return;
            }
            String clean = TextPreprocessor.preprocess(input);
            if (clean.isEmpty()) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: текст не содержит букв.");
                return;
            }
            int key;
            try {
                key = Integer.parseInt(keyField.getText().trim());
            } catch (NumberFormatException ex) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: введите целое число для ключа.");
                return;
            }
            boolean cyrillic = isCyrillic(clean);
            int normKey = cyrillic ? KeyNormalizer.normalizeCyrillicKey(key) : KeyNormalizer.normalizeLatinKey(key);
            String plain = CaesarCipher.decrypt(clean, normKey);
            String formatted = TextFormatter.formatByFive(plain);
            resultArea.setForeground(Color.BLACK);
            resultArea.setText(formatted);
        });
    }

    private void showBreakPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextArea inputArea = new JTextArea(5, 30);
        JButton breakBtn = new JButton("Взломать");
        JTextArea resultArea = new JTextArea(3, 30);
        resultArea.setEditable(false);
        resultArea.setForeground(Color.BLACK);
        panel.add(new JLabel("Введите зашифрованный русский текст:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(breakBtn);
        panel.add(south, BorderLayout.SOUTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.EAST);

        JFrame frame = new JFrame("Взлом шифра");
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        breakBtn.addActionListener((ActionEvent e) -> {
            String input = inputArea.getText();
            if (!isValidInput(input)) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: текст должен содержать только буквы кириллицы или латиницы.");
                return;
            }
            String clean = TextPreprocessor.preprocess(input);
            if (clean.isEmpty()) {
                resultArea.setForeground(Color.RED);
                resultArea.setText("Ошибка: текст не содержит букв.");
                return;
            }
            CaesarBreaker.BreakResult result = CaesarBreaker.breakRussianCaesar(clean);
            String formatted = TextFormatter.formatByFive(result.plainText);
            resultArea.setForeground(Color.BLACK);
            resultArea.setText("Текст: " + formatted + "\nКлюч: " + result.key);
        });
    }

    private boolean isCyrillic(String text) {
        for (char c : text.toCharArray()) {
            if (c >= 'а' && c <= 'я') return true;
            if (c >= 'a' && c <= 'z') return false;
        }
        return false;
    }

    // Проверка: только буквы кириллицы или латиницы, пробелы и знаки препинания запрещены
    private boolean isValidInput(String text) {
        if (text == null || text.isEmpty()) return false;
        String replaced = text.replace('Ё', 'Е').replace('ё', 'е');
        for (char c : replaced.toCharArray()) {
            if (!((c >= 'а' && c <= 'я') || (c >= 'А' && c <= 'Я') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == 'ё' || c == 'Ё' || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DecryptorGUI().setVisible(true));
    }
} 