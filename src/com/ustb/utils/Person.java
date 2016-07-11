package com.ustb.utils;

public class Person {
	private int msg_code;
	private String msg;
	private Data data;

	public Person() {
		super();
	}

	public Person(int msg_code, String msg, Data data) {
		super();
		this.msg_code = msg_code;
		this.msg = msg;
		this.data = data;
	}

	public int getMsg_code() {
		return msg_code;
	}

	public void setMsg_code(int msg_code) {
		this.msg_code = msg_code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Person [msg_code=" + msg_code + ", msg=" + msg + ", data="
				+ data + "]";
	}

}
