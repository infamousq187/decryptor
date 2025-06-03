package rutimur.decryptor;

public class CaesarCipher {
    private static final String CYRILLIC = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String CYRILLIC_NO_E = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String LATIN = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Шифрует текст шифром Цезаря
     */
    public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 'а' && c <= 'я') {
                int offset = c - 'а';
                int newOffset = (offset + key + 32) % 32;
                result.append((char) ('а' + newOffset));
            } else if (c >= 'a' && c <= 'z') {
                int offset = c - 'a';
                int newOffset = (offset + key + 26) % 26;
                result.append((char) ('a' + newOffset));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Дешифрует текст шифром Цезаря
     */
    public static String decrypt(String text, int key) {
        return encrypt(text, -key);
    }

    private static String shift(String text, int key, boolean encrypt) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        String alphabet = detectAlphabet(text);
        int n = alphabet.length();
        for (char c : text.toCharArray()) {
            int idx = alphabet.indexOf(c);
            if (idx == -1) {
                sb.append(c); // если вдруг встретился неалфавитный символ
            } else {
                int shift = encrypt ? (idx + key) % n : (idx - key + n) % n;
                sb.append(alphabet.charAt(shift));
            }
        }
        return sb.toString();
    }

    // Определяет алфавит по первой букве текста
    private static String detectAlphabet(String text) {
        for (char c : text.toCharArray()) {
            if (CYRILLIC.indexOf(c) != -1) return CYRILLIC;
            if (LATIN.indexOf(c) != -1) return LATIN;
        }
        // По умолчанию латиница
        return LATIN;
    }
} 