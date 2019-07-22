package edition.academy.seventh.service;

import edition.academy.seventh.database.model.DtoBook;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manages providers that have been registered by <code>registerPromotionProvider</code> method.
 * Provides API to run all registered {@link PromotionProvider} and stores results of their work.
 *
 * @author Kamil Rojek
 */
@Service
public class PromotionProviderManager {
  private List<DtoBook> scrappedBooks;
  private List<PromotionProvider> providers;
  private Phaser phaser = new Phaser(1);

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
  public void registerPromotionProvider(PromotionProvider promotionProvider) {
    providers.add(promotionProvider);
  }

  /**
   * Provides results of single providers' run.
   *
   * @return List of scrapped {@link DtoBook books}
   * @throws ProvidersNotFoundException when there are no registered {@link PromotionProvider
   *     promotion providers}.
   */
  public List<DtoBook> getScrappedBooks() throws ProvidersNotFoundException {
    if (providers.isEmpty()) throw new ProvidersNotFoundException("No providers detected");

    scrappedBooks.clear();
    runProviders();
    return scrappedBooks;
  }

  private void runProviders() {
    ExecutorService executorService = Executors.newFixedThreadPool(providers.size());
    providers.forEach(
        provider -> {
          phaser.register();
          executorService.submit(runProvider(provider));
        });
    phaser.arriveAndAwaitAdvance();
  }

  private Runnable runProvider(PromotionProvider provider) {
    return () -> {
      storeBooks(provider.getPromotions());
    };
  }

  private void storeBooks(List<DtoBook> booksOnPromotion) {
    scrappedBooks.addAll(booksOnPromotion);
    phaser.arrive();
  }
}
