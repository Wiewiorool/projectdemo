package pl.wasinskipatryk.demo;

import java.util.List;

public interface SaleRepository {

    List<Sale> findAll();

    Sale createNewSale(Sale sale);

    //here we're going to update amount of cars in magazine?

    void delete(Sale sale);


}
