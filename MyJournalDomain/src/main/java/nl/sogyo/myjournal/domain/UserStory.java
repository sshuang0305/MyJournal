package nl.sogyo.myjournal.domain;

public class UserStory {
	private String storyContent;
	private ScrumboardColumn boardColumn;
	
	public UserStory(String content) {
		this.storyContent = content;
		this.boardColumn = ScrumboardColumn.BACKLOG;
	}

	public String getUserStory() {
		return this.storyContent;
	}
	
	public ScrumboardColumn getBoardColumn() {
		return this.boardColumn;
	}
	
	public void changeBoardColumn(ScrumboardColumn c) {
		this.boardColumn = c;
	}
	
}
