package commandfactories;

import commands.Command;
import players.Player;
import stocks.Stock;

import java.util.List;

public interface CommandAbstractFactory {
    Command createInitializeCommand(List<Player> players, List<Stock> stocks);

    Command createBuyStocksCommand(int stockNumber, int amount, Player player);

    Command createSellStocksCommand(int stockNumber, int amount, Player player);

    Command createFluctuationPriceCommand(Stock stock);


}
