package players;

import stocks.StockCheck;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<StockCheck> heldStockChecks = new ArrayList<>();
    private int money ;
    private String name;
    private int playerNumber;
    private PlayerAttribute playerAttribute;

    public Player(String name,int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public abstract int makeDecisions();

    public abstract int[] selectBuyStocksAmount(int stockMarketStockAmount);

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addStockCheck(StockCheck stockCheck) {
        heldStockChecks.add(stockCheck);
    }

    public void spendMoney(int expense) {
        money -= expense;
    }

    public void addMoney(int money) {
        this.money -= money;
    }

    public List<StockCheck> getHeldStockChecks() {
        return heldStockChecks;
    }

    public StockCheck getStockCheck(int number) {
        return heldStockChecks.get(number);
    }

    public int getStockCheckAmount(int number){
        return heldStockChecks.get(number).getAmount();
    }

    public int stockSpeciesAmount() {
        return heldStockChecks.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeldStockChecks(List<StockCheck> heldStockChecks) {
        this.heldStockChecks = heldStockChecks;
    }

    public PlayerAttribute getPlayerAttribute() {
        return playerAttribute;
    }

    public void setPlayerAttribute(PlayerAttribute playerAttribute) {
        this.playerAttribute = playerAttribute;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

}
