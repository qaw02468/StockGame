package cashflow;

import players.Player;
import stocks.StockCheck;
import stocks.StockName;

public class StockAgent {
    private StockMarket stockMarket;

    public StockAgent(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    public void buyStocks(int stockNumber, int amount, Player player) {
        StockCheck stockCheck = new StockCheck(stockMarket.getStock(stockNumber), amount);
        int stockMoney = stockMarket.getStock(stockNumber).getPrice();
        StockName playerBuyStockName;

        for (StockCheck playerStockCheck : player.getHeldStockChecks()) {
            playerBuyStockName = playerStockCheck.getStock().getName();

            if (playerBuyStockName.equals(stockCheck.getStock().getName())) {
                playerStockCheck.addAmount(amount);
                player.spendMoney(stockMoney * amount);
                return;
            }
        }
        player.addStockCheck(stockCheck);
        player.spendMoney(stockMoney * amount);
    }

    public void sellStocks(int stockNumber, int amount, Player player) {
        int price = stockMarket.getStock(stockNumber).getPrice();

        player.getStockCheck(stockNumber - 1).reduceAmount(amount);
        player.addMoney(price * amount);
    }
}
