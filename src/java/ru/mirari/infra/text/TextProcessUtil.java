package ru.mirari.infra.text;

/**
 * @author alari
 * @since 9/22/12 10:16 PM
 */
public interface TextProcessUtil {
    public String cleanHtml(String unsafe);

    public String markdownToHtml(String text);

    public String htmlToMarkdown(String theHTML);
}
