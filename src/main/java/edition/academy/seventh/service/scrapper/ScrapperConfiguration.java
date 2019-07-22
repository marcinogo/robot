package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.service.PromotionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Provides beans of {@link PromotionProvider} type to the
 * {@link org.springframework.context.ApplicationContext}.
 *
 * @author Kacper Staszek
 */
@Configuration
class ScrapperConfiguration {

  @Bean(name = "empikScrapper")
  PromotionProvider empikPromotionProvider() {
    return new EmpikScrapper(
        "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=",
        "&qtype=facetForm",
        "productWrapper",
        "EMPIK");
  }

  @Bean(name = "pwnScrapper")
  PromotionProvider pwnPromotionProvider() {
    return new PwnScrapper(
        "https://ksiegarnia.pwn.pl/promocje?limit=96&vt=list&page=",
        "",
        "emp-product-tile-list",
        "PWN");
  }

  @Bean(name = "swiatKsiazkiScrapper")
  PromotionProvider swiatKsiazkiPromotionProvider() {
    return new SwiatKsiazkiScrapper(
        "https://www.swiatksiazki.pl/Ksiazki/outlet-3255.html?p=",
        "&product_list_limit=30&product_list_mode=grid",
        "item product product-item",
        "ŚWIAT KSIĄŻKI");
  }
}
