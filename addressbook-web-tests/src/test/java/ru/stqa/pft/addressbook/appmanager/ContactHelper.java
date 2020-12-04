package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void initClassModification(int index) {
    click(By.xpath("//a[@href='edit.php?id="+ index +"']"));
  }

  public void updateContactForm() {
    click(By.name("update"));
  }

  public void createContact(ContactData contact) {
    initAddContact();
    fillContactForm(contact, true);
    submitContactForm();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("[title='Edit']"));
    for (int i = 2; i <= elements.size(); i++) {
//      String firstname = element.getText();
      String lastname = wd.findElement(By.xpath("//tr[" + i + "]/td[2]")).getText();
      String firstname = wd.findElement(By.xpath("//tr[" + i + "]/td[3]")).getText();
//      String id = element.findElement(By.tagName("input")).getAttribute("value");
      int id = Integer.parseInt(wd.findElement(By.name("selected[]")).getAttribute("id"));
      ContactData contact = new ContactData(id, firstname, lastname, null, null, null );
      contacts.add(contact);
    }
    return contacts;
  }
}
