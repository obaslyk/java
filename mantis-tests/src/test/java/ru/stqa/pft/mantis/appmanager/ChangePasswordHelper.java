package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ChangePasswordHelper extends HelperBase {

    // инициализация браузера
    public ChangePasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void loginUI(String username, String password) {
        wd.get(app.getProperty("web.baseUrl"));
        type(By.name("username"), username);
        click(By.cssSelector("input[value='Войти']"));
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Войти']"));
    }

    public void start(int id) {
        click(By.xpath("//a[contains(@href, 'manage_overview_page.php')]"));
        click(By.xpath("//a[contains(@href, 'manage_user_page.php')]"));
        click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
        click(By.cssSelector("input[value='Сбросить пароль']"));
    }
}
