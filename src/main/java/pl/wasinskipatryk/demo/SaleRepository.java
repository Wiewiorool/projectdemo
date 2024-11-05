package pl.wasinskipatryk.demo;

import java.util.List;

public interface SaleRepository {

    List<Sale> findAll();

    Sale createNewSale(Sale sale);

    void delete(Sale sale);


}
