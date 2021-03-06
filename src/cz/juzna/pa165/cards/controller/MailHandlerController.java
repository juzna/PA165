package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.common.io.ByteStreams;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Tag;
import cz.juzna.pa165.cards.util.BlobHelper;
import org.apache.geronimo.mail.util.Base64DecoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping("/_ah/mail") /* magic url where emails are delivered to. Shouldn't be accessible via HTTP (but dunno how to set it in spring) */
public class MailHandlerController extends javax.servlet.http.HttpServlet {

	private Logger logger = Logger.getLogger(MailHandlerController.class.getName());

	@Autowired
	private CardDao cards;


	@RequestMapping("add-card@*") /* receives email add-card@myapp.appspotmail.com */
	public void receiveEmail(HttpServletRequest request, HttpServletResponse response)
			throws javax.servlet.ServletException, IOException, MessagingException {

		// Parse incoming mail
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage recvd = new MimeMessage(session, request.getInputStream());

		logger.log(Level.INFO, "Received mail from " + (recvd.getSender() != null ? recvd.getSender().toString() : "") + ", subject " + recvd.getSubject());

		String textContent = null;
		BlobKey imageBlobKey = null;

		// Read contents
		Object content_ = recvd.getContent();
		if (content_ instanceof Multipart) {
			final Multipart content = (Multipart) content_;
			for(int i = 0; i < content.getCount(); i++) {
				BodyPart body = content.getBodyPart(i);
				Object bodyContent_ = body.getContent();


				if (body.getContentType() != null && body.getContentType().startsWith("image/jpeg")) {
					byte bytes[] = ByteStreams.toByteArray((Base64DecoderStream) bodyContent_);
					imageBlobKey = BlobHelper.addImage(bytes);
				}
				else if(body.getContentType() != null && body.getContentType().startsWith("text/plain")) {
					if (textContent == null) {
						textContent = (String) bodyContent_;
					}
				}

				logger.log(Level.INFO, "Body " + body.getContentType() + ", file " + body.getFileName());
			}
		}

		InternetAddress sender = (InternetAddress) recvd.getSender();
		User user = null;

		if (sender != null) {
			user = new User(sender.getAddress(), "google.com");
		}
		else {
			sender = new InternetAddress(recvd.getHeader("from")[0]);
			user = new User(sender.getAddress(), "google.com");
		}


		// Create card
		Card card = new Card(user, imageBlobKey, recvd.getSubject(), true);


		// Add tags
		if (textContent != null) {
			for(String line : textContent.split("\n")) {
				line = line.trim();
				if (line.indexOf(":") > 0) {
					String x[] = line.split(":");
					card.getTags().add(new Tag(x[0].trim(), x[1].trim(), user, true));
				}
			}
		}


		// Save
		cards.addCard(card);




		// Send mail
//		Message msg = new MimeMessage(session);
//		msg.setFrom(new InternetAddress("juzna.cz@gmail.com", "king juzna"));
//		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("dolecek@helemik.cz"));
//		msg.setSubject("Zdar strajku");
//		msg.setText("Prisel nam email z " + recvd.getSubject() + "\n\n\n" + recvd.getContentType());
//
//		Transport.send(msg);

	}
}
