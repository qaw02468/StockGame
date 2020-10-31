package fluctuationpricestrategies;

import stocks.Stock;

public class FluctuationPrice implements FluctuationPriceStrategy {
    Stock stock;

    public FluctuationPrice(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void fluctuatePrice() {
        int changeRange = (int) (Math.random() * 50);
        int randomSelect = (int) (Math.random() * 2) + 1;
        int stockPrice = stock.getPrice();

        if (randomSelect == 1) {
            stock.setPrice(stockPrice + changeRange);
            System.out.println(stock.getName() + " 上漲了 " + changeRange + " 元");
        } else {
            if (stock.getPrice() - changeRange >= 0) {
                stock.setPrice(stockPrice - changeRange);
                System.out.println(stock.getName() + " 下跌了 " + changeRange + " 元");
            } else {
                stock.setPrice(0);
            }
        }
        setUpPreviousPrices(stockPrice, changeRange);
    }

    public void setUpPreviousPrices(int stockPrice, int changeRange) {
        if (stock.getPreviousPricesSize() >= 5) {
            stock.reducePreviousPrice(0);
        }
        stock.addPreviousPrice(stockPrice + changeRange);
    }
}
