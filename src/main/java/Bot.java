import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        Long chat_id = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage().setChatId(chat_id).setText(response(message));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    public String response(String message) throws NullPointerException {
        String answer = "";
        String greetings = "Привет!\n";
        String noProblem = "Мне есть что тебе предложить: \n";

        if (message.toLowerCase().contains("/привет")) {
            answer += greetings;
        } else if (message.toLowerCase().contains("/нужно расслабиться")) {
            answer += noProblem;
            for (String s : menu()) {
                answer += s + "\n";
            }
        } else if (message.toLowerCase().contains("/что значит ")) {
            message = message.replace("/что значит ", "");
            message = message.trim();
            return getArticleText(message);

        } else if (message.toLowerCase().contains("/спасибо")) {
                answer += "Рад помочь";
        } else {
            answer += "Не понимаю тебя";
        }

        return answer;
    }

    public static String getArticleText(String message) {
        Article article = new Article(message);
        List<String> articleText = article.getArticle();

        String result = "";
        for (String s : articleText) {
            result += s + "\n\n";
        }
        return result;
    }

    public static List<String> menu() {
        List<String> list = new ArrayList<>();
        list.add("https://www.youtube.com/watch?v=5qap5aO4i9A");
        return list;
    }

    public String getBotUsername() {
        return "WikiScrapper";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}
