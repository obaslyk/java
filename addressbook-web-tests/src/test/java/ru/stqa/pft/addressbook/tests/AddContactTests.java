package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AddContactTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData("Olga", "Petrova", "123456789", "12345@mail.ru", "test1");
    app.contact().create(contact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

//   1 способ (использование цикла)
//    int max = 0;
//    for (ContactData c : after) {
//      if (c.getId() > max) {
//        max = c.getId();
//      }
//    }

////    2 способ (с помощью анонимного класса)
//    Comparator<? super ContactData> byid = new Comparator<ContactData>() {
//      @Override
//      public int compare(ContactData o1, ContactData o2) {
//        return Integer.compare(o1.getId(), o2.getId());
//      }
//    };
//    int max = after.stream().max(byid).get().getId();

//   3 способ при помощи потоков и анонимных функций (лямбда выражений)
//    contact.setId(after.stream().max( (o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
//    before.add(contact);
//    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
  }
}



