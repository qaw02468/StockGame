package cashflow;

import stocks.BraveScreen;
import stocks.HonHai;
import stocks.Htc;
import stocks.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockMarket {
    private List<Stock> stocks = new ArrayList<>();

    public void setUpStocks() {
        stocks.add(new HonHai(300));
        stocks.add(new BraveScreen(250));
        stocks.add(new Htc(100));
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Stock getStock(int number) {
        return stocks.get(number - 1);
    }

}
