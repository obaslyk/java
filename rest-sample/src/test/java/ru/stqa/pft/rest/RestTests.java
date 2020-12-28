package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {

    @Test
//    создаем новый баг-репорт в баг-трекере bugify
    public void testCreateIssue() throws IOException {
//        получаем старый список баг-репортов
        Set<Issue> oldIssues = getIssues();
//        создаем новый баг-репорт
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
////        получаем новый список баг-репортов
        Set<Issue> newIssues = getIssues();
//        добавляем в него созданный баг-репорт
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
                .returnContent().asString();
//        получаем json элемент
        JsonElement parsed = new JsonParser().parse(json);
//        из него по ключу нужно извлечь нужную часть (issuers)
        JsonElement issues = parsed.getAsJsonObject().get("issues");
//        преобразовываем в множество объектов типа issuer
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());

    }

    private int createIssue(Issue newIssue) throws IOException {
        // Запрос создания баг-репорта в Bugify
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent()
                .asString();
        // анализируем ответ
        JsonElement parsed = JsonParser.parseString(json);
        // извлекаем ИД нового баг-репорта и возврат результата в виде целого числа
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

}
