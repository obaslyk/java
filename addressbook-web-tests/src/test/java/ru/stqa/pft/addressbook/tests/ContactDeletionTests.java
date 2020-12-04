package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void ensurePreconditions() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().returnToHomePage();
      app.getContactHelper().createContact(new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "[none]"));
    }
  }

  @Test
  public void testContactDelete() throws InterruptedException {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContact();
    Thread.sleep(3000);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }
}
