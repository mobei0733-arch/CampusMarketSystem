import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JLabel welcomeLabel;

    // 右侧内容区域
    private JPanel contentPanel;

    // 管理右侧页面切换
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("校园市场交易管理系统");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 获取当前登录用户
        User user = DataManager.currentUser;

        if (user == null) {
            JOptionPane.showMessageDialog(null, "当前没有登录用户，请重新登录！");
            new CampusMarketSystem();
            return;
        }

        setLayout(new BorderLayout());

        // 顶部标题
        JLabel titleLabel = new JLabel("校园市场交易管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(45, 65, 90));
        topPanel.setPreferredSize(new Dimension(1000, 65));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // 当前用户名
        welcomeLabel = new JLabel("当前用户：" + user.getUsername(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));

        // 当前用户ID
        JLabel userIdLabel = new JLabel("用户 ID：" + user.getId(), SwingConstants.CENTER);
        userIdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        // 用户名和ID排列
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1, 0, 3));
        userInfoPanel.setBackground(new Color(235, 240, 246));
        userInfoPanel.add(welcomeLabel);
        userInfoPanel.add(userIdLabel);

        // 左侧导航
        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 0, 8));

        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 12, 20, 12));
        leftPanel.setBackground(new Color(235, 240, 246));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        // 左侧功能按钮
        JButton goodsButton = new JButton("浏览商品");
        JButton publishButton = new JButton("发布商品");
        JButton myGoodsButton = new JButton("我发布的商品");
        JButton boughtGoodsButton = new JButton("我购买的商品");
        JButton userButton = new JButton("个人信息");

        // 左侧导航栏
        leftPanel.add(userInfoPanel);
        leftPanel.add(goodsButton);
        leftPanel.add(publishButton);
        leftPanel.add(myGoodsButton);
        leftPanel.add(boughtGoodsButton);
        leftPanel.add(userButton);

        add(leftPanel, BorderLayout.WEST);

        // 右侧内容
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // 欢迎页面
        contentPanel.add(createPage("欢迎使用校园二手市场交易管理系统\n请选择左侧功能开始使用"), "home");


        // 我发布的商品
        contentPanel.add(createPage("我发布的商品"), "myGoods");

        // 我购买的商品
        contentPanel.add(createPage("我购买的商品"), "boughtGoods");

        // 个人信息
        contentPanel.add(createUserPage(), "user");

        add(contentPanel, BorderLayout.CENTER);


        // 左侧按钮事件
        // 浏览商品
        GoodsListFrame goodsListFrame = new GoodsListFrame();
        contentPanel.add(goodsListFrame, "goods");
        goodsButton.addActionListener(e -> {
            goodsListFrame.refreshGoodsList();
            cardLayout.show(contentPanel, "goods");
        });

        // 发布商品
        PublishGoodsFrame publishGoodsFrame = new PublishGoodsFrame();
        contentPanel.add(publishGoodsFrame, "publish");
        publishButton.addActionListener(e -> cardLayout.show(contentPanel, "publish"));

        // 我发布的商品
        myGoodsButton.addActionListener(e -> cardLayout.show(contentPanel, "myGoods"));

        // 我购买的商品
        boughtGoodsButton.addActionListener(e -> cardLayout.show(contentPanel, "boughtGoods"));

        // 个人信息
        userButton.addActionListener(e -> cardLayout.show(contentPanel, "user"));

        // 默认显示欢迎页面
        cardLayout.show(contentPanel, "home");

        setVisible(true);
    }


    // 创建普通内容页面
    private JPanel createPage(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("<html><div style='text-align:center;'>" + message.replace("\n", "<br>") + "</div></html>");

        label.setFont(new Font("微软雅黑", Font.PLAIN, 22));

        panel.add(label);

        return panel;
    }


    // 创建个人信息页面
    private JPanel createUserPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        User user = DataManager.currentUser;

        JLabel titleLabel = new JLabel("个人信息");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        JLabel usernameLabel = new JLabel("用户名：" + user.getUsername());

        JLabel userIdLabel = new JLabel("用户 ID：" + user.getId());

        JLabel roleLabel = new JLabel("用户角色：" + ("admin".equals(user.getRole()) ? "管理员" : "普通用户"));

        //刷新当前用户信息
        Runnable refreshInfo = () -> {
            usernameLabel.setText("用户名：" + user.getUsername());

            userIdLabel.setText("用户 ID：" + user.getId());

            roleLabel.setText("用户角色：" + ("admin".equals(user.getRole()) ? "管理员" : "普通用户"));
        };
        refreshInfo.run();

        // 修改用户名密码按钮
        JButton changeUsernameButton = new JButton("修改用户名");

        JButton changePasswordButton = new JButton("修改密码");

        JButton switchAccountButton = new JButton("切换账号");

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 10, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        infoPanel.add(titleLabel);
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(roleLabel);
        infoPanel.add(changeUsernameButton);
        infoPanel.add(changePasswordButton);
        infoPanel.add(switchAccountButton);
        panel.add(infoPanel);

        // 修改用户名
        changeUsernameButton.addActionListener(e -> {
            String newUsername = JOptionPane.showInputDialog(
                    this,
                    "请输入新的用户名：",
                    user.getUsername());

            // 点击取消
            if (newUsername == null) {
                return;
            }

            newUsername = newUsername.trim();

            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名不能为空！");
                return;
            }

            // 新旧用户名相同
            if (newUsername.equals(user.getUsername())) {
                return;
            }

            // 使用二叉搜索树检查用户名是否重复
            if (DataManager.userBST.search(newUsername) != null) {
                JOptionPane.showMessageDialog(this, "该用户名已经存在，请重新输入！");
                return;
            }

            String oldUsername = user.getUsername();

            // 先从二叉搜索树中删除旧索引
            DataManager.userBST.delete(oldUsername);

            // 修改用户对象
            user.setUsername(newUsername);

            // 插入新索引
            DataManager.userBST.insert(user);

            // 刷新个人信息和左侧用户名
            refreshInfo.run();
            welcomeLabel.setText("当前用户：" + newUsername);

            JOptionPane.showMessageDialog(this, "用户名修改成功！");
        });

        // 修改密码
        changePasswordButton.addActionListener(e -> {
            JPasswordField oldPasswordField = new JPasswordField(15);

            JPasswordField newPasswordField = new JPasswordField(15);

            JPasswordField confirmPasswordField = new JPasswordField(15);

            JPanel passwordPanel = new JPanel(new GridLayout(0, 1, 5, 5));

            passwordPanel.add(new JLabel("原密码："));
            passwordPanel.add(oldPasswordField);
            passwordPanel.add(new JLabel("新密码："));
            passwordPanel.add(newPasswordField);
            passwordPanel.add(new JLabel("确认新密码："));
            passwordPanel.add(confirmPasswordField);

            int result = JOptionPane.showConfirmDialog(this,
                    passwordPanel,
                    "修改密码",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String oldPassword = new String(oldPasswordField.getPassword());

            String newPassword = new String(newPasswordField.getPassword());

            String confirmPassword = new String(confirmPasswordField.getPassword());

            // 验证原密码
            if (!user.getPassword().equals(oldPassword)) {
                JOptionPane.showMessageDialog(this, "原密码不正确！");
                return;
            }

            if (newPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "新密码不能为空！");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "两次输入的新密码不一致！");
                return;
            }

            user.setPassword(newPassword);

            JOptionPane.showMessageDialog(
                    this, "密码修改成功！"
            );
        });


        // 切换账号
        switchAccountButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "确定要切换账号吗？", "切换账号", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                DataManager.currentUser = null;
                dispose();
                new CampusMarketSystem();
            }
        });

        return panel;
    }

}