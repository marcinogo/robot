package edition.academy.seventh.bookstoreconnectionservice;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;

/**
 * @author Ola Podorska
 */

public class DataProviderForJsonParser {

  @DataProvider
  public static Object[][] dataProviderForJsonResponse() {
    return new Object[][]{
        {
            "https://api.itbook.store/1.0/new",
            "{\"error\":\"0\",\"total\":\"10\",\"books\":[{\"title\":\"Learning C++ by Building Games with Unreal Engine 4, "
                +
                "2nd Edition\",\"subtitle\":\"A beginner's guide to learning 3D game development with C++ and UE4\","
                +
                "\"isbn13\":\"9781788476249\",\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781788476249.png\""
                +
                ",\"url\":\"https://itbook.store/books/9781788476249\"},{\"title\":\"Apache Kafka Quick Start Guide\","
                +
                "\"subtitle\":\"Leverage Apache Kafka 2.0 to simplify real-time data processing for distributed applications\","
                +
                "\"isbn13\":\"9781788997829\",\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781788997829.png\","
                +
                "\"url\":\"https://itbook.store/books/9781788997829\"},{\"title\":\"Hands-On Dark Web Analysis\","
                +
                "\"subtitle\":\"Learn what goes on in the Dark Web, and how to work with it\",\"isbn13\":\"9781789133363\","
                +
                "\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789133363.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789133363\"},{\"title\":\"CentOS Quick Start Guide\","
                +
                "\"subtitle\":\"Get up and running with CentOS server administration\",\"isbn13\":\"9781789344875\","
                +
                "\"price\":\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789344875.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789344875\"},{\"title\":\"Machine Learning with Apache Spark Quick Start Guide\","
                +
                "\"subtitle\":\"Uncover patterns, derive actionable insights, and learn from big data using MLlib\","
                +
                "\"isbn13\":\"9781789346565\",\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789346565.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789346565\"},{\"title\":\"Mastering OpenCV 4, 3rd Edition\","
                +
                "\"subtitle\":\"A comprehensive guide to building computer vision and image processing applications with C++\","
                +
                "\"isbn13\":\"9781789533576\",\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781789533576.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789533576\"},{\"title\":\"Xamarin.Forms Projects\","
                +
                "\"subtitle\":\"Build seven real-world cross-platform mobile apps with C# and Xamarin.Forms\","
                +
                "\"isbn13\":\"9781789537505\",\"price\":\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789537505.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789537505\"},{\"title\":\"Microsoft Dynamics NAV Development Quick Start Guide\","
                +
                "\"subtitle\":\"Get up and running with Microsoft Dynamics NAV\",\"isbn13\":\"9781789612769\","
                +
                "\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789612769.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789612769\"},{\"title\":\"Practical Network Automation, 2nd Edition\","
                +
                "\"subtitle\":\"A beginner's guide to automating and optimizing networks using Python, Ansible, and more\","
                +
                "\"isbn13\":\"9781789955651\",\"price\":\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789955651.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789955651\"},{\"title\":\"QlikView: Advanced Data Visualization\","
                +
                "\"subtitle\":\"Discover Deeper Insights with Qlikview by Building Your Own Rich Analytical Applications from Scratch\","
                +
                "\"isbn13\":\"9781789955996\",\"price\":\"$45.17\",\"image\":\"https://itbook.store/img/books/9781789955996.png\","
                +
                "\"url\":\"https://itbook.store/books/9781789955996\"}]}"
        },
        {
            "https://api.itbook.store/1.0/books/9781788476249",
            "{\"error\":\"0\",\"title\":\"Learning C++ by Building Games with Unreal Engine 4, 2nd Edition\",\"subtitle\":"
                +
                "\"A beginner's guide to learning 3D game development with C++ and UE4\",\"authors\":\"Sharan Volin\","
                +
                "\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1788476247\",\"isbn13\":\"9781788476249\","
                +
                "\"pages\":\"468\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Learning to program in C++ requires "
                +
                "some serious motivation. Unreal Engine 4 (UE4) is a powerful C++ engine with a full range of features used to create top-notch, "
                +
                "exciting games by AAA studios, making it the fun way to dive into learning C++17.This book starts by installing a code editor so you can...\","
                +
                "\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781788476249.png\",\"url\":\"https://itbook.store/books/9781788476249\"}"
        },
        {
            "https://api.itbook.store/1.0/books/9781789612769",
            "{\"error\":\"0\",\"title\":\"Microsoft Dynamics NAV Development Quick Start Guide\",\"subtitle\":"
                +
                "\"Get up and running with Microsoft Dynamics NAV\",\"authors\":\"Alexander Drogin\",\"publisher\":"
                +
                "\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1789612764\",\"isbn13\":\"9781789612769\","
                +
                "\"pages\":\"210\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Microsoft Dynamics NAV is an enterprise"
                +
                " resource planning (ERP) software suite for organizations. The system offers specialized functionality "
                +
                "for manufacturing, distribution, government, retail, and other industries. This book gets you started with"
                +
                " its integrated development environment for solving ...\",\"price\":\"$29.99\",\"image\":"
                +
                "\"https://itbook.store/img/books/9781789612769.png\",\"url\":\"https://itbook.store/books/9781789612769\"}"
        },
        {
            "https://api.itbook.store/1.0/books/9781789344875",
            "{\"error\":\"0\",\"title\":\"CentOS Quick Start Guide\",\"subtitle\":\"Get up and running with CentOS server administration\","
                +
                "\"authors\":\"Shiwang Kalkhanda\",\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1789344875\","
                +
                "\"isbn13\":\"9781789344875\",\"pages\":\"320\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Linux kernel "
                +
                "development has been the worlds largest collaborative project to date. With this practical guide, you will "
                +
                "learn Linux through one of its most popular and stable distributions.This book will introduce you to essential "
                +
                "Linux skills using CentOS 7. It describes how a Linux system is organ...\",\"price\":\"$39.99\",\"image\":"
                +
                "\"https://itbook.store/img/books/9781789344875.png\",\"url\":\"https://itbook.store/books/9781789344875\"}"
        }
    };
  }

  @DataProvider
  public static Object[][] dataProviderForConvertingIsbnToBook() {
    return new Object[][]{
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
            new ArrayList<>(List.of("9781788476249", "9781788997829", "9781789133363")),
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
                        + "\"https://itbook.store/books/9781788476249\"}",
                    "{\"error\":\"0\",\"title\":\"Apache Kafka Quick Start Guide\",\"subtitle\":\"Leverage Apache Kafka 2.0 "
                        + "to simplify real-time data processing for distributed applications\",\"authors\":\"Raul Estrada\","
                        + "\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1788997824\",\"isbn13\":"
                        + "\"9781788997829\",\"pages\":\"186\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Apache Kafka "
                        + "is a great open source platform for handling your real-time data pipeline to ensure high-speed "
                        + "filtering and pattern matching on the \\ufb02y. In this book, you will learn how to use Apache "
                        + "Kafka for efficient processing of distributed applications and will get familiar with solving everyda...\","
                        + "\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781788997829.png\",\"url\":"
                        + "\"https://itbook.store/books/9781788997829\"}",
                    "{\"error\":\"0\",\"title\":\"Hands-On Dark Web Analysis\",\"subtitle\":\"Learn what goes on in the Dark Web, "
                        + "and how to work with it\",\"authors\":\"Sion Retzkin\",\"publisher\":\"Packt Publishing\",\"language\":"
                        + "\"English\",\"isbn10\":\"178913336X\",\"isbn13\":\"9781789133363\",\"pages\":\"210\",\"year\":\"2018\","
                        + "\"rating\":\"0\",\"desc\":\"The overall world wide web is divided into three main areas - the Surface Web,"
                        + " the Deep Web, and the Dark Web. The Deep Web and Dark Web are the two areas which are not accessible"
                        + " through standard search engines or browsers. It becomes extremely important for security professionals"
                        + " to have control o...\",\"price\":\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789133363.png\","
                        + "\"url\":\"https://itbook.store/books/9781789133363\"}"))
        },
        {
            new ArrayList<>(List.of("9781789533576", "9781789537505", "9781789612769")),
            new ArrayList<>(
                List.of(
                    "{\"error\":\"0\",\"title\":\"Mastering OpenCV 4, 3rd Edition\",\"subtitle\":\"A comprehensive guide to "
                        +
                        "building computer vision and image processing applications with C++\",\"authors\":\"Roy Shilkrot,"
                        +
                        " David Millan Escriva\",\"publisher\":\"Packt Publishing\",\"language\":\"English\",\"isbn10\":"
                        +
                        "\"1789533570\",\"isbn13\":\"9781789533576\",\"pages\":\"280\",\"year\":\"2018\",\"rating\":\"0\","
                        +
                        "\"desc\":\"Mastering OpenCV, now in its third edition, targets computer vision engineers taking"
                        +
                        " their first steps toward mastering OpenCV. Keeping the mathematical formulations to a solid but"
                        +
                        " bare minimum, the book delivers complete projects from ideation to running code, targeting current"
                        +
                        " hot topics in comput...\",\"price\":\"$44.99\",\"image\":\"https://itbook.store/img/books/9781789533576.png\","
                        +
                        "\"url\":\"https://itbook.store/books/9781789533576\"}",
                    "{\"error\":\"0\",\"title\":\"Xamarin.Forms Projects\",\"subtitle\":\"Build seven real-world cross-platform"
                        +
                        " mobile apps with C# and Xamarin.Forms\",\"authors\":\"Johan Karlsson, Daniel Hindrikes\",\"publisher\":"
                        +
                        "\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1789537509\",\"isbn13\":\"9781789537505\","
                        +
                        "\"pages\":\"416\",\"year\":\"2018\",\"rating\":\"5\",\"desc\":\"Xamarin.Forms is a lightweight "
                        +
                        "cross-platform development toolkit for building applications with a rich user interface.In this"
                        +
                        " book you'll start by building projects that explain the Xamarin.Forms ecosystem to get up and "
                        +
                        "running with building cross-platform applications. We'll increase in difficulty...\",\"price\":"
                        +
                        "\"$39.99\",\"image\":\"https://itbook.store/img/books/9781789537505.png\",\"url\":\"https://itbook.store/books/9781789537505\"}",
                    "{\"error\":\"0\",\"title\":\"Microsoft Dynamics NAV Development Quick Start Guide\",\"subtitle\":\"Get "
                        +
                        "up and running with Microsoft Dynamics NAV\",\"authors\":\"Alexander Drogin\",\"publisher\":"
                        +
                        "\"Packt Publishing\",\"language\":\"English\",\"isbn10\":\"1789612764\",\"isbn13\":\"9781789612769\","
                        +
                        "\"pages\":\"210\",\"year\":\"2018\",\"rating\":\"0\",\"desc\":\"Microsoft Dynamics NAV is an "
                        +
                        "enterprise resource planning (ERP) software suite for organizations. The system offers specialized"
                        +
                        " functionality for manufacturing, distribution, government, retail, and other industries. This "
                        +
                        "book gets you started with its integrated development environment for solving ...\",\"price\":"
                        +
                        "\"$29.99\",\"image\":\"https://itbook.store/img/books/9781789612769.png\",\"url\":\"https://itbook.store/books/9781789612769\"}"))
        }
    };
  }
}
