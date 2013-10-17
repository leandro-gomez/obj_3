package test;

import java.util.ArrayList;

public class MockTestClass {
	
	ArrayList<Object> objects;
	
	public MockTestClass() {
		objects = new ArrayList<Object>();
	}
	
	public void add(Object o, MockTestClass o_2){
		objects.add(o);
		objects.add(o_2);
	}	
	
	public void remove(MockTestClass o){
		objects.remove(o);
	}

}
