package stocks;

import fluctuationpricestrategies.FluctuationPrice;
import fluctuationpricestrategies.FluctuationPriceStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class Stock {
    private StockName name;
    private FluctuationPriceStrategy fluctuatePrice = new FluctuationPrice(this);
    private List<Integer> previousPrices = new ArrayList<>();
    private int price;

    public Stock(int price) {
        this.price = price;

    }

    public void fluctuatePrice() {
        fluctuatePrice.fluctuatePrice();
    }

    public StockName getName() {
        return name;
    }

    public void setName(StockName name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getPreviousPrices() {
        return previousPrices;
    }

    public int getPreviousPricesSize() {
        return previousPrices.size();
    }

    public void addPreviousPrice(int price) {
        previousPrices.add(price);
    }

    public void reducePreviousPrice(int index) {
        previousPrices.remove(index);
    }

    public void setPreviousPrices(List<Integer> previousPrices) {
        this.previousPrices = previousPrices;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
