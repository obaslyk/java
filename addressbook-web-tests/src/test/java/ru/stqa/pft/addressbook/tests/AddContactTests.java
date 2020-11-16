package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class AddContactTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "test1"));
    app.getNavigationHelper().returnToHomePage();
  }
}
