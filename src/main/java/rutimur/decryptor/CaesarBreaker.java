package rutimur.decryptor;

public class CaesarBreaker {
    // Частоты букв русского языка (без ё), в порядке: а,б,в,г,д,е,ж,з,и,й,к,л,м,н,о,п,р,с,т,у,ф,х,ц,ч,ш,щ,ъ,ы,ь,э,ю,я
    private static final double[] RUS_FREQ = {
        0.0801, 0.0159, 0.0454, 0.0170, 0.0298, 0.0845, 0.0094, 0.0165, 0.0735, 0.0121, 0.0349, 0.0440, 0.0321, 0.0670, 0.1097, 0.0281, 0.0473, 0.0547, 0.0632, 0.0262, 0.0026, 0.0097, 0.0048, 0.0144, 0.0073, 0.0036, 0.0004, 0.0189, 0.0174, 0.0032, 0.0064, 0.0190
    };
    private static final String RUS_ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";

    public static BreakResult breakRussianCaesar(String cipherText) {
        cipherText = cipherText.toLowerCase();
        int bestKey = 0;
        double minScore = Double.MAX_VALUE;
        String bestPlain = cipherText;
        for (int key = 0; key < 33; key++) {
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
        StringBuilder sb = new StringBuilder();
        int n = RUS_ALPHABET.length();
        for (char c : text.toCharArray()) {
            int idx = RUS_ALPHABET.indexOf(c);
            if (idx == -1) {
                sb.append(c);
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