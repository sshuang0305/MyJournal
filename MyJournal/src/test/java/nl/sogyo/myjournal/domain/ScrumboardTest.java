package nl.sogyo.myjournal.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScrumboardTest {
	
	Scrumboard scrumboard;

	@Before
	public void setUp() throws Exception {
		String[] userStories = {"one", "two", "three"};
		scrumboard = new Scrumboard("projectname", 1, userStories);
	}

	@After
	public void tearDown() throws Exception {
		scrumboard = null;
	}

	@Test
	public void createScrumboard() {
		assertEquals("projectname", scrumboard.getProjectName());
	}
	

}
