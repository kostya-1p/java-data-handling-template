package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.repository.SimpleFileRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        SimpleFileRepository fileRep = new SimpleFileRepository();
        StringBuilder content = new StringBuilder(fileRep.readFileFromResources("sensitive_data.txt"));

        Pattern pat = Pattern.compile("\\s*\\d{4}\\s+(\\d{4}\\s+\\d{4})\\s+\\d{4}");
        Matcher match = pat.matcher(content);
        while (match.find()) {
            content.replace(match.start(1), match.end(1), "**** ****");
        }

        return content.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        SimpleFileRepository fileRep = new SimpleFileRepository();
        StringBuilder content = new StringBuilder(fileRep.readFileFromResources("sensitive_data.txt"));
        Pattern pat = Pattern.compile("(\\$\\{p.*[}]).+(\\$\\{b.*[}])");
        Matcher match = pat.matcher(content);

        while (match.find()) {
            content.replace(match.start(2), match.end(2), Integer.toString((int)balance));
            content.replace(match.start(1), match.end(1), Integer.toString((int)paymentAmount));
        }
        return content.toString();
    }
}
