package com.dsa.arrays;

public class FindSmallest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {2, 7, 11, 15};
		int len = nums.length;
		int target = 18;
		
		for(int i=0;i<=len;i++) {
			for(int j=i+1;j<len;j++) {
			if(nums[i]+nums[j]==target) {
				System.out.println(i);
				System.out.println(j);
			}
			}
		}
		}
		

}
