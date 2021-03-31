package dojo.supermarket.model;

// interfaccia = contratto

public interface SupermarketCatalog {
    void addProduct(Product product, double price);

    double getUnitPrice(Product product);

}
