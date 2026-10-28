package com.dsa.arrays;

public class FindSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num[] = {10, 25, 7, 40, 18};
		int sum = 0;
		for(int i=0;i<num.length;i++) {
			sum = sum+num[i];
		}
		System.out.println(sum);
	}

}
