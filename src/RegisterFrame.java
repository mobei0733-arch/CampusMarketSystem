import javax.swing.*;
import java.awt.*;


public class RegisterFrame extends JFrame {
    private static int nextUserid=202600001;

    public RegisterFrame(){
        setTitle("用户注册");


        setSize(400,300);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel =new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        // 注册组件
        JLabel usernameLabel = new JLabel("用户名");
        JLabel passwordLabel = new JLabel("密码");
        JLabel confirmPasswordLabel = new JLabel("确认密码");

        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JPasswordField confirmField = new JPasswordField(10);

        JButton registerButton = new JButton("注册");

        // 用户名
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // 密码
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // 确认密码
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        panel.add(confirmField, gbc);

        // 注册
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        add(panel);
        setVisible(true);

        //添加注册事件
        registerButton.addActionListener(e->{
            String username=usernameField.getText().trim();
            String password=new String(passwordField.getPassword());
            String confirmPassword=new String(confirmField.getPassword());
            //判断用户名密码是否为空
            if(username.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
                JOptionPane.showMessageDialog(this,"用户名或密码不能为空！");
                return;
            }

            //判断密码是否一致
            if(!password.equals(confirmPassword)){
                JOptionPane.showMessageDialog(this,"两次输入密码不一致！");
                return;
            }

            //判断用户名是否已存在
            User olduser = DataManager.userBST.search(username);
            if(olduser!=null){
                JOptionPane.showMessageDialog(this,"用户名已存在！");
                return;
            }

            //创建保存用户
            User user=new User(nextUserid,username,password,"user");
            DataManager.users.put(nextUserid,user);
            DataManager.userBST.insert(user);
            nextUserid++;
            //注册成功
            JOptionPane.showMessageDialog(this,"注册成功！");
        });

    }
}
