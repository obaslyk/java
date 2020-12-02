package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.*;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() throws InterruptedException {
    if (! app.getContactHelper().isThereAContact()) {

      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().createContact(new ContactData( "Olga", "Petrova", "123456789", "12345@mail.ru", "[none]"));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().initClassModification();
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Olga1", "Petrova", "123456789", "12345@mail.ru", null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().updateContactForm();
    app.getNavigationHelper().returnToHomePage();
    Thread.sleep(3000);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}
