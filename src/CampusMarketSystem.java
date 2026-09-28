import javax.swing.*;
import java.awt.*;

public class CampusMarketSystem {
    public static void main(String[] args) {
        JFrame frame =new JFrame("校园市场交易管理系统");
        frame.setSize(500,350);

        //关闭窗口时退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel=new JPanel();

        panel.setLayout(new FlowLayout());

        //创建文本 用户名密码
        JLabel usernameLabel=new JLabel("用户名");
        JLabel passwordLabel=new JLabel("密码");
        //创建文本框 用户名密码
        JTextField usernameField=new JTextField(20);
        JPasswordField passwordField=new JPasswordField(20);
        //创建按钮
        JButton loginButton=new JButton("登录");
        JButton registerButton=new JButton("注册");

        //添加组件到面板  用户名
        panel.add(usernameLabel);
        panel.add(usernameField);
        //密码
        panel.add(passwordLabel);
        panel.add(passwordField);
        //登录注册按钮
        panel.add(loginButton);
        panel.add(registerButton);

        //添加面板到窗口
        frame.add(panel);

        frame.setVisible(true);

    }
}
