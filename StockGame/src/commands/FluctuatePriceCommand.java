package commands;

import stocks.Stock;
import stocks.StockName;

import java.util.ArrayList;
import java.util.List;

public class FluctuatePriceCommand implements Command {
    private transient Stock stock;
    private int stockPrice;
    private StockName stockName;
    private List<Integer> stockPreviousPrices = new ArrayList<>();


    public FluctuatePriceCommand(Stock stock) {
        this.stock = stock;
        stockPrice = stock.getPrice();
        stockName = stock.getName();
        stockPreviousPrices.addAll(stock.getPreviousPrices());
    }

    @Override
    public void execute() {
        stock.fluctuatePrice();
    }

    @Override
    public void undo() {
        stock.setPrice(stockPrice);
        stock.setPreviousPrices(stockPreviousPrices);
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public StockName getStockName() {
        return stockName;
    }
}
