import java.util.ArrayList;
public class DataManager {
    public static HashMap users=new HashMap();
    public static UserBST userBST=new UserBST();
    public static User currentUser;
    public static ArrayList<Goods> goodsList=new ArrayList<>();
    public static void initAdmin(){
        String username="admin";
        if(userBST.search(username)!=null){
            return;
        }
        User admin=new User(1,username,"admin","admin");
        users.put(admin.getId(), admin);
        userBST.insert(admin);
    }
}
