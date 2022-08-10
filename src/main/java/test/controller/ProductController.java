package test.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import test.model.Product;
import test.utilities.ProductDAO;

public class ProductController {
	public static void main(String[] agrs) {
		scrapingPage();
	}

	/**
	 * Metodo que consiste en hacer toda la logica del scraping este metodo,
	 * consulta la url de la pagina que sera escrapeada y asi mismo se extraen los
	 * datos como url, nombre y precio del producto para luego enviar estos datos al
	 * metodo que guarda en base de datos
	 */
	public static void scrapingPage() {

		ProductDAO prodDAO = new ProductDAO();

		try {
			String urlKtronix = "https://www.ktronix.com/computadores-tablet/computadores-portatiles/c/BI_104_KTRON";
			Document doc = Jsoup.connect(urlKtronix).get();
			List<Product> products = new ArrayList<Product>();
			int pages = 1;
			do {
				Elements elements = doc.select("li.product__list--item.product__list--ktronix > div > a");

				for (Element element : elements) {
					Product product = new Product();
					product.setUrl("https://www.ktronix.com/" + element.attr("href"));
					doc = Jsoup.connect(product.getUrl()).get();
					product.setName(doc.select("h1.product-name__name.ktronix-title-color").text());
					
					String price = doc.select(
							"span.col-xs-12.font-title.font-title--product-price.reset-padding__left.reset-padding__right.price-ktronix")
							.text();
				
					if (price.toLowerCase().contains("hoy")) {
						price = price.substring(0, price.toLowerCase().indexOf("hoy"));
					}
					
					product.setPrice(price);
					product.setCode(doc.select("span.product-name__sku-code > span.code").text());
					products.add(product);
					prodDAO.print(product);
				}

				doc = Jsoup.connect(urlKtronix + "?page=" + pages + "&pageSize=25&sort=relevance").get();

				pages = pages + 1;
			} while (pages <= 8);
			prodDAO.save(products);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
