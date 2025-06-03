package rutimur.decryptor;

public class CaesarBreaker {
    // Частоты букв русского языка (без буквы 'ё')
    private static final double[] RUS_FREQ = {
            0.062,  // А
            0.014,  // Б
            0.038,  // В
            0.013,  // Г
            0.025,  // Д
            0.077,  // Е (включая частоту 'ё')
            0.007,  // Ж
            0.016,  // З
            0.062,  // И
            0.010,  // Й
            0.028,  // К
            0.035,  // Л
            0.026,  // М
            0.053,  // Н
            0.090,  // О
            0.023,  // П
            0.040,  // Р
            0.045,  // С
            0.053,  // Т
            0.021,  // У
            0.002,  // Ф
            0.009,  // Х
            0.003,  // Ц
            0.012,  // Ч
            0.006,  // Ш
            0.003,  // Щ
            0.014,  // Ъ
            0.016,  // Ы
            0.014,  // Ь
            0.003,  // Э
            0.006,  // Ю
            0.018   // Я
    };
    private static final String RUS_ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";

    public static BreakResult breakRussianCaesar(String cipherText) {
        cipherText = cipherText.toLowerCase();
        int bestKey = 0;
        double minScore = Double.MAX_VALUE;
        String bestPlain = cipherText;
        for (int key = 0; key < 32; key++) {
            String plain = decrypt(cipherText, key);
            double score = calcScore(plain);
            if (score < minScore) {
                minScore = score;
                bestKey = key;
                bestPlain = plain;
            }
        }
        return new BreakResult(bestPlain, bestKey);
    }

    private static String decrypt(String text, int key) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int n = RUS_ALPHABET.length();
        for (char c : text.toCharArray()) {
            if (c == 'ё') {
                c = 'е';
            }
            int idx = RUS_ALPHABET.indexOf(c);
            if (idx == -1) {
                sb.append(c); // если вдруг встретился неалфавитный символ
            } else {
                int shift = (idx - key + n) % n;
                sb.append(RUS_ALPHABET.charAt(shift));
            }
        }
        return sb.toString();
    }

    // Метод наименьших квадратов
    private static double calcScore(String text) {
        int[] counts = new int[32];
        int total = 0;
        for (char c : text.toCharArray()) {
            if (c == 'ё') {
                c = 'е';
            }
            int idx = RUS_ALPHABET.indexOf(c);
            if (idx != -1) {
                counts[idx]++;
                total++;
            }
        }
        if (total == 0) return Double.MAX_VALUE;
        double score = 0.0;
        for (int i = 0; i < 32; i++) {
            double freq = (double) counts[i] / total;
            score += Math.pow(freq - RUS_FREQ[i], 2);
        }
        return score;
    }

    public static class BreakResult {
        public final String plainText;
        public final int key;
        public BreakResult(String plainText, int key) {
            this.plainText = plainText;
            this.key = key;
        }
    }
}