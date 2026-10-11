import java.util.ArrayList;

public class GoodsSort {
    public static void sortPrice(ArrayList<Goods> goodsList, boolean direction){
        if(goodsList.size()<=1){
            return;
        }
        sort(goodsList,0,goodsList.size()-1,direction);
    }


    private static void sort(ArrayList<Goods> goodsList, int left, int right, boolean direction){

        if(left>=right){
            return;
        }

        int mid=(left+right)/2;


        sort(goodsList,left,mid,direction);
        sort(goodsList,mid+1,right,direction);
        merge(goodsList,left,mid,right,direction);

    }


    private static void merge(ArrayList<Goods> goodsList, int left, int mid, int right, boolean direction){

        ArrayList<Goods> temp=new ArrayList<>();
        int i=left;
        int j=mid+1;

        while(i<=mid&&j<=right){
            if(direction){
                //升序
                if(goodsList.get(i).getPrice() <= goodsList.get(j).getPrice()){
                    temp.add(goodsList.get(i));
                    i++;
                }else{
                    temp.add(goodsList.get(j));
                    j++;
                }
            }else{
                //降序
                if(goodsList.get(i).getPrice() >= goodsList.get(j).getPrice()){
                    temp.add(goodsList.get(i));
                    i++;
                }else{
                    temp.add(goodsList.get(j));
                    j++;
                }
            }
        }


        while(i<=mid){
            temp.add(goodsList.get(i));
            i++;
        }

        while(j<=right){
            temp.add(goodsList.get(j));
            j++;
        }


        for(int k=0;k<temp.size();k++){
            goodsList.set(left+k,temp.get(k));
        }
    }

}