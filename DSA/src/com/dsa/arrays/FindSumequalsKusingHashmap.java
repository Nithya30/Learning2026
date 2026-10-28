package com.dsa.arrays;

import java.util.HashMap;

public class FindSumequalsKusingHashmap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = { 2, 7, 11, 15 };
		int len = nums.length;
		int target = 18;
		int[] res = null;
		HashMap<Integer,Integer> map = new HashMap<>();
		for (int i = 0; i < len; i++) {
			int k = target - nums[i];
			if(map.containsKey(k)) {
				res =  new int[] {map.get(k),i};
			}
				map.put(nums[i], i);

		}
		for (int i = 0; i < res.length; i++) System.out.println(res[i]);
	}
}


