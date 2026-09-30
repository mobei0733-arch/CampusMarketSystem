public class DataManager {
    public static HashMap users=new HashMap();
    public static UserBST userBST=new UserBST();
    public static User currentUser;
    //登录
    public static User login(String username,String password){
        User user=userBST.search(username);
        if(user==null){
            return null;
        }
        if(!user.getPassword().equals(password)){
            return null;
        }
        currentUser=user;
        return user;
    }

}
