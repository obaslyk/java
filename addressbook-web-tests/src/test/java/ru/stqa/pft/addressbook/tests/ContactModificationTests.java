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
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstname("Olga").withLastname("Petrova")
            .withHome("123456789").withEmail("12345@mail.ru");
    app.goTo().homePage();
    app.contact().modify(before, index, contact);
    app.goTo().homePage();
    Thread.sleep(3000);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}
