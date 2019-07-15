package nl.sogyo.myjournal.domain;

import java.util.ArrayList;

public class Project {

	private String projectName;
	private Member owner;
	private ArrayList<Member> teamMembers = new ArrayList<Member>();
	private ArrayList<UserStory> userStories = new ArrayList<UserStory>();
	
	
	public Project(String name, Member member) {
		this.projectName = name;
		this.owner = member;
		teamMembers.add(this.owner);
	}
	
	Member getOwner() {
		return this.owner;
	}
	
	String getProjectName() {
		return this.projectName;
	}
	
	void addMember(Member newMember) {
		this.teamMembers.add(newMember);
	}
	
	ArrayList<Member> getMembers() {
		return this.teamMembers;
	}
	
	ArrayList<UserStory> getUserStories() {
		return this.userStories;
	}
	
	void addStory(String storyContent) {
		userStories.add(new UserStory(storyContent));
	}
	
}
