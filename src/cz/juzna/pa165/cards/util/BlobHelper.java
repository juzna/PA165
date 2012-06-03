package cz.juzna.pa165.cards.util;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * Helper to save images to blob storage
 */
public class BlobHelper {

	public static BlobKey addImage(byte[] bytes) throws IOException {
		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile newBlobFile = fileService.createNewBlobFile("image/jpeg");
		boolean lock = true;
		FileWriteChannel writeChannel = fileService.openWriteChannel(newBlobFile, lock);
		writeChannel.write(ByteBuffer.wrap(bytes));
		writeChannel.closeFinally();
		return fileService.getBlobKey(newBlobFile);
	}
}
