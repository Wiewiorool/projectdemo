package pl.wasinskipatryk.demo.salesrepository;

import pl.wasinskipatryk.demo.sale.Sale;
import pl.wasinskipatryk.demo.carsrepository.CarsRepository;

import java.util.ArrayList;
import java.util.List;

public class ListBasedSaleRepository implements SaleRepository {

    private List<Sale> sales = new ArrayList<>();
    private CarsRepository carsRepository;

    public ListBasedSaleRepository(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;

    }

    @Override
    public List<Sale> findAll() {
        return List.of();
    }

    @Override
    public Sale createNewSale(Sale saleToAdd) {
        sales.add(saleToAdd);
        carsRepository.delete(saleToAdd.getCar());
        return saleToAdd;
    }

    @Override
    public void delete(Sale saleToBeDeleted) {
        sales.remove(saleToBeDeleted);

    }

}
