package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "[none]"));
    }
  }

  @Test
  public void testContactDelete() throws InterruptedException {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.goTo().homePage();
    app.contact().deleteContact(index);
    Thread.sleep(3000);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before, after);
  }

}
