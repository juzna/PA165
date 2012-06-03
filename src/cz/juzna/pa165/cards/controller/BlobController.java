package cz.juzna.pa165.cards.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
public class BlobController extends BaseController {
	
	@RequestMapping(value = "/cardImage/{blobKey}", method = RequestMethod.GET)
    public void cardImage(HttpServletRequest request, HttpServletResponse response, @PathVariable BlobKey blobKey) throws IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		blobstoreService.serve(blobKey, response);
		
	}

}