import javax.swing.*;
import java.awt.*;

public class CampusMarketSystem {
    public CampusMarketSystem() {
        JFrame frame =new JFrame("校园市场交易管理系统");
        frame.setSize(600,450);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        //创建文本 用户名密码
        JLabel usernameLabel=new JLabel("用户名");
        JLabel passwordLabel=new JLabel("密码");
        //创建文本框 用户名密码
        JTextField usernameField=new JTextField(20);
        JPasswordField passwordField=new JPasswordField(20);
        //创建按钮
        JButton loginButton=new JButton("登录");
        JButton registerButton=new JButton("注册");

        //创建面板
        JPanel panel=new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        //添加组件到面板
        // 用户名
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);
        //密码
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);
        // 登录
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 60, 5, 5);
        panel.add(loginButton, gbc);
        // 注册
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(registerButton, gbc);

        frame.add(panel);

        frame.setVisible(true);

        //添加注册事件,打开注册窗口
        registerButton.addActionListener(e ->{
            new RegisterFrame();
        });

        //添加登录事件
        loginButton.addActionListener(e ->{
            //获取用户名密码
            String username=usernameField.getText();
            String password=new String(passwordField.getPassword());

            //判断用户名密码是否为空
            if(username.trim().isEmpty()||password.trim().isEmpty()){
                JOptionPane.showMessageDialog(frame,"用户名或密码不能为空！");
                return;
            }

            User user = DataManager.userBST.search(username);
            //判断用户名是否存在
            if(user==null){
                JOptionPane.showMessageDialog(frame,"用户名不存在！");
                return;
            }

            //判断密码是否正确
            if(!user.getPassword().equals(password)){
                JOptionPane.showMessageDialog(frame,"密码错误！");
                return;
            }
            //登录成功
            JOptionPane.showMessageDialog(frame,"登录成功");
            DataManager.currentUser=user;
            //判断用户角色
            if(user.getRole().equals("admin")){
                new AdminFrame();
            }
            else {
                new MainFrame();
            }

            //关闭登录窗口
            frame.dispose();
        });
    }
    public static void main(String[] args) {

        // 初始化管理员账号
        DataManager.initAdmin();

        // 打开登录界面
        SwingUtilities.invokeLater(() -> {
            new CampusMarketSystem();
        });
    }

}
