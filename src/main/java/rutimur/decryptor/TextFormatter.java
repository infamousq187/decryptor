package rutimur.decryptor;

public class TextFormatter {
    /**
     * Делит текст на группы по 5 символов, разделяя их пробелом
     */
    public static String formatByFive(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (char c : text.toCharArray()) {
            if (count > 0 && count % 5 == 0) {
                sb.append(' ');
            }
            sb.append(c);
            count++;
        }
        return sb.toString();
    }
} 