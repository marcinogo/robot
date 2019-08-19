package edition.academy.seventh.service;

import com.sun.xml.bind.v2.TODO;
import edition.academy.seventh.database.model.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.function.Consumer;

/**
 * Manages providers that have been registered by <code>registerPromotionProvider</code> method.
 * Provides API to run all registered {@link PromotionProvider} and stores results of their work.
 *
 * @author Kamil Rojek
 */

//todo zmiana nazwy klasy, klasa odpala albo zbiera ksiazki ODPALACZ nie MANAGER
@Service
public class PromotionProviderManager {

  private List<BookDto> scrappedBooks;
  private List<PromotionProvider> providers;
//  private Phaser phaser = new Phaser(1);

  /** Constructs <code>PromotionProviderManager</code> with non providers registered. */
  public PromotionProviderManager() {
    this.scrappedBooks = new CopyOnWriteArrayList<>();
    this.providers = new ArrayList<>();
  }

  /**
   * Constructs <code>PromotionProviderManager</code> with registered {@link PromotionProvider
   * promotion providers}.
   *
   * @param promotionProvider {@link PromotionProvider promotion provider} to be registered.
   */
  @Autowired
  public PromotionProviderManager(List<PromotionProvider> promotionProvider) {
    this.scrappedBooks = new CopyOnWriteArrayList<>();
    this.providers = promotionProvider;
  }

  /**
   * Adds given List of {@link PromotionProvider promotion providers} to registered promotion
   * providers.
   *
   * @param promotionProviders List of {@link PromotionProvider promotion providers} to be
   *     registered.
   */
  public void registerPromotionProvider(List<PromotionProvider> promotionProviders) {
    promotionProviders.forEach(this::registerPromotionProvider);
  }

  /**
   * Adds single {@link PromotionProvider promotion provider} to registered.
   *
   * @param promotionProvider {@link PromotionProvider promotion provider} to be registered.
   */
  private void registerPromotionProvider(PromotionProvider promotionProvider) {
    providers.add(promotionProvider);
  }

  /**
   * Provides results of single providers' run.
   *
   * @return List of scrapped {@link BookDto books}
   * @throws ProvidersNotFoundException when there are no registered {@link PromotionProvider
   *     promotion providers}.
   */
  public List<BookDto> getScrappedBooks() throws ProvidersNotFoundException {
    //todo zmienic opis, dlaczego tak sie moglo stac?
    if (providers.isEmpty()) throw new ProvidersNotFoundException("No providers detected");

    scrappedBooks.clear();
    runProviders();
    return scrappedBooks;
  }

  private void runProviders() {
    //todo runtime??
    ExecutorService executorService = Executors.newFixedThreadPool(providers.size());
    //todo nie chcemy tego zmienic?(moze semafor?)
    Phaser phaser = new Phaser(1);
    Consumer<PromotionProvider> promotionProviderConsumer =
        provider -> {

          phaser.register();
          Runnable runnable =
              () -> {
                List<BookDto> promotions = provider.getPromotions();
                scrappedBooks.addAll(promotions);
                phaser.arriveAndDeregister();
                promotions.clear();
              };
          executorService.submit(runnable);
        };
    providers.forEach(promotionProviderConsumer);
    phaser.arriveAndAwaitAdvance();
  }

//  private Runnable runProvider(PromotionProvider provider) {
//    return runnable;
//  }
//
//  private void storeBooks(List<BookDto> booksOnPromotion) {
//    scrappedBooks.addAll(booksOnPromotion);
//    phaser.arriveAndDeregister();
//  }
}
