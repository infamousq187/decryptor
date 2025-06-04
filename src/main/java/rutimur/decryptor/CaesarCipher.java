package rutimur.decryptor;

public class CaesarCipher {
    private static final String CYRILLIC = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String CYRILLIC_NO_E = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String LATIN = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Шифрует текст шифром Цезаря посимвольно, не завися от алфавита всего текста
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
     * Дешифрует текст шифром Цезаря посимвольно, не завися от алфавита всего текста
     */
    public static String decrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 'а' && c <= 'я') {
                int offset = c - 'а';
                int newOffset = (offset - key + 32) % 32;
                result.append((char) ('а' + newOffset));
            } else if (c >= 'a' && c <= 'z') {
                int offset = c - 'a';
                int newOffset = (offset - key + 26) % 26;
                result.append((char) ('a' + newOffset));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
} 