package edition.academy.seventh.service;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviderForConvertingIsbnToBook {

  @DataProvider
  public static Object[][] dataProviderForConvertingIsbnToBook() {
    return new Object[][] {
      {
        new ArrayList<>(List.of("9781788476249")),
        new ArrayList<>(
            List.of(
                "{\"error\":\"0\",\"title\":\"Learning C++ by Building Games with Unreal Engine 4, 2nd Edition\",\"subtitle\":"
                    + "\"A beginner's guide to learning 3D game development with C++ and UE4\",\"authors\":\"Sharan Volin\","
                    + "\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1788476247\",\"isbn13\":"
                    + "\"9781788476249\",\"pages\":\"468\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Learning to program"
                    + " in C++ requires some serious motivation. Unreal Engine 4 (UE4) is a powerful C++ engine with a"
                    + " full range of features used to create top-notch, exciting games by AAA studios, making it the "
                    + "fun way to dive into learning C++17.This book starts by installing a code editor so you can...\","
                    + "\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781788476249.png\",\"url\":\"https://itbook.store/books/9781788476249\"}"))
      },
      {
        new ArrayList<>(List.of("9781788476249")),
        new ArrayList<>(
            List.of(
                "{\"error\":\"0\",\"title\":\"Learning C++ by Building Games with Unreal Engine 4, 2nd Edition\",\"subtitle\":"
                    + "\"A beginner's guide to learning 3D game development with C++ and UE4\",\"authors\":\"Sharan Volin\","
                    + "\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1788476247\",\"isbn13\":"
                    + "\"9781788476249\",\"pages\":\"468\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Learning to "
                    + "program in C++ requires some serious motivation. Unreal Engine 4 (UE4) is a powerful C++ engine"
                    + " with a full range of features used to create top-notch, exciting games by AAA studios, making "
                    + "it the fun way to dive into learning C++17.This book starts by installing a code editor so you can...\","
                    + "\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781788476249.png\",\"url\":"
                    + "\"https://itbook.store/books/9781788476249\"}"))
      },
      {
        new ArrayList<>(List.of("9781789533576")),
        new ArrayList<>(
            List.of(
                "{\"error\":\"0\",\"title\":\"Mastering OpenCV 4, 3rd Edition\",\"subtitle\":\"A comprehensive guide to "
                    + "building computer vision and image processing applications with C++\",\"authors\":\"Roy Shilkrot,"
                    + " David Millan Escriva\",\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":"
                    + "\"1789533570\",\"isbn13\":\"9781789533576\",\"pages\":\"280\",\"year\":\"2018\",\"rating\":\"0\","
                    + "\"desc\":\"Mastering OpenCV, now in its third edition, targets computer vision engineers taking"
                    + " their first steps toward mastering OpenCV. Keeping the mathematical formulations to a solid but"
                    + " bare minimum, the book delivers complete projects from ideation to running code, targeting current"
                    + " hot topics in comput...\",\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781789533576.png\","
                    + "\"url\":\"https://itbook.store/books/9781789533576\"}"))
      }
    };
  }
}
