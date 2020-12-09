package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("home"), contactData.getHome());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initAddContact() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int index) {
    click(By.xpath("//a[@href='edit.php?id="+ index +"']"));
  }

  public void initContactModificationById(int id) {
    click(By.xpath("//a[@href='edit.php?id="+ id +"']"));
  }

  public void updateContactForm() {
    click(By.name("update"));
  }

  public void create(ContactData contact) {
    initAddContact();
    fillContactForm(contact, true);
    submitContactForm();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    updateContactForm();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContact();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("[title='Edit']"));
    for (int i = 2; i <= elements.size(); i++) {
      String lastname = wd.findElement(By.xpath("//tr[" + i + "]/td[2]")).getText();
      String firstname = wd.findElement(By.xpath("//tr[" + i + "]/td[3]")).getText();
//      String id = element.findElement(By.tagName("input")).getAttribute("value");
      int id = Integer.parseInt(wd.findElement(By.name("selected[]")).getAttribute("id"));
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }

//  public Set<ContactData> all() {
//    Set<ContactData> contacts = new HashSet<ContactData>();
//    List<WebElement> elements = wd.findElements(By.cssSelector("[title='Edit']"));
//    for (int i = 2; i <= elements.size(); i++) {
//      String lastname = wd.findElement(By.xpath("//tr[" + i + "]/td[2]")).getText();
//      String firstname = wd.findElement(By.xpath("//tr[" + i + "]/td[3]")).getText();
//      int id = Integer.parseInt(wd.findElement(By.name("selected[]")).getAttribute("id"));
//      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
//    }
//    return contacts;
//  }

  public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String firstname = element.findElement(By.xpath("td[3]")).getText();
      String lastname = element.findElement(By.xpath("td[2]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }

}
