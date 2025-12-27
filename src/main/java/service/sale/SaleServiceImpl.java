package service.sale;

import repository.sale.SaleRepository;

public class SaleServiceImpl implements SaleService{
    private SaleRepository saleRepository;
    public SaleServiceImpl(SaleRepository saleRepository)
    {
        this.saleRepository=saleRepository;
    }
    @Override
    public boolean add_sale() {
        return saleRepository.add_sale();
    }
}
