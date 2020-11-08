package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class AddContactTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initAddContact();
    fillContactForm(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru"));
    submitContactForm();
    returnToHomePage();
  }

}
