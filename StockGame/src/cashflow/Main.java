package cashflow;

import commandfactories.CommandAbstractFactory;
import commandfactories.CommandFactory;
import players.HumanPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static StockMarket stockMarket = new StockMarket();
    private static CommandAbstractFactory commandFactory = new
            CommandFactory(new StockAgent(stockMarket));
    private static CashFlowSystem cashFlowSystem = new CashFlowSystem(commandFactory, stockMarket);
    private static SaveManger saveManger = new SaveManger(null);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        System.out.println("==========歡迎來到現金流遊戲==========");
        if (checkLoadSave()) {
            cashFlowSystem.setLoad(true);
        } else {
            saveManger.setUpNewSave();
            namePlayer(getHumanPlayers());
        }
        cashFlowSystem.startGame();
        System.out.println("============遊戲結束============");
    }

    public static boolean checkLoadSave() {
        if (saveManger.checkSave()) {
            System.out.println("偵測到存檔，請問玩家是否要讀取存檔(y/n)");
            String select = input.next();
            select = select.toUpperCase();

            if (saveManger.isLoad(select)) {
                try {
                    Save save = saveManger.loadSave();
                    cashFlowSystem.setUsedCommands(save.getUsedCommands());
                    return true;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static int getHumanPlayers() {
        int select;

        System.out.println("請輸入玩家人數，最多四位");
        select = inputSelect(4, 1);

        return select;
    }


    public static int inputSelect(int maxOption, int lowestOption) {
        int select;

        do {
            select = input.nextInt();


        } while (select > maxOption || select < lowestOption);

        return select;
    }

    public static void namePlayer(int playerAmount) {
        for (int i = 1; i <= playerAmount; i++) {
            System.out.println("玩家 " + i + " 請取名");

            String name = input.next();
            cashFlowSystem.addPlayers(new HumanPlayer(name, i));
        }
    }
}
