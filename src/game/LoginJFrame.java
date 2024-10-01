package game;

import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class LoginJFrame extends JFrame implements MouseListener {
    static ArrayList<User> allUsers = new ArrayList<>();

    //在静态代码块存储一些user
    static {
        allUsers.add(new User("zhangsan", "123"));
        allUsers.add(new User("lisi", "1234"));
    }
    //成员变量
    JLabel realcheckNumber = new JLabel(getCheckNumber());
    JButton loginButton = new JButton("登录");
    JButton registerButton = new JButton("注册");
    JTextField checkNumberField = new JTextField(20);
    JTextField userNameField = new JTextField(20);
    JPasswordField userPasswordField = new JPasswordField(20);

    public LoginJFrame() {
        //初始化界面
        initJFrame();
        //初始化组件
        initView();
        //让当前界面显示出来
        this.setVisible(true);
    }

    //初始化界面的函数
    private void initView() {
        //设置背景图片
        //设置组件——组件的大小、位置

        //首先定义这些组件，很大可能这些组件要设置在成员变量处
        JLabel userNameLabel = new JLabel("用户名:");
        JLabel userPasswordLabel = new JLabel("密码:");
        JLabel checkNumber = new JLabel("验证码:");

        JLabel bg = new JLabel(new ImageIcon("src\\image\\loginbg.png"));
        JLabel loginButtonLabel = new JLabel(new ImageIcon("src\\image\\loginButton.png"));


        //设置组件的大小&位置
        //可以直接用一个setBouns函数来设置大小和位置
        userNameLabel.setBounds(160,60,70,25);
        userNameField.setBounds(230,60,230,25);
        userPasswordLabel.setBounds(160,120,70,25);
        userPasswordField.setBounds(230,120,230,25);
        checkNumber.setBounds(160,180,70,25);
        checkNumberField.setBounds(230,180,150,25);
        realcheckNumber.setBounds(400,180,50,25);
        registerButton.setBounds(160,280,128,47);
        registerButton.setIcon(new ImageIcon("src\\image\\registerButton.png"));
        loginButton.setBounds(390,280,128,47);
        loginButton.setIcon(new ImageIcon("src\\image\\loginButton.png"));
        bg.setBounds(0,0,633,423);

        //设置组件的格式
        Font usernameFont = new Font(null,1,16);
        userNameLabel.setFont(usernameFont);
        Font passwordFont = new Font(null,1,16);
        userPasswordLabel.setFont(passwordFont);
        Font checkNumberFont = new Font(null,1,16);
        checkNumber.setFont(checkNumberFont);
        Font realcheckNumberFont = new Font(null,1,16);
        realcheckNumber.setFont(realcheckNumberFont);
        //去除按钮的边界
        loginButton.setBorderPainted(false);
        registerButton.setBorderPainted(false);
        //去除按钮的背景
        loginButton.setContentAreaFilled(false);
        registerButton.setContentAreaFilled(false);

        //把组件添加到界面上去
        this.getContentPane().add(userNameLabel);
        this.getContentPane().add(userNameField);
        this.getContentPane().add(userPasswordLabel);
        this.getContentPane().add(userPasswordField);
        this.getContentPane().add(checkNumber);
        this.getContentPane().add(checkNumberField);
        this.getContentPane().add(realcheckNumber);
        this.getContentPane().add(registerButton);
        this.getContentPane().add(loginButton);
        this.getContentPane().add(bg);


        //各组件添加点击事件
        //验证码label点击之后进行更新新的验证码
        realcheckNumber.addMouseListener(this);
        loginButton.addMouseListener(this);
        registerButton.addMouseListener(this);

    }

    //初始化组件的函数
    private void initJFrame() {
        //大小
        this.setSize(633, 423);
        //标题
        this.setTitle("斗地主游戏 V1.0登录");
        //居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置置顶
        this.setAlwaysOnTop(true);
        //取消内部默认布局，组件可以按照我们的想法来摆，不用根据JFrame设定的来
        this.setLayout(null);
    }

    //生成验证码的函数
    public String getCheckNumber(){
        char[] ch =new char[62];
        for (int i = 0; i < ch.length; i++) {
            if(i<=9){
                ch[i]=(char)('0'+i);
            }
            else if(i<36){
                ch[i]=(char)('a'+i-10);
            }else{
                ch[i]=(char)('A'+i-36);
            }
        }
        StringBuilder builder = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int index = rand.nextInt(ch.length);
            builder.append(ch[index]);
        }
        return builder.toString();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object ob = e.getSource();
        if(ob == realcheckNumber){
            //如果触发验证码的点击事件，验证码进行更新
            checkNumberField.setText(getCheckNumber());
        }else if(ob == loginButton){
            //如果登录按钮被触发，验证输入的用户名密码是否正确
            String nowUserid = userNameField.getText();
            String nowPassword = userPasswordField.getText();
            String nowCheckNumber = checkNumberField.getText();

            checkUser(nowUserid,nowPassword,nowCheckNumber);

        }else if(ob == registerButton){
            //如果注册按钮被触发

        }
    }

    private boolean checkUser(String nowUserid, String nowPassword, String nowCheckNumber) {
        for (int i = 0; i < allUsers.size(); i++) {
            if(nowUserid.equals(allUsers.get(i).getId())){
                if(nowPassword.equals(allUsers.get(i).getPassword())){
                    if(nowCheckNumber.equals(checkNumberField.getText())){
                        showDialog("登录成功！");
                    }else{
                        showDialog("验证码不正确！");
                        realcheckNumber.setText(getCheckNumber());
                    }
                }else{
                    showDialog("用户名或者密码不正确");
                }
            }
            showDialog("用户名或者密码不正确");
        }
        return true;
    }

    //显示弹窗的函数，指定弹窗中显示的文字
    public void showDialog(String content){
        JDialog jd = new JDialog();
        jd.setSize(200, 150);
        jd.setAlwaysOnTop(true);
        //让弹框居中
        jd.setLocationRelativeTo(null);
        //弹窗不关闭无法进行下面的操作
        jd.setModal(true);

        //弹窗里面用label进行显示文字
        JLabel jLabel = new JLabel(content);
        jLabel.setBounds(0,0,200,500);
        jd.getContentPane().add(jLabel);

        //弹窗显示出来
        jd.setVisible(true);
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
