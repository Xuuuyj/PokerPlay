import domain.Poker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameJFrame extends javax.swing.JFrame implements ActionListener {
    //此类为游戏的页面类
    //使用一个Container来装所有的容器
    public static Container container = new Container();

    //成员变量
    //牌盒
    ArrayList<Poker> pokerBox = new ArrayList<>();

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
    }

    private void initCard() {
        //先把牌加入到牌盒中

    }

    private void InitView() {
        //添加组件，这些组件是比较固定的，都会有的，比如说按钮以及倒计时
        //创建抢地主按钮
        JButton robBut = new JButton("抢地主");
        //设置大小位置
        robBut.setBounds(320, 400, 75, 20);
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























