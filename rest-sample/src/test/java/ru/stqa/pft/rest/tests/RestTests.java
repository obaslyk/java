package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

    @Test
//    создаем новый баг-репорт в баг-трекере bugify
    public void testCreateIssue() throws IOException {
//        получаем старый список баг-репортов
        Set<Issue> oldIssues = app.rest().getIssues();
//        создаем новый баг-репорт
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = app.rest().createIssue(newIssue);
////        получаем новый список баг-репортов
        Set<Issue> newIssues = app.rest().getIssues();
//        добавляем в него созданный баг-репорт
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }
}
