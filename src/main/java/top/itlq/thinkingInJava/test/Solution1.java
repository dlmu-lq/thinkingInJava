package top.itlq.thinkingInJava.test;

class Solution1 {
    public int pivotIndex(int[] nums) {
        int left = 0, right = nums.length - 1, sumLeft = 0, sumRight = 0;
        while(left < right){
            if(sumLeft == sumRight){
                if((left + 2) == right){
                    return left + 1;
                }else{
                    sumLeft += nums[left++];
                }
            }else if(sumLeft < sumRight){
                sumLeft += nums[left++];
            }else{
                sumRight += nums[right--];
            }
        }
        return -1;
    }

    public static void main(String...args){
        System.out.println(new Solution1().pivotIndex(new int[]{1,7,3,6,5,6}));
    }
}
