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
    public void createNewSale(Sale sale) {

    }
}
