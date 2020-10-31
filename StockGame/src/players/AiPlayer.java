package players;

public class AiPlayer extends Player {
    public AiPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        setPlayerAttribute(PlayerAttribute.AI);
    }

    @Override
    public int makeDecisions() {

        return (int) (Math.random() * 3) + 1;
    }

    @Override
    public int[] selectBuyStocksAmount(int stockMarketStockAmount) {
        int selectStock = (int) (Math.random() * stockMarketStockAmount) + 1;
        int selectAmount = (int) (Math.random() * 10) + 1;

        return new int[]{selectStock, selectAmount};
    }
}
