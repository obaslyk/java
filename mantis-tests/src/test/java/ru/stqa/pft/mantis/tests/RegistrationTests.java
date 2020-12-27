package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase{

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistrationWiser() throws IOException, MessagingException {
        // Текущее время в миллисекундах от 01.01.1970
        long now = System.currentTimeMillis();
        String email = String.format("user%s@localhost.localdomain", now);
        String user = String.format("user%s", now);
        String password = "password";
        //Заполнение и отправка формы регистрации
        app.registration().start(user, email);
        // Ожидание почтового сообщения
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 20000);
        // Извлечение ссылки на подтверждение регистрации
        String confirmationLink = findConfirmationLink(mailMessages, email);
        // Проверка, что вход в приложение выполнен успешно
        app.registration().finish(confirmationLink, password);
        // Проверка, что вход в приложение выполнен успешно
        assertTrue(app.newSession().login(user, password));
    }

    @Test (enabled = false)
    public void testRegistrationJames() throws IOException, MessagingException {
        // Текущее время в миллисекундах от 01.01.1970
        long now = System.currentTimeMillis();
        String email = String.format("user%s@localhost", now);
        String user = String.format("user%s", now);
        String password = "password";
        // Создание пользователя на внешнем почтовом сервере
        app.james().createUser(user, password);
        //Заполнение и отправка формы регистрации
        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.james().waitForMail(user, password,20000);
//        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
//        находим среди списка писем нужное и отправляем на ссылку
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user, password));
    }

    // Среди всех сообщений нужное и извлекаем ссылку на подтверждение регистрации
    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        // Поиск ссылки на подтверждение регистрации с помощью библотеки verbalregex
        // Найти подстроку "http://", за ней должны идти один или более непробельных символов
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }


     @AfterMethod(alwaysRun = true)
//    alwaysRun = true - чтобы почтовый сервер останавливался и при неуспешном завершении тестов
    public void stopMailServer() {
        app.mail().stop();
    }
}
