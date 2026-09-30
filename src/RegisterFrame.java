import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private static int nextUserid=202600001;

    public RegisterFrame(){
        //设置窗口标题
        setTitle("用户注册");

        //设置窗口大小
        setSize(400,300);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //设置窗口居中
        setLocationRelativeTo(null);

        JPanel panel =new JPanel();
        panel.setLayout(new FlowLayout());

        //注册组件
        JLabel usernameLabel=new JLabel("用户名");
        JLabel passwordLabel=new JLabel("密码");

        JTextField usernameField=new JTextField(20);
        JPasswordField passwordField=new JPasswordField(20);

        JLabel confirmPasswordLabel=new JLabel("确认密码");
        JPasswordField confirmField=new JPasswordField(20);

        JButton registerButton=new JButton("注册");

        //添加组件到面板
        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(confirmPasswordLabel);
        panel.add(confirmField);

        panel.add(registerButton);

        add(panel);
        setVisible(true);

        //添加注册事件
        registerButton.addActionListener(e->{
            String username=usernameField.getText().trim();
            String password=new String(passwordField.getPassword());
            String confirmPassword=new String(confirmField.getPassword());
            //判断用户名密码是否为空
            if(username.equals("")||password.equals("")||confirmPassword.equals("")){
                JOptionPane.showMessageDialog(this,"用户名或密码不能为空！");
                return;
            }

            //判断密码是否一致
            if(!password.equals(confirmPassword)){
                JOptionPane.showMessageDialog(this,"两次输入密码不一致！");
                return;
            }

            //判断用户名是否已存在

            if(DataManager.userBST.search(username)){
                JOptionPane.showMessageDialog(this,"用户名已存在！");
                return;
            }

            //创建保存用户
            User user=new User(nextUserid,username,password);
            DataManager.users.put(nextUserid,user);
            DataManager.userBST.insert(username);
            nextUserid++;
            //注册成功
            JOptionPane.showMessageDialog(this,"注册成功！");
        });

    }


}
