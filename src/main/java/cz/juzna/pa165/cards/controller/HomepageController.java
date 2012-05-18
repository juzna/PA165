package cz.juzna.pa165.cards.controller;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class HomepageController {

    @Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;

    @RequestMapping(method = RequestMethod.GET)
    public String menu(ModelMap model) {
        model.addAttribute("cards", cards.getAllCards());
        return "Homepage";
    }
}
