package rutimur.decryptor;

public class TextPreprocessor {
    /**
     * Обрабатывает текст по правилам:
     * 1. Заменяет Ё/ё на Е/е
     * 2. Удаляет все небуквенные символы (оставляет только кириллицу и латиницу)
     * 3. Приводит к нижнему регистру
     */
    public static String preprocess(String input) {
        if (input == null) return "";
        // Замена Ё/ё на Е/е
        String replaced = input.replace('Ё', 'Е').replace('ё', 'е');
        // Оставляем только буквы кириллицы и латиницы
        StringBuilder sb = new StringBuilder();
        for (char c : replaced.toCharArray()) {
            if ((c >= 'а' && c <= 'я') || (c >= 'a' && c <= 'z') || (c >= 'А' && c <= 'Я') || (c >= 'A' && c <= 'Z')) {
                sb.append(c);
            }
        }
        // Приводим к нижнему регистру
        return sb.toString().toLowerCase();
    }
} 