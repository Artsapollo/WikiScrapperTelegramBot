import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private Document doc;
    private String searchWord;

    public Article(String searchWord) {
        this.searchWord = searchWord;
        connect();
    }

    public void connect() {
        try {
            doc = Jsoup.connect("https://ru.wikipedia.org/wiki/" + searchWord).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getArticle() {
        Elements base = doc.getElementsByAttributeValue("class", "mw-parser-output").select("p");
        if (base.size() == 1) {
            Elements extra = doc.getElementsByAttributeValue("class", "mw-parser-output").select("ul");
            return getArticleText(extra);
        }
        return getArticleText(base);
    }

    public List<String> getArticleText(Elements elements) {
        List<String> article = new ArrayList<>();
        int count = 0;

        if (elements.size() > 1) {
            for (int i = 0; i < elements.size(); i++) {
                if (count != 3) {
                    article.add(elements.get(i).text());
                    count++;
                }
            }
        } else {
            article.add(elements.text());
        }
        return article;
    }
}
