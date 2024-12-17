package pl.wasinskipatryk.demo.salesrepository;

import pl.wasinskipatryk.demo.sale.Sale;

import java.util.List;

public interface SaleRepository {

    List<Sale> findAll();

    Sale createNewSale(Sale sale);

    void delete(Sale sale);


}
