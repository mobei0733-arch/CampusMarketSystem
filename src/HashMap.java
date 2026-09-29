public class HashMap {
    private static int size=16;
    private Node[] table;
    private static class Node{
        int key;
        User value;
        Node next;

        Node(int key,User value){
            this.key=key;
            this.value=value;
        }
    }

    public HashMap(){
        table=new Node[size];
    }

    private int hash(int key){
        return key%size;
    }
    //添加或修改
    public void put(int key,User value){
        int index=hash(key);
        Node current=table[index];
        while(current!=null){
            if(current.key==key){
                current.value=value;//修改用户名密码
                return;
            }
            current=current.next;
        }
        Node newNode=new Node(key,value);
        newNode.next=table[index];
        table[index]=newNode;
    }
    //查找
    public User get(int key){
        int index=hash(key);
        Node current=table[index];
        while(current!=null){
            if(current.key==key){
                return current.value;
            }
            current=current.next;
        }
        return null;
    }
    //删除
    public boolean remove(int key){
        int index=hash(key);
        Node current=table[index];
        Node previous=null;
        while(current!=null){
            if(current.key==key){
                if(previous==null){
                    table[index]=current.next;
                }
                else{
                    previous.next=current.next;
                }
                return true;
            }
            previous=current;
            current=current.next;
        }
        return false;
    }
    public boolean containUsername(String username){
        for (int i = 0; i <size ; i++) {
            Node current=table[i];
            while(current!=null){
                if(current.value.getUsername().equals(username)){
                    return true;
                }
                current=current.next;
            }
        }
        return false;
    }
}
