package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().initClassModification();
    app.getContactHelper().fillContactForm(new ContactData("Olga1", "Petrova1", "123456789", "12345@mail.ru", null), false);
    app.getContactHelper().updateContactForm();
    app.getContactHelper().returnToHomePage();

  }
}
