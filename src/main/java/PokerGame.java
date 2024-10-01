import java.util.*;

public class PokerGame {
    //1.准备牌
    //注意准备牌应该写在构造方法外边，因为无论开多少次游戏，我们就只使用一副牌
    //解决办法：静态代码块，随着类的加载而加载，并且只加载一次
    static ArrayList<String> porkBox = new ArrayList<>();
    static HashMap<String, Integer> hm = new HashMap();

    static {
        String[] color = {"♥", "♠", "♣", "♦"};
        String[] num = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        //ArrayList做牌盒
        for (int i = 0; i < num.length; i++) {
            for (int i1 = 0; i1 < color.length; i1++) {
                String card = color[i1] + num[i];
                porkBox.add(card);
            }
        }
        porkBox.add(" 大王");
        porkBox.add(" 小王");
        System.out.println(porkBox);
        hm.put("J", 11);
        hm.put("Q", 12);
        hm.put("K", 13);
        hm.put("A", 14);
        hm.put("2", 15);
        hm.put("小王", 16);
        hm.put("大王", 17);
    }

    PokerGame() {
        //2.洗牌-在构造方法里面进行，每新new一个对象就加载一次
        Collections.shuffle(porkBox);
        //System.out.println(porkBox);
        //3发牌-取余，前三张牌直接拿出来作为底牌
        ArrayList<String> lord = new ArrayList<>();
        ArrayList<String> player1 = new ArrayList<>();
        ArrayList<String> player2 = new ArrayList<>();
        ArrayList<String> player3 = new ArrayList<>();
        for (int i = 0; i < porkBox.size(); i++) {
            if (i <= 2) {
                lord.add(porkBox.get(i));
            }
            if (i % 3 == 0) {
                player1.add(porkBox.get(i));
            }
            if (i % 3 == 1) {
                player2.add(porkBox.get(i));
            }
            if (i % 3 == 2) {
                player3.add(porkBox.get(i));
            }
        }
        //4.看牌
        lookPoker("lord", lord);
        lookPoker("player1", player1);
        lookPoker("player2", player2);
        lookPoker("player3", player3);

        //5.排序
        sortCard(lord);
        sortCard(player1);
        sortCard(player2);
        sortCard(player3);

        //排序完看牌
        lookPoker("lord", lord);
        lookPoker("player1", player1);
        lookPoker("player2", player2);
        lookPoker("player3", player3);

    }

    //3.看牌
    public void lookPoker(String name, ArrayList<String> player) {
        StringJoiner sj = new StringJoiner(",");
        for (String s : player) {
            sj.add(s);
        }
        System.out.println(name + "：" + sj.toString());
    }

    //排序-利用牌的价值排序
    public void sortCard(ArrayList<String> player) {
        Collections.sort(player, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = getValue(o1) - getValue(o2);
                result = result ==0 ?o1.substring(0,1).compareTo(o2.substring(0,1)):result;
                return result;
            }
        });
    }

    //获得牌对应的价值
    public int getValue(String s) {
        s = s.substring(1);
        if(hm.containsKey(s)) {
            return hm.get(s);
        }else{
            return Integer.parseInt(s);
        }
    }

}
