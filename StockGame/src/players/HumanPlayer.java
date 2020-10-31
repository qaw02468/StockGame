package players;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner playerInput = new Scanner(System.in);

    public HumanPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        setPlayerAttribute(PlayerAttribute.HUMAN);
    }

    @Override
    public int makeDecisions() {
        int select;
        System.out.println("玩家選擇要 (1) 購買股票 (2) 賣股票 (3) 結束回合 (4)回上一步 (5)結束遊戲");

        select = input(1, 5);

        return select;
    }

    public int[] selectBuyStocksAmount(int stockMarketStockAmount) {
        int selectStock;
        int selectAmount;

        System.out.println("請輸入股票編號：");
        selectStock = input(1, stockMarketStockAmount);

        System.out.println("請輸入張數");
        selectAmount = input(1, Integer.MAX_VALUE);

        return new int[]{selectStock, selectAmount};
    }

    public int input(int lowestOption, int maxOption) {
        int select;

        do {
            select = playerInput.nextInt();
            if (select > maxOption || select < lowestOption) {
                System.out.println("只能輸入 " + lowestOption + " ~ " + maxOption + " 的數字");
            }
        } while (select < lowestOption || select > maxOption);

        return select;
    }


}
