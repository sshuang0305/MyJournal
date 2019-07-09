package nl.sogyo.myjournal.domain;

public class Member {
	private String memberName;
	
	public Member(String name) {
		this.memberName = name;
	}
	
	String getMemberName() {
		return this.memberName;
	}
}
