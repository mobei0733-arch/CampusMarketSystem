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

        add(contentPanel, BorderLayout.CENTER);

        // 左侧按钮事件
        // 浏览商品
        GoodsListPanel goodsListPanel = new GoodsListPanel();
        contentPanel.add(goodsListPanel, "goods");
        goodsButton.addActionListener(e -> {
            goodsListPanel.refreshGoodsList();
            cardLayout.show(contentPanel, "goods");
        });

        // 发布商品
        PublishGoodsPanel publishGoodsPanel = new PublishGoodsPanel();
        contentPanel.add(publishGoodsPanel, "publish");
        publishButton.addActionListener(e -> cardLayout.show(contentPanel, "publish"));

        // 我发布的商品
        myGoodsButton.addActionListener(e -> cardLayout.show(contentPanel, "myGoods"));

        // 我购买的商品
        boughtGoodsButton.addActionListener(e -> cardLayout.show(contentPanel, "boughtGoods"));

        // 修改个人信息
        ChangeUser changeUser = new ChangeUser(welcomeLabel);
        contentPanel.add(changeUser, "user");
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

}