package cashflow;

import commandfactories.CommandAbstractFactory;
import commands.*;
import players.AiPlayer;
import players.HumanPlayer;
import players.Player;
import stocks.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CashFlowSystem implements Serializable {
    private boolean isLoad = false;
    private boolean isGameOver = false;
    private Player currentRoundPlayer;
    private List<Stock> stocks;
    private StockMarket stockMarket;
    private List<Player> players = new ArrayList<>();
    private Stack<Command> usedCommands = new Stack<>();
    private CommandAbstractFactory commandFactory;
    private SaveManger saveManger = new SaveManger(usedCommands);
    private int fluctuationCount = 0;
    private boolean isFluctuationCount = true;
    private final int BUY_STOCK = 1;
    private final int SELL_STOCK = 2;
    private final int END_THIS_ROUND = 3;
    private final int BACK_PREVIOUS_STEP = 4;
    private final int END_GAME = 5;

    public CashFlowSystem(CommandAbstractFactory commandAbstractFactory, StockMarket stockMarket) {
        this.stockMarket = stockMarket;
        this.commandFactory = commandAbstractFactory;
    }

    public void startGame() {
        initialization();

        int turn = -1;
        while (!isGameOver) {
            if (checkIsWin()) {
                printRank();
                saveManger.deleteSave();
                isGameOver = true;
                return;
            }
            fluctuationPrice();
            turn = (turn + 1) % players.size();
            currentRoundPlayer = players.get(turn);

            executePlayerSelect(currentRoundPlayer.makeDecisions());
        }
    }

    public void initialization() {
        if (isLoad) {
            loadGameRestoreGame();
            return;
        }
        if (!checkPlayersAmountIsFour()) {
            setUpAi();
        }
        stockMarket.setUpStocks();
        stocks = stockMarket.getStocks();
        setUpPlayerStocksAndMoney();

    }

    public void printPlayerState(Player player) {
        System.out.println("玩家 : " + player.getName());
        System.out.println("手頭現金 : " + player.getMoney());
        System.out.println("=============擁有股票=============");

        for (StockCheck stockCheck : currentRoundPlayer.getHeldStockChecks()) {
            System.out.println(stockCheck.getStock().getName() + " " + (stockCheck.getAmount()) + "張");
        }
    }

    public void executePlayerSelect(int playerSelect) {
        printPlayerState(currentRoundPlayer);
        isFluctuationCount = true;
        int[] playerSelectStockNumberAndAmount;
        int selectStockNumber;
        int selectAmount;

        switch (playerSelect) {
            case BUY_STOCK:
                printStockState();
                playerSelectStockNumberAndAmount = currentRoundPlayer.selectBuyStocksAmount(stocks.size());
                selectStockNumber = playerSelectStockNumberAndAmount[0];
                selectAmount = playerSelectStockNumberAndAmount[1];

                if (checkMoneyIsEnough(selectStockNumber, selectAmount)) {
                    buyStocks(selectStockNumber, selectAmount);
                }
                break;
            case SELL_STOCK:
                printStockState();
                playerSelectStockNumberAndAmount = currentRoundPlayer.selectBuyStocksAmount(stocks.size());
                selectStockNumber = playerSelectStockNumberAndAmount[0];
                selectAmount = playerSelectStockNumberAndAmount[1];

                if (checkStockAmountIsEnough(selectStockNumber, selectAmount)) {
                    sellStocks(selectStockNumber, selectAmount);
                }
                break;
            case END_THIS_ROUND:
                return;
            case BACK_PREVIOUS_STEP:
                if (usedCommands.isEmpty()) {
                    System.out.println("無法回上一步了");
                } else {
                    useUndoCommand();
                    isFluctuationCount = false;
                }
                break;
            case END_GAME:
                saveGameThenExit();
                return;
        }
        executePlayerSelect(currentRoundPlayer.makeDecisions());
    }

    private void loadGameRestoreGame() {
        InitializeCommand initializeCommand = (InitializeCommand) usedCommands.pop();

        loadSaveSetAllHumanPlayer();
        stockMarket.setUpStocks();
        stocks = stockMarket.getStocks();
        setUpPlayerStocksAndMoney();

        initializeCommand.setPlayers(players);
        initializeCommand.setStocks(stocks);
        initializeCommand.undo();
    }

    private void saveGameThenExit() {
        Command initializeCommand = commandFactory.createInitializeCommand(players, stocks);
        initializeCommand.execute();
        usedCommands.push(initializeCommand);
        isGameOver = true;
        saveManger.save();
    }

    public void setUpPlayerStocksAndMoney() {
        for (Player player : players) {
            player.setMoney(6000);
            for (Stock stock : stocks) {
                player.addStockCheck(new StockCheck(stock, 0));
            }
        }
    }

    public void printStockState() {
        for (int i = 1; i <= stocks.size(); i++) {
            Stock stock = stocks.get(i - 1);

            System.out.println("(" + i + ") " + stock.getName() + stock.getPrice() + "$  →" +
                    stock.getPreviousPrices());
        }
    }

    public void printRank() {
        System.out.println("==========遊戲排名==========");
        players.sort((o1, o2) -> o2.getMoney() - o1.getMoney());

        for (int i = 1; i <= players.size(); i++) {
            System.out.println("第 " + i + " 名 " +  players.get(i).getName() + " 總共得到了 " +
                    players.get(i).getMoney() + " 元 ");
        }
    }

    public void buyStocks(int stockNumber, int amount) {
        Command buyStocksCommand = commandFactory.createBuyStocksCommand(stockNumber, amount, currentRoundPlayer);
        buyStocksCommand.execute();

        usedCommands.push(buyStocksCommand);
    }

    public void sellStocks(int stockNumber, int amount) {
        Command sellStocksCommand = commandFactory.createSellStocksCommand(stockNumber, amount, currentRoundPlayer);
        sellStocksCommand.execute();

        usedCommands.push(sellStocksCommand);
    }

    public void fluctuationPrice() {
        if (isFluctuationCount = true) {
            fluctuationCount++;
        }
        if (fluctuationCount % 4 == 0) {
            for (Stock stock : stocks) {
                Command fluctuationPriceCommand = commandFactory.createFluctuationPriceCommand(stock);
                fluctuationPriceCommand.execute();

                usedCommands.push(fluctuationPriceCommand);
            }
        }
    }

    public void loadSaveSetAllHumanPlayer() {
        for (int i = 1; i <= 4; i++) {
            players.add(new HumanPlayer("HumanPlayer", i));
        }
    }

    public void setUpAi() {
        int humanPlayersAmount = players.size();

        for (int i = 1; i <= 4 - humanPlayersAmount; i++) {
            players.add(new AiPlayer("電腦" + i, i + humanPlayersAmount));
        }
    }

    public boolean checkMoneyIsEnough(int stockNumber, int amount) {
        Stock stock = stocks.get(stockNumber - 1);
        if (stock.getPrice() * amount <= currentRoundPlayer.getMoney()) {
            return true;
        } else {
            System.out.println("金錢不足，請重新選擇");
            return false;
        }
    }

    public boolean checkStockAmountIsEnough(int stockNumber, int amount) {
        if (currentRoundPlayer.getStockCheck(stockNumber - 1).getAmount() >= amount) {
            return true;
        } else {
            System.out.println("張數不足，請重新選擇");
            return false;
        }
    }

    public boolean checkIsWin() {
        for (Player player : players) {
            if (player.getMoney() == 20000) {
                return true;
            }
        }
        return false;
    }

    public void useUndoCommand() {
        Command undoCommand = usedCommands.pop();

        if (undoCommand instanceof BuyStocksCommand) {
            buyStocksUndo(undoCommand);
        } else if (undoCommand instanceof SellStocksCommand) {
            sellStocksUndo(undoCommand);
        } else {
            fluctuationPriceUndo(undoCommand);
        }
        undoCommand.undo();
    }

    public void buyStocksUndo(Command undoCommand) {
        BuyStocksCommand buyStocksCommand = (BuyStocksCommand) undoCommand;

        for (Player player : players) {
            int playerNumber = player.getPlayerNumber();

            if (playerNumber == buyStocksCommand.getPlayerNumber()) {
                buyStocksCommand.setPlayer(player);
                return;
            }
        }
    }

    public void sellStocksUndo(Command undoCommand) {
        SellStocksCommand sellStocksCommand = (SellStocksCommand) undoCommand;
        for (Player player : players) {
            int playerNumber = player.getPlayerNumber();

            if (playerNumber == sellStocksCommand.getPlayerNumber()) {
                sellStocksCommand.setPlayer(player);
                return;
            }
        }
    }

    public void fluctuationPriceUndo(Command undoCommand) {
        FluctuatePriceCommand fluctuatePriceCommand = (FluctuatePriceCommand) undoCommand;
        for (Stock stock : stocks) {
            if (stock.getName().equals(fluctuatePriceCommand.getStockName())) {
                fluctuatePriceCommand.setStock(stock);
                return;
            }
        }
    }

    public boolean checkPlayersAmountIsFour() {
        return players.size() == 4;
    }

    public void addPlayers(Player player) {
        players.add(player);
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public void setUsedCommands(Stack<Command> usedCommands) {
        this.usedCommands = usedCommands;
    }
}

