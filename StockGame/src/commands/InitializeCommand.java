package commands;

import players.AiPlayer;
import players.Player;
import players.PlayerAttribute;
import stocks.Stock;

import java.util.ArrayList;
import java.util.List;

public class InitializeCommand implements Command {
    private transient List<Player> players;
    private transient List<Stock> stocks;
    private int stockSpeciesAmount;
    private List<String> playersName = new ArrayList<>();
    private List<PlayerAttribute> playersAttributes = new ArrayList<>();
    private List<Integer> playersMoney = new ArrayList<>();
    private List<Integer> playersStockAmount = new ArrayList<>();
    private List<Integer> stocksPrice = new ArrayList<>();
    private List<List<Integer>> stocksPreviousPrices = new ArrayList<>();

    public InitializeCommand(List<Player> players, List<Stock> stocks) {
        this.players = players;
        this.stocks = stocks;
    }

    @Override
    public void execute() {
        stockSpeciesAmount = players.get(0).stockSpeciesAmount();

        for (Player player : players) {
            playersName.add(player.getName());
            playersMoney.add(player.getMoney());
            playersAttributes.add(player.getPlayerAttribute());
            for (int i = 0; i < stockSpeciesAmount; i++) {
                playersStockAmount.add(player.getStockCheck(i).getAmount());
            }
        }
        for (Stock stock : stocks) {
            stocksPrice.add(stock.getPrice());
            stocksPreviousPrices.add(stock.getPreviousPrices());
        }
    }

    @Override
    public void undo() {
        int stockIndex = 0;
        int playerIndex = 0;
        int stockPriceIndex = 0;

        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).getPlayerAttribute().equals(playersAttributes.get(i))) {
                if (playersAttributes.get(i).equals(PlayerAttribute.AI)) {
                    players.set(i, new AiPlayer("電腦", i + 1));
                    players.get(i).setHeldStockChecks(players.get(0).getHeldStockChecks());
                }
            }
        }

        for (Player player : players) {

            player.setName(playersName.get(playerIndex));
            player.setMoney(playersMoney.get(playerIndex++));
            for (int i = 0; i < stockSpeciesAmount; i++) {
                player.getStockCheck(i).setAmount(playersStockAmount.get(stockIndex++));
            }
        }
        for (Stock stock : stocks) {
            stock.setPrice(stocksPrice.get(stockPriceIndex));
            stock.setPreviousPrices(stocksPreviousPrices.get(stockPriceIndex++));
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
