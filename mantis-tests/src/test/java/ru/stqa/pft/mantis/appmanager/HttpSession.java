package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private CloseableHttpClient httpclient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
        this.app = app;
//      создается объект, который будет отправлять запросы на сервер
//      setRedirectStrategy - перенаправления
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

//    метод выпоняет логин
    public boolean login(String username, String password) throws IOException {
//        создаем запрос типа post на адрес web.baseUrl + /login.php
        HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
//        задаем параметры
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
//        параметры упаковываются и помещаются в запрос post.setEntity
        post.setEntity(new UrlEncodedFormEntity(params));
//        httpclient.execute(post) - здесь выполняется запрос, получаем ответ
        CloseableHttpResponse response = httpclient.execute(post);
        String body = getTextFrom(response);
//        проверям успешно ли пользователь вошел (текст страницы содержит строку "<span class=\"user-info\">%s</span>"
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

//    метод определяет, какой пользователь сейчас залогинен
    public boolean isLoggedInAs(String username) throws IOException {
//        выполняем запрос get  на адрес web.baseUrl + /index.php
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
//        выполняем запрос, получаем ответ
        CloseableHttpResponse response = httpclient.execute(get);
//        получаем текст
        String body = getTextFrom(response);
//        проверяем, что в тексте страницы содержится нужный фрагмент <span class="user-info">%s</span>
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
    }
}
