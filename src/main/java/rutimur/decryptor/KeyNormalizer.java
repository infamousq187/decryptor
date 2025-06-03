package rutimur.decryptor;

public class KeyNormalizer {
    /**
     * Нормализует ключ для кириллицы в диапазон [0;32]
     */
    public static int normalizeCyrillicKey(int key) {
        int mod = key % 33;
        return mod < 0 ? mod + 33 : mod;
    }

    /**
     * Нормализует ключ для латиницы в диапазон [0;26]
     */
    public static int normalizeLatinKey(int key) {
        int mod = key % 26;
        return mod < 0 ? mod + 26 : mod;
    }

    public static int normalizeKey(int key) {
        // Для кириллицы (32 буквы)
        if (key >= -32 && key <= 32) {
            return (key + 32) % 32;
        }
        // Для латиницы (26 букв)
        return (key + 26) % 26;
    }
} 