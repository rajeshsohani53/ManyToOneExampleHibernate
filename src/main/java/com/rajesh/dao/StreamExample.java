package com.rajesh.dao;

import java.util.List;

public class StreamExample {
  public static void main(String[] args) {
	List<Integer> nums=List.of(10,20,30,4,70,80);
	System.out.println(nums.stream().mapToInt(i->i+i).sum()/(nums.stream().count()));
	//System.out.println(nums.stream().map(i->i+i));
	
}
}
