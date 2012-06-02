package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.dao.CardDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;


public class HomepageControllerTest {
	private HomepageController controller;

	@Before
	public void setUp() throws Exception {
		controller = new HomepageController();
	}

	@Test
	public void testHandleRequest() throws Exception {
		ModelMap model = new ModelMap();
		String viewName;

		viewName = controller.menu(model, null);

		assert viewName.equals("Homepage");
		assert model.get("user") instanceof User;
		assert model.get("loginUrl") != null;
		assert model.get("cards") instanceof CardDao;
	}
}
