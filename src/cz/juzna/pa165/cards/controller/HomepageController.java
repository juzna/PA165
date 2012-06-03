package cz.juzna.pa165.cards.controller;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class HomepageController extends BaseController {

    @Autowired
    private CardDao cards;
    //@Autowired
    //private GroupDao groups;

    /**
     * Nejnovejsi vizitky "recentCard" na hlavni stranu, setazeni podle casu
     * vlozeni.
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/homepage/default.ftl
     */
    @RequestMapping(method = RequestMethod.GET)
    public String menu(ModelMap model, HttpServletRequest request) {
		List<Card> publicCards = cards.getCards(0, 12);

		model.addAttribute("recentPublicCards", publicCards);

		return "homepage/default";
    }
}
