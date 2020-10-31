package commands;

import cashflow.StockAgent;
import players.Player;

public class SellStocksCommand implements Command {
    private transient StockAgent stockAgent;
    private transient Player player;
    private int playerStockAmount;
    private int playerMoney;
    private int stockNumber;
    private int stockAmount;
    private int playerNumber;

    public SellStocksCommand(int stockNumber, int stockAmount, Player player, StockAgent stockAgent) {
        this.stockNumber = stockNumber;
        this.stockAmount = stockAmount;
        this.player = player;
        this.stockAgent = stockAgent;
        playerMoney = player.getMoney();
        playerStockAmount = player.getStockCheckAmount(stockNumber - 1);
        playerNumber = player.getPlayerNumber();
    }

    @Override
    public void execute() {
        stockAgent.sellStocks(stockNumber, stockAmount, player);
    }

    @Override
    public void undo() {
        player.setMoney(playerMoney);
        player.getStockCheck(stockNumber - 1).setAmount(playerStockAmount);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public int getPlayerNumber() {
        return playerNumber;
    }

}
