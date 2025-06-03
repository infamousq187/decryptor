package rutimur.decryptor;

public class TextPreprocessor {
    /**
     * Обрабатывает текст по правилам:
     * 1. Заменяет Ё/ё на Е/е
     * 2. Удаляет все небуквенные символы (оставляет только кириллицу и латиницу)
     * 3. Приводит к нижнему регистру
     */
    public static String preprocess(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            if (c == 'ё') {
                result.append('е');
            } else if ((c >= 'а' && c <= 'я') || (c >= 'a' && c <= 'z')) {
                result.append(c);
            }
        }
        return result.toString();
    }
} 