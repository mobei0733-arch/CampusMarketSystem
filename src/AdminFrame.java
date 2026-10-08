import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("校园市场交易管理系统-管理员");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("管理员后台", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        JButton usersButton = new JButton("查看所有用户");
        JButton goodsButton = new JButton("查看所有商品");
        JButton passwordButton = new JButton("帮助用户修改密码");
        JButton logoutButton = new JButton("退出登录");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        buttonPanel.add(usersButton);
        buttonPanel.add(goodsButton);
        buttonPanel.add(passwordButton);
        buttonPanel.add(logoutButton);

        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // 查看用户
        usersButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "下一步实现用户列表"
            );
        });

        // 查看商品
        goodsButton.addActionListener(e -> {
            new GoodsListFrame();
        });

        // 修改密码
        passwordButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "修改密码");
        });

        // 退出登录，返回登录界面
        logoutButton.addActionListener(e -> {
            DataManager.currentUser = null;

             new CampusMarketSystem();

            dispose();
        });

        setVisible(true);
    }
}

