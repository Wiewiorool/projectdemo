package pl.wasinskipatryk.demo;

import java.util.ArrayList;
import java.util.List;

public class ListBasedSaleRepository implements SaleRepository {

    private List<Sale> sales = new ArrayList<>();
    private CarsRepository carsRepository;

    public ListBasedSaleRepository(CarsRepository carsRepository){
        this.carsRepository = carsRepository;

    }

    @Override
    public List<Sale> findAll() {
        return List.of();
    }

    @Override
    public Sale createNewSale(Sale sale) {
        return null;

    }

    @Override
    public void read(Sale sale) {

    }

    @Override
    public void update(Sale oldSale, Sale newSale) {

    }

    @Override
    public void delete(Sale sale) {

    }
}
