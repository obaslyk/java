package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDelete() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().createContact(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "[none]"));
    }
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
  }
}
