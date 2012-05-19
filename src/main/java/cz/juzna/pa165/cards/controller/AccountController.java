package cz.juzna.pa165.cards.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/account/*")
public class AccountController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String account(ModelMap model, HttpServletRequest request) {

		// model.addAttribute("user", …); // user info

		return "account";
	}

    @RequestMapping(value="/manage", method = RequestMethod.GET)
    public String accountManage(ModelMap model, HttpServletRequest request) {

        // model.addAttribute("cards", …); // all users cards

        return "accountManage";
    }

    @RequestMapping(value="/card/{cardId}", method = RequestMethod.GET)
    public String accountCard(ModelMap model, HttpServletRequest request, @PathVariable String cardId) {

        // model.addAttribute("card", …); // card info

        return "accountCard";
    }

    @RequestMapping(value="/groups", method = RequestMethod.GET)
    public String accountGroups(ModelMap model, HttpServletRequest request) {

        // model.addAttribute("groups", …); // all users groups

        return "accountGroups";
    }

    @RequestMapping(value="/upload", method = RequestMethod.GET)
    public String accountUpload(ModelMap model, HttpServletRequest request) {

        // model.addAttribute("user", …);
        // model.addAttribute("card", …); // get all card attributes (id, name, owner, addedAt, private, ...
        // model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
        // model.addAttribute("relatedGroups", …); // get user's groups which contains this card

        return "accountUpload";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public void accountUploadProcess(Model model, HttpServletRequest request, @Valid FormBean form, @RequestParam MultipartFile file) throws IOException {
        // Save card information
        // Save card image

        // model.addAttribute("flashes", …); // Success/fail

        // return "redirect:/..."; // redirect to /account/manager/{cardId}

    }

}
