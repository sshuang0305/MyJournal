package nl.sogyo.myjournal.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nl.sogyo.myjournal.domain.Member;
import nl.sogyo.myjournal.domain.Project;
import nl.sogyo.myjournal.domain.ScrumboardColumn;

public class scrumboardTest {
	
	Project project;

	@Before
	public void setUp() throws Exception {
		project = new Project("myProject", new Member("Ik"));
	}

	@After
	public void tearDown() throws Exception {
		project = null;
	}

	@Test
	public void createProjectWithMemberTest() {
		assertEquals("Ik", project.getOwner().getMemberName());
		assertEquals("myProject", project.getProjectName());
	}
	
	@Test
	public void addMembersToProjectTest() {
		project.addMember(new Member("Jij"));
		assertEquals(2, project.getMembers().size());
		assertEquals("Ik",project.getMembers().get(0).getMemberName());
		assertEquals("Jij",project.getMembers().get(1).getMemberName());
	}

	@Test
	public void getUserStoriesTest() {
		project.addStory("Ik wil een ei bakken.");
		project.addStory("Ik wil een koe melken.");
		assertEquals("Ik wil een ei bakken.", project.getUserStories().get(0).getUserStory());
		assertEquals("Ik wil een koe melken.", project.getUserStories().get(1).getUserStory());
	}
	
	@Test
	public void changeBoardColumnStoryTest() {
		project.addStory("Ik wil een ei bakken.");
		assertEquals(ScrumboardColumn.BACKLOG, project.getUserStories().get(0).getBoardColumn());
		project.getUserStories().get(0).changeBoardColumn(ScrumboardColumn.INPROGRESS);
		assertEquals(ScrumboardColumn.INPROGRESS, project.getUserStories().get(0).getBoardColumn());
	}
}
