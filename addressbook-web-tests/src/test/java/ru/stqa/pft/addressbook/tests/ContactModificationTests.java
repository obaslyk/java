package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() throws InterruptedException {
    int before = app.getContactHelper().getContactCount();
    if (! app.getContactHelper().isThereAContact()) {

      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().createContact(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "[none]"));
    }
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().initClassModification();
    app.getContactHelper().fillContactForm(new ContactData("Olga1", "Petrova1", "123456789", "12345@mail.ru", null), false);
    app.getContactHelper().updateContactForm();
    app.getNavigationHelper().returnToHomePage();
    Thread.sleep(3000);
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
  }
}
