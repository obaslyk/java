package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDelete() throws InterruptedException {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().createContact(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "[none]"));
    }
    int before = app.getContactHelper().getContactCount();
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact(before - 1);
    app.getContactHelper().deleteSelectedContact();
    Thread.sleep(3000);
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);
  }
}
