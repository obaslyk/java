package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistanse() {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(4, 6);
    Assert.assertEquals(p1.distanse(p2), 5);
  }
  @Test
  public void testDistanse2() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(5, 12);
    Assert.assertEquals(p2.distanse(p1), 13);
  }

}