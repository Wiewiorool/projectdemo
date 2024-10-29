package pl.wasinskipatryk.demo;

import java.util.List;

public interface SaleRepository {

    List<Sale> findAll();

    Sale createNewSale(Sale sale);

    void read(Sale sale);

    void update(Sale oldSale, Sale newSale);
    //here we're going to update amount of cars in magazine?

    void delete(Sale sale);


}
