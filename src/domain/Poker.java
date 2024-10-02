package domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Poker extends JLabel implements MouseListener {
    //属性

    //牌的名字：是一个字符串格式：数字-数字，第一个表示属性 第二个表示大小
    private String name;
    //牌的正反面
    private boolean up;
    //牌是否可点击
    private boolean canClick = false;
    //牌被点击之后的操作
    private boolean clicked = false;

    //空参构造不用，因为牌必须有名字
    /*public Poker() {
    }*/


    //带参构造两个属性即可，因为其他两个都有默认值了
    public Poker(String name, boolean up) {
        this.name = name;
        this.up = up;
        if (this.up == true) {
            //显示正面的成员方法
            turnFront();
        } else {
            //显示反面的成员方法
            turnBack();
        }

        //设置每张牌的大小
        this.setSize(71, 96);

        //设置可以显示出来
        this.setVisible(true);
        this.addMouseListener(this);

    }

    //成员方法1-显示正面
    public void turnFront() {
        this.setIcon(new ImageIcon("D:\\allCode\\JavaProject\\playCard\\src\\image\\poker\\" + this.name + ".png"));
        this.up = true;
    }

    //成员方法2-显示背面
    public void turnBack() {
        this.setIcon(new ImageIcon("D:\\allCode\\JavaProject\\playCard\\src\\image\\poker\\rear.png"));
        this.up = false;
    }
    //成员方法3-显示点击

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return up
     */
    public boolean isUp() {
        return up;
    }

    /**
     * 设置
     *
     * @param up
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * 获取
     *
     * @return canClick
     */
    public boolean isCanClick() {
        return canClick;
    }

    /**
     * 设置
     *
     * @param canClick
     */
    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    /**
     * 获取
     *
     * @return clicked
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * 设置
     *
     * @param clicked
     */
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String toString() {
        return "Poker{name = " + name + ", up = " + up + ", canClick = " + canClick + ", clicked = " + clicked + "}";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int step = 0;
        //先判断当前的牌是否可以点击
        if (this.canClick) {
            //如果可以点击
            if (clicked) {
                //已经被点击
                //降落
                step = -20;
            } else {
                //没有被点击过
                step = 20;
            }
            //修改一下位置
            Point p = this.getLocation();
            p.y += step;
            this.setLocation(p);
            clicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
