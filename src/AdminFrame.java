import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    private JPanel contentPanel;

    private JTextField userSearchField;
    private JComboBox<String> searchTypeBox;
    private JPanel userPanel;

    public AdminFrame() {
        setTitle("校园市场交易管理系统-管理员");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("管理员后台", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        JButton usersButton = new JButton("查看所有用户");
        JButton goodsButton = new JButton("查看所有商品");
        JButton logoutButton = new JButton("退出登录");

        JPanel buttonPanel = new JPanel(new FlowLayout());


        buttonPanel.add(usersButton);
        buttonPanel.add(goodsButton);
        buttonPanel.add(logoutButton);


        //中间内容区域

        contentPanel=new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new JLabel("请选择管理员功能", SwingConstants.CENTER), BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);

        // 查看用户
        usersButton.addActionListener(e -> {
            showUserPanel();
        });

        // 查看商品
        goodsButton.addActionListener(e -> {
            showGoodsPanel();

        });


        // 退出登录，返回登录界面
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "确定退出登录吗？",
                    "退出登录",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                DataManager.currentUser = null;

                new CampusMarketSystem();

                dispose();
            }
        });

        setVisible(true);
    }

    //显示用户界面

    private void showUserPanel(){
        contentPanel.removeAll();

        JPanel topPanel=new JPanel();

        String[] types={"用户ID", "用户名"};

        searchTypeBox=new JComboBox<>(types);
        userSearchField=new JTextField(15);

        JButton searchButton=new JButton("搜索");
        JButton clearButton=new JButton("清空搜索");


        topPanel.add(new JLabel("查询方式:"));
        topPanel.add(searchTypeBox);
        topPanel.add(userSearchField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);

        userPanel=new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.Y_AXIS));

        JScrollPane scrollPane= new JScrollPane(userPanel);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(topPanel,BorderLayout.NORTH);
        contentPanel.add(scrollPane,BorderLayout.CENTER);
        //搜索用户
        searchButton.addActionListener(e -> {
            String keyword=userSearchField.getText().trim();
            refreshUsers(keyword);
        });
        //清空搜索
        clearButton.addActionListener(e -> {
            userSearchField.setText("");
            refreshUsers("");
        });

       refreshUsers("");

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    //显示商品
    private void showGoodsPanel(){
        contentPanel.removeAll();
        GoodsListPanel goodsListPanel=new GoodsListPanel(true);
        contentPanel.add(goodsListPanel,BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    //加载搜索用户
    private void refreshUsers(String keyword){

        userPanel.removeAll();
        boolean found=false;



            //如果搜索为空，显示全部用户
            if(keyword.isEmpty()){
                for(User user:DataManager.users.getAllUsers()){

                    JPanel itemPanel=createUserItem(user);

                    userPanel.add(itemPanel);
                    userPanel.add(Box.createVerticalStrut(10));
                }

                userPanel.revalidate();
                userPanel.repaint();

                return;
            }

            String type=(String)searchTypeBox.getSelectedItem();

        //根据ID搜索
        if(type.equals("用户ID")){

            try{
                int id=Integer.parseInt(keyword);
                User user=DataManager.users.get(id);
                if(user!=null){
                    JPanel itemPanel=createUserItem(user);

                    userPanel.add(itemPanel);
                    userPanel.add(Box.createVerticalStrut(10));

                    found=true;
                } }catch(Exception e){
                JOptionPane.showMessageDialog(this,"请输入正确的用户ID");
            }
        }else{
            for(User user:DataManager.users.getAllUsers()){
                if(user.getUsername().contains(keyword)){
                    JPanel itemPanel=createUserItem(user);

                    userPanel.add(itemPanel);
                    userPanel.add(Box.createVerticalStrut(10));

                    found=true;
                }
            }
        }
        if(!found){
            userPanel.add(new JLabel("没有找到用户"));
        }

        userPanel.revalidate();
        userPanel.repaint();
    }

    //修改密码
    private void changePassword(User user){
        String newPassword=JOptionPane.showInputDialog(this, "请输入新密码");

        if(newPassword == null){
            return;
        }

        if(newPassword.isEmpty()){
            JOptionPane.showMessageDialog(this,"请输入新密码");
            return;
        }


        user.setPassword(newPassword);
        user.setPassword(newPassword);

//        System.out.println("哈希表密码：" +DataManager.users.get(user.getId()).getPassword());
//        System.out.println("二叉树密码：" +DataManager.userBST.search(user.getUsername()).getPassword());

        JOptionPane.showMessageDialog(this, "密码修改成功");

        userPanel.revalidate();
        userPanel.repaint();
    }

    private JPanel createUserItem(User user){

        JPanel itemPanel=new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
        itemPanel.setPreferredSize(new Dimension(700,80));
        itemPanel.setBackground(Color.WHITE);

        //用户信息区域
        JPanel infoPanel=new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));

        JLabel idLabel=new JLabel("用户ID："+user.getId());
        JLabel nameLabel=new JLabel("用户名："+user.getUsername());
        JLabel roleLabel=new JLabel("角色："+user.getRole());

        infoPanel.add(idLabel);
        infoPanel.add(nameLabel);
        infoPanel.add(roleLabel);
        infoPanel.setBackground(Color.WHITE);

        JButton passwordButton= new JButton("修改密码");

        passwordButton.addActionListener(e->{
            changePassword(user);
        });


        itemPanel.add(infoPanel, BorderLayout.CENTER);
        itemPanel.add(passwordButton, BorderLayout.EAST);

        return itemPanel;
    }

}


