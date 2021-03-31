package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    // il nome del test non dice nulla, o meglio, dice cose sbaglaite :)
    @Test
    public void tenPercentDiscount() {
        //ARRANGE
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        // Nostre naming convention
        // classi interne iniziano per S, classi VCL iniziano con T, es TButton, TTextBox, ...
        // oggetti con lettere minuscola
        // membri interni con fminuscola, es. fcampoPrivato

        // no notazione ungherese

        // m_campoPrivato (m_ -> membro interno di una classe)
        // _campoPrivato (ho tolto m)
        // campoPrivato (ho tolto underscore)

        //camelCase || PascalCase
        RegistratoreDiCassa registratoreDiCassa = new RegistratoreDiCassa(catalog);
        registratoreDiCassa.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = registratoreDiCassa.checksOutArticlesFrom(cart);

        // ASSERT
        /*
          - molte asserzioni per singolo test: va bene?
          - Uncle Bob: "un test deve avere uno (ed un solo) motivo per fallire"
        * */

        assertEquals(4.975, receipt.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size()); //questo unico test "utile"

        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice());
        assertEquals(2.5*1.99, receiptItem.getTotalPrice());
        assertEquals(2.5, receiptItem.getQuantity());

    }

    @Test()
    public void le_mele_costano_199_al_kilo() {
        //ARRANGE
        SupermarketCatalog catalog = new FakeCatalog();

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        RegistratoreDiCassa registratoreDiCassa = new RegistratoreDiCassa(catalog);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        // ACT
        Receipt receipt = registratoreDiCassa.checksOutArticlesFrom(cart);

        assertEquals(4.975, receipt.getTotalPrice(), 0.01);

    }

    @Test()
    public void le_mele_hanno_sconto_20_percento() {
        //ARRANGE
        SupermarketCatalog catalog = new FakeCatalog();

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        RegistratoreDiCassa registratoreDiCassa = new RegistratoreDiCassa(catalog);
        // enum + argomento = minchiata :D
        // l'enum non rappresenta correttamente l'offerta
        // idea: implementare diverse offerte
        registratoreDiCassa.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 20.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 1);

        // ACT
        Receipt receipt = registratoreDiCassa.checksOutArticlesFrom(cart);

        assertEquals(1.60, receipt.getTotalPrice(), 0.01);

    }

}
