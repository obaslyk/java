package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoGroupPage();
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().createContact(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "test1"));
    }
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().initClassModification();
    app.getContactHelper().fillContactForm(new ContactData("Olga1", "Petrova1", "123456789", "12345@mail.ru", null), false);
    app.getContactHelper().updateContactForm();
    app.getNavigationHelper().returnToHomePage();
  }
}
