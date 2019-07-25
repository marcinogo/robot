package edition.academy.seventh.repository;

import edition.academy.seventh.model.PriceHistory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.DataProvider;

public class DataProviderForBookstoreBookParser {

  private static Random random = new Random();

  @DataProvider
  public static Object[][] dataProviderBookstoreBookParser() {
    return new Object[][] {
      {"Title", "Author", "Subtitle", "Księgarnia1", "Hiperłącze1","IMG", getRandomPriceHistoryList()},
      {"Title2", "Author2", "Subtitle2", "Księgarnia2", "Hiperłącze2","IMG2", getRandomPriceHistoryList()},
      {"2Title", "2Subtitle", "2Author", "Księgarnia3", "Hiperlink3", "IMG3",getRandomPriceHistoryList()}
    };
  }

  private static List<PriceHistory> getRandomPriceHistoryList() {
    List<LocalDateTime> dateTimes = Stream.generate(() -> LocalDateTime.of(
        random.nextInt(2000)+1,
        random.nextInt(10)+1,
        random.nextInt(25 )+1,
        random.nextInt(20)+1,
        random.nextInt((50))+1))
        .limit(random.nextInt(8) + 1)
        .collect(Collectors.toList());

    return dateTimes.stream().map(dateTime ->
        new PriceHistory(null, "cena1", "cena2", dateTime))
        .collect(Collectors.toList());
  }
}
