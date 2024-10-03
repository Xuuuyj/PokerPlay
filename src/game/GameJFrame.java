import domain.Poker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameJFrame extends javax.swing.JFrame implements ActionListener {
    //此类为游戏的页面类
    //使用一个Container来装所有的容器
    public static Container container = new Container();

    //成员变量
    //牌盒
    ArrayList<Poker> pokerBox = new ArrayList<>();
    //三张底牌
    ArrayList<Poker> lordList = new ArrayList<>();

    //每个人目前要打出的牌，嵌套集合
    //大集合中有三个小集合
    //小集合中装着每一个玩家当前要出的牌
    //0索引：左边的电脑玩家
    //1索引：中间的自己
    //2索引：右边的电脑玩家
    ArrayList<ArrayList<Poker>> currentPoker = new ArrayList<>();

    //每个人手中还有的牌
    ArrayList<ArrayList<Poker>> stillPoker = new ArrayList<>();

    //每个人打出去的牌
    ArrayList<ArrayList<Poker>> playedPoker = new ArrayList<>();

    //抢地主或者不抢地主的按钮集合
    JButton[] landlord = new JButton[2];

    //出牌或者跳过的按钮
    JButton[] publishCard = new JButton[2];

    //地主的标志
    JLabel LordLabel = new JLabel(new ImageIcon("D:\\allCode\\JavaProject\\playCard\\src\\image\\poker\\dizhu.png"));

    //三个玩家前方的文本提示
    //0索引：左边的电脑玩家
    //1索引：中间的自己
    //2索引：右边的电脑玩家
    JTextField[] timeCount = new JTextField[3];

    GameJFrame() {
        //构造函数
        //设置任务栏图标
        setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\allCode\\JavaProject\\playCard\\src\\image\\poker\\dizhu.png"));
        //初始化界面
        InitJFrame();
        //初始化组件
        InitView();
        //显示界面
        //先显示出来界面在进行发牌
        this.setVisible(true);

        //准备牌:洗牌、发牌、排序
        initCard();

        //开始游戏
        //展示抢地主和不抢地主两个按钮并且再创建三个集合用来装三个玩家准备要出的牌
        initGame();

    }

    private void initGame() {
        //打游戏前的准备工作
        //创建三个集合来装准备要出的牌
        ArrayList<Poker> palyer0 = new ArrayList<>();
        ArrayList<Poker> palyer1 = new ArrayList<>();
        ArrayList<Poker> palyer2 = new ArrayList<>();

        currentPoker.add(palyer0);
        currentPoker.add(palyer1);
        currentPoker.add(palyer2);
        //显示抢地主不抢地主按钮
        for (int i = 0; i < landlord.length; i++) {
            landlord[i].setVisible(true);
        }
        //展示倒计时文本
        for (int i = 0; i < timeCount.length; i++) {
            timeCount[i].setText("倒计时30秒");
            timeCount[i].setVisible(true);
        }
    }

    private void initCard() {
        //准备牌先把牌加入到牌盒中
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 13; j++) {
                if (i == 5 && j > 2) {
                    break;
                }
                Poker p = new Poker(i + "-" + j, false);
                p.setLocation(350, 150);
                container.add(p);
                pokerBox.add(p);
            }
        }
        //洗牌
        Collections.shuffle(pokerBox);
        //发牌
        //创建三个集合用来装分发下来的牌，并且把三个集合放到大集合里面进行管理
        ArrayList<Poker> player0 = new ArrayList<>();
        ArrayList<Poker> player1 = new ArrayList<>();
        ArrayList<Poker> player2 = new ArrayList<>();

        //遍历牌盒进行发牌
        //并且将牌的位置进行移动
        for (int i = 0; i < pokerBox.size(); i++) {
            Poker p = pokerBox.get(i);
            if (i <= 2) {
                //三张底牌拿出来
                lordList.add(p);
                //同时底牌要进行移动，在common文件夹中有移动的动画
                Common.move(p, p.getLocation(), new Point(270 + 75 * i, 10));
            } else {
                if (i % 3 == 0) {
                    player0.add(p);
                    Common.move(p, p.getLocation(), new Point(50, 60 + 5 * i));
                }
                if (i % 3 == 1) {
                    //给中间的自己发牌,注意自己的牌展示正面
                    p.turnFront();
                    player1.add(p);
                    Common.move(p, p.getLocation(), new Point(180 + 7 * i, 450));
                }
                if (i % 3 == 2) {
                    player2.add(p);
                    Common.move(p, p.getLocation(), new Point(700, 5 * i + 60));
                }
            }
            //把当前的牌至于最顶端，这样就会有牌依次错开且叠起来的效果
            container.setComponentZOrder(p, 0);

        }
        //把每个人手中牌的ArrayList添加到嵌套循环中
        stillPoker.add(player0);
        stillPoker.add(player1);
        stillPoker.add(player2);

        //排序
        for (int i = 0; i < stillPoker.size(); i++) {
            //排序之后记得重新放置牌的顺序

            sortPoker(stillPoker.get(i));
            //重新放置牌的顺序，利用common中的动画属性
            Common.rePosition(this, stillPoker.get(i), i);
        }


    }

    public void sortPoker(ArrayList<Poker> pokers) {
        //通过价值的方式进行排序
        Collections.sort(pokers, new Comparator<Poker>() {
            @Override
            public int compare(Poker p1, Poker p2) {
                String name1 = p1.getName();
                String name2 = p2.getName();
                int value1 = getValue(name1);
                int color1 = Integer.valueOf(name1.substring(0, 1));
                int value2 = getValue(name2);
                int color2 = Integer.valueOf(name2.substring(0, 1));
                int result = value2 - value1;
                result = result == 0 ? color2 - color1 : result;
                return result;

            }
        });

    }
    //!!!!严重注意踩雷点：字符串不要用==进行比较啊，要用equals
    public int getValue(String PokerName) {
        int color = Integer.valueOf(PokerName.substring(0, 1));
        int value= Integer.valueOf(PokerName.substring( 2));

        if (color==5) {
            if (value ==1 ) {
                System.out.println(PokerName);
                return 16;
            } else {
                System.out.println(PokerName);
                return 17;
            }
        }else{
            if (value ==1 ) {
                System.out.println(PokerName);
                return 14;
            }
            if (value == 2) {
                System.out.println(PokerName);
                return 15;
            }
        }


        return value;

    }

    //添加组件
    public void InitView() {
        //创建抢地主的按钮
        JButton robBut = new JButton("抢地主");
        //设置位置
        robBut.setBounds(320, 400, 75, 20);
        //添加点击事件
        robBut.addActionListener(this);
        //设置隐藏
        robBut.setVisible(false);
        //添加到数组中统一管理
        landlord[0] = robBut;
        //添加到界面中
        container.add(robBut);
        //添加按钮到container中
        container.add(robBut);
        //将抢地主按钮添加到数组中
        landlord[0] = robBut;
        //添加点击事件
        robBut.addActionListener(this);
        //设置隐藏
        robBut.setVisible(false);

        //创建不抢地主按钮
        JButton noBut = new JButton("不抢");
        //设置按钮的大小以及位置
        noBut.setBounds(420, 400, 75, 20);
        container.add(noBut);
        landlord[1] = noBut;
        noBut.addActionListener(this);
        noBut.setVisible(false);

        //创建出牌按钮
        JButton outCardBut = new JButton();
        outCardBut.setBounds(320, 400, 60, 20);
        outCardBut.addActionListener(this);
        outCardBut.setVisible(false);
        publishCard[0] = outCardBut;
        container.add(outCardBut);

        //创建不出牌按钮
        JButton noCardBut = new JButton("要不起");
        noCardBut.setBounds(420, 400, 60, 20);
        noCardBut.addActionListener(this);
        noCardBut.setVisible(false);
        publishCard[1] = noCardBut;
        container.add(noCardBut);

        //创建每个玩家前面的提示文字，即倒计时
        //左边的玩家0 自己为1 右边的玩家2

        for (int i = 0; i < timeCount.length; i++) {
            timeCount[i] = new JTextField("倒计时:");
            //设置不可编辑
            timeCount[i].setEditable(false);
            //设置可见
            timeCount[i].setVisible(false);

            container.add(timeCount[i]);
        }
        timeCount[0].setBounds(140, 230, 60, 20);
        timeCount[1].setBounds(374, 360, 60, 20);
        timeCount[2].setBounds(620, 230, 60, 20);

        //创建地主图标
        LordLabel.setVisible(false);
        LordLabel.setSize(40, 40);
        container.add(LordLabel);

    }

    private void InitJFrame() {

        //设置界面标题
        this.setTitle("斗地主");
        //设置大小
        this.setSize(830, 620);
        //设置居中
        this.setLocationRelativeTo(null);
        //设置显示在上层
        this.setAlwaysOnTop(true);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置无法调节
        this.setResizable(false);

        //获取界面中的隐藏容器，以后直接用无需再次调用方法获取了
        container = this.getContentPane();
        //取消内部默认的居中放置
        container.setLayout(null);
        //设置背景颜色
        container.setBackground(Color.gray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}























