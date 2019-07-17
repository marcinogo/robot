package edition.academy.seventh.serivce;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviderForJsonParser {

  @DataProvider
  public static Object[][] dataProviderForJsonResponse() {
    return new Object[][] {
      {
        "https://api.itbook.store/1.0/new",
        "{\"error\":\"0\",\"total\":\"10\",\"books\":[{\"title\":\"Learning C++ by Building Games with Unreal Engine 4, "
            + "2nd Edition\",\"subtitle\":\"A beginner's guide to learning 3D game development with C++ and UE4\","
            + "\"isbn13\":\"9781788476249\",\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781788476249.png\""
            + ",\"url\":\"https://itbook.store/books/9781788476249\"},{\"title\":\"Apache Kafka Quick Start Guide\","
            + "\"subtitle\":\"Leverage Apache Kafka 2.0 to simplify real-time data processing for distributed applications\","
            + "\"isbn13\":\"9781788997829\",\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781788997829.png\","
            + "\"url\":\"https://itbook.store/books/9781788997829\"},{\"title\":\"Hands-On Dark Web Analysis\","
            + "\"subtitle\":\"Learn what goes on in the Dark Web, and how to work with it\",\"isbn13\":\"9781789133363\","
            + "\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789133363.png\","
            + "\"url\":\"https://itbook.store/books/9781789133363\"},{\"title\":\"CentOS Quick Start Guide\","
            + "\"subtitle\":\"Get up and running with CentOS server administration\",\"isbn13\":\"9781789344875\","
            + "\"price\":\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789344875.png\","
            + "\"url\":\"https://itbook.store/books/9781789344875\"},{\"title\":\"Machine Learning with Apache Spark Quick Start Guide\","
            + "\"subtitle\":\"Uncover patterns, derive actionable insights, and learn from big data using MLlib\","
            + "\"isbn13\":\"9781789346565\",\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789346565.png\","
            + "\"url\":\"https://itbook.store/books/9781789346565\"},{\"title\":\"Mastering OpenCV 4, 3rd Edition\","
            + "\"subtitle\":\"A comprehensive guide to building computer vision and image processing applications with C++\","
            + "\"isbn13\":\"9781789533576\",\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781789533576.png\","
            + "\"url\":\"https://itbook.store/books/9781789533576\"},{\"title\":\"Xamarin.Forms Projects\","
            + "\"subtitle\":\"Build seven real-world cross-platform mobile apps with C# and Xamarin.Forms\","
            + "\"isbn13\":\"9781789537505\",\"price\":\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789537505.png\","
            + "\"url\":\"https://itbook.store/books/9781789537505\"},{\"title\":\"Microsoft Dynamics NAV Development Quick Start Guide\","
            + "\"subtitle\":\"Get up and running with Microsoft Dynamics NAV\",\"isbn13\":\"9781789612769\","
            + "\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789612769.png\","
            + "\"url\":\"https://itbook.store/books/9781789612769\"},{\"title\":\"Practical Network Automation, 2nd Edition\","
            + "\"subtitle\":\"A beginner's guide to automating and optimizing networks using Python, Ansible, and more\","
            + "\"isbn13\":\"9781789955651\",\"price\":\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789955651.png\","
            + "\"url\":\"https://itbook.store/books/9781789955651\"},{\"title\":\"QlikView: Advanced Data Visualization\","
            + "\"subtitle\":\"Discover Deeper Insights with Qlikview by Building Your Own Rich Analytical Applications from Scratch\","
            + "\"isbn13\":\"9781789955996\",\"price\":\"$45.17\",\"image\":\"https://itbook.store/img/books/9781789955996.png\","
            + "\"url\":\"https://itbook.store/books/9781789955996\"}]}"
      },
      {
        "https://api.itbook.store/1.0/books/9781788476249",
        "{\"error\":\"0\",\"title\":\"Learning C++ by Building Games with Unreal Engine 4, 2nd Edition\",\"subtitle\":"
            + "\"A beginner's guide to learning 3D game development with C++ and UE4\",\"authors\":\"Sharan Volin\","
            + "\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1788476247\",\"isbn13\":\"9781788476249\","
            + "\"pages\":\"468\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Learning to program in C++ requires "
            + "some serious motivation. Unreal Engine 4 (UE4) is a powerful C++ engine with a full range of features used to create top-notch, "
            + "exciting games by AAA studios, making it the fun way to dive into learning C++17.This book starts by installing a code editor so you can...\","
            + "\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781788476249.png\",\"url\":\"https://itbook.store/books/9781788476249\"}"
      },
      {
        "https://api.itbook.store/1.0/books/9781789612769",
        "{\"error\":\"0\",\"title\":\"Microsoft Dynamics NAV Development Quick Start Guide\",\"subtitle\":"
            + "\"Get up and running with Microsoft Dynamics NAV\",\"authors\":\"Alexander Drogin\",\"publisher\":"
            + "\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1789612764\",\"isbn13\":\"9781789612769\","
            + "\"pages\":\"210\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Microsoft Dynamics NAV is an enterprise"
            + " resource planning (ERP) software suite for organizations. The system offers specialized functionality "
            + "for manufacturing, distribution, government, retail, and other industries. This book gets you started with"
            + " its integrated development environment for solving ...\",\"price\":\"$29.99\",\"image\":"
            + "\"https://itbook.store/img/books/9781789612769.png\",\"url\":\"https://itbook.store/books/9781789612769\"}"
      },
      {
        "https://api.itbook.store/1.0/books/9781789344875",
        "{\"error\":\"0\",\"title\":\"CentOS Quick Start Guide\",\"subtitle\":\"Get up and running with CentOS server administration\","
            + "\"authors\":\"Shiwang Kalkhanda\",\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1789344875\","
            + "\"isbn13\":\"9781789344875\",\"pages\":\"320\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Linux kernel "
            + "development has been the worlds largest collaborative project to date. With this practical guide, you will "
            + "learn Linux through one of its most popular and stable distributions.This book will introduce you to essential "
            + "Linux skills using CentOS 7. It describes how a Linux system is organ...\",\"price\":\"$39.99\",\"image\":"
            + "\"https://itbook.store/img/books/9781789344875.png\",\"url\":\"https://itbook.store/books/9781789344875\"}"
      }
    };
  }
}
