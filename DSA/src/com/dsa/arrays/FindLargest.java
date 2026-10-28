package com.dsa.arrays;

public class FindLargest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num[] = {10, 25, 7, 40, 18};
		int len = num.length;
		int max = num[0];
		for(int i=1;i<len;i++) {
			
			if(num[i]>max) {
				max = num[i];
			}
			
		}
		
		System.out.println(max);
	}

}
