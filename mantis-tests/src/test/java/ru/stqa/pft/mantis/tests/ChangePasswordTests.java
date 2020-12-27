package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, InterruptedException, MessagingException {
        // Получение информации о пользователе из БД
        UserData user = app.db().user();
        String email = user.getEmail();
        String password = "new_password";
        app.changePassword().loginUI("administrator", "root");
        // смена пароля пользователя
        app.changePassword().start(user.getId());
        // Ожидание почтового сообщения
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        // Извлечение ссылки на подтверждение регистрации
        String confirmationLink = findConfirmationLink(mailMessages, email);
        System.out.println(confirmationLink);
        app.changePassword().finish(confirmationLink, password);
        // Вход в приложение с новым паролем
        Assert.assertTrue(app.newSession().login(user.getUsername(), password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}

