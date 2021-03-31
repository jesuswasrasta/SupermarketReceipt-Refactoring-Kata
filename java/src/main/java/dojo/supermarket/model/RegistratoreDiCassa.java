package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistratoreDiCassa {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    /*
      - e se lo chiamassi RegistratoreDiCassa?
        - in questo codice mi urterebbe
        - nel dominio uso la lingua naturale, ad es. l'italiano
        - Gherkin
    * */
    public RegistratoreDiCassa(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    /*
      e' giusto che sia lui a gestire le offerte? e' sua responsabilita?
      non e' espressa l'idea dell'offerta
    * */
    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq: productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        //le offerte poi le manipola il Cart, mix di responsabilita'
        theCart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

}
