package commandfactories;

import cashflow.StockAgent;
import commands.*;
import players.Player;
import stocks.Stock;

import java.util.List;

public class CommandFactory implements CommandAbstractFactory {
    private StockAgent stockAgent;


    public CommandFactory(StockAgent stockAgent) {
        this.stockAgent = stockAgent;

    }

    @Override
    public Command createInitializeCommand(List<Player> players, List<Stock> stocks) {
        return new InitializeCommand(players,stocks);
    }

    @Override
    public Command createBuyStocksCommand(int stockNumber, int amount, Player player) {
        return new BuyStocksCommand(stockNumber, amount, player, stockAgent);
    }

    @Override
    public Command createSellStocksCommand(int stockNumber, int amount, Player player) {
        return new SellStocksCommand(stockNumber, amount, player, stockAgent);
    }

    @Override
    public Command createFluctuationPriceCommand(Stock stock) {
        return new FluctuatePriceCommand(stock);
    }

}
