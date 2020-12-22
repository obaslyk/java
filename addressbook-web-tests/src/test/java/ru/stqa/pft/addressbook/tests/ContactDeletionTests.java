package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstName("Olga").withLastName("Petrova")
//              .withEmail("12345@mail.ru").withGroup("[none]"));
              .withEmail("12345@mail.ru"));
    }
  }

  @Test
  public void testContactDelete() throws InterruptedException {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.goTo().homePage();
    app.contact().delete(deletedContact);
    Thread.sleep(1000);
    Contacts after = app.db().contacts();
    assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.withOut(deletedContact)));
  }

}
