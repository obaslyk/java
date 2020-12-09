package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.*;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public  void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstname("Olga").withLastname("Petrova").withHome("123456789")
              .withEmail("12345@mail.ru").withGroup("[none]"));
    }
  }

  @Test
  public void testContactModification() throws InterruptedException {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Olga").withLastname("Petrova")
            .withHome("123456789").withEmail("12345@mail.ru");
    app.goTo().homePage();
    app.contact().modify(contact);
    app.goTo().homePage();
    Thread.sleep(3000);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);
  }
}
