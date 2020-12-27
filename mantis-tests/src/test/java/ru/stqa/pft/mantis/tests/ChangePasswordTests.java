package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;

public class ChangePasswordTests extends TestBase {

    @Test
    public void testChangePassword() throws IOException {
        app.newSession().login("administator", "root");
        UserData user = app.db().user();
        System.out.println(user);
    }
}

