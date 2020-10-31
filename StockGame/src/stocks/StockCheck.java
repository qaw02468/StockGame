package stocks;

import java.io.Serializable;

public class StockCheck {
    private Stock stock;
    private int amount;

    public StockCheck(Stock stock, int amount) {
        this.stock = stock;
        this.amount = amount;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void reduceAmount(int amount) {
        this.amount -= amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
