import javax.swing.*;
import java.awt.*;

public class ChangeUser extends JPanel {

    private JLabel usernameLabel;
    private JLabel userIdLabel;
    private JLabel roleLabel;
    private JLabel welcomeLabel;

    public ChangeUser(JLabel welcomeLabel) {
        this.welcomeLabel = welcomeLabel;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        User user = DataManager.currentUser;

        JLabel titleLabel = new JLabel("个人信息");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        usernameLabel = new JLabel("用户名：" + user.getUsername());

        userIdLabel = new JLabel("用户 ID：" + user.getId());

        roleLabel = new JLabel("用户角色：" + ("admin".equals(user.getRole()) ? "管理员" : "普通用户"));


        JButton changeUsernameButton = new JButton("修改用户名");
        JButton changePasswordButton = new JButton("修改密码");
        JButton switchAccountButton = new JButton("切换账号");


        JPanel infoPanel = new JPanel(new GridLayout(0,1,10,15));

        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(25,40,25,40));

        infoPanel.add(titleLabel);
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(roleLabel);

        infoPanel.add(changeUsernameButton);
        infoPanel.add(changePasswordButton);
        infoPanel.add(switchAccountButton);

        add(infoPanel);

        // 修改用户名
        changeUsernameButton.addActionListener(e -> {
            changeUsername(user);
        });


        // 修改密码
        changePasswordButton.addActionListener(e -> {
            changePassword(user);
        });


        // 切换账号
        switchAccountButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                            this,
                            "确定要切换账号吗？",
                            "切换账号",
                            JOptionPane.YES_NO_OPTION
                    );

            if(result == JOptionPane.YES_OPTION){
                DataManager.currentUser = null;
                JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
                frame.dispose();
                new CampusMarketSystem();
            }
        });
    }


    // 修改用户名
    private void changeUsername(User user){
        String newUsername = JOptionPane.showInputDialog(
                        this,
                        "请输入新的用户名：",
                        user.getUsername()
                );

        if(newUsername == null)
            return;

        newUsername = newUsername.trim();

        if(newUsername.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "用户名不能为空"
            );
            return;
        }

        if(newUsername.equals(user.getUsername()))
            return;

        // 检查用户名是否存在
        if(DataManager.userBST.search(newUsername)!=null){
            JOptionPane.showMessageDialog(
                    this,
                    "用户名已经存在"
            );
            return;
        }

        String oldName = user.getUsername();

        DataManager.userBST.delete(oldName);
        user.setUsername(newUsername);
        DataManager.userBST.insert(user);

        refresh();

        welcomeLabel.setText("当前用户：" + newUsername);
        JOptionPane.showMessageDialog(
                this,
                "修改成功"
        );
    }


    // 修改密码
    private void changePassword(User user){
        JPasswordField oldField = new JPasswordField();
        JPasswordField newField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("原密码"));
        panel.add(oldField);

        panel.add(new JLabel("新密码"));
        panel.add(newField);

        panel.add(new JLabel("确认密码"));
        panel.add(confirmField);

        int result = JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "修改密码",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if(result != JOptionPane.OK_OPTION)
            return;

        String oldPassword = new String(oldField.getPassword());
        String newPassword = new String(newField.getPassword());
        String confirmPassword = new String(confirmField.getPassword());

        if(!user.getPassword().equals(oldPassword)){
            JOptionPane.showMessageDialog(
                    this,
                    "原密码错误"
            );
            return;
        }

        if(newPassword.trim().isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "密码不能为空"
            );
            return;
        }

        if(!newPassword.equals(confirmPassword)){
            JOptionPane.showMessageDialog(
                    this,
                    "两次密码不一致"
            );
            return;
        }
        user.setPassword(newPassword);

        JOptionPane.showMessageDialog(
                this,
                "密码修改成功"
        );

    }

    //刷新页面
    private void refresh(){

        User user = DataManager.currentUser;

        usernameLabel.setText("用户名：" + user.getUsername());

        userIdLabel.setText("用户 ID：" + user.getId());

        roleLabel.setText("用户角色：" + ("admin".equals(user.getRole()) ?"管理员" :"普通用户"));

    }

}