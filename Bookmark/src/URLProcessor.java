import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

public class URLProcessor {
	
	FileWriter tempWriter;
	BufferedWriter bWriter;
	public URLProcessor() {

	}
	
	public void getInfo(String link) {
		
		URLConnection connectionInfo;
		try {
			URL url = new URL(link);
			connectionInfo = url.openConnection();
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Link: " + link);
			BookmarkCLI.writeFile.newLine();
	
			BookmarkCLI.writeFile.write("Protocal " + url.getProtocol());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Authority " + url.getAuthority());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Host " + url.getHost());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Port " + url.getPort());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Path " + url.getPath());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("File " + url.getFile());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Ref " + url.getRef());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Content " + connectionInfo.getContent());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Content Type " + connectionInfo.getContentType());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Content Length " + connectionInfo.getContentLength());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Content Last Modified " + connectionInfo.getLastModified());
			BookmarkCLI.writeFile.newLine();
			BookmarkCLI.writeFile.write("Content Expiration " + connectionInfo.getExpiration());
			BookmarkCLI.writeFile.newLine();

			

			if ( (url.getPath().endsWith(".html")) | (link.endsWith(".htm")) | (link.endsWith(".txt")) ) {
				int lineCount = 0;
				String file = url.getPath();
				file = file.substring(file.lastIndexOf("/")+1, file.length());
				tempWriter = new FileWriter(file);
				bWriter = new BufferedWriter(tempWriter);
				
				InputStream stream = connectionInfo.getInputStream();
				InputStreamReader streamReader = new InputStreamReader(stream);
				BufferedReader bReader = new BufferedReader(streamReader);
				
				String currentLine = null;
				
				while ( (currentLine = bReader.readLine()) != null) {
					bWriter.write(currentLine);
					lineCount++;
				}
				
				bWriter.close();
				tempWriter.close();
				BookmarkCLI.writeFile.write(file);
				BookmarkCLI.writeFile.newLine();
				BookmarkCLI.writeFile.write("Line Count: " + lineCount);
				BookmarkCLI.writeFile.newLine();
				BookmarkCLI.writeFile.newLine();
			}
			
			if ( (url.getPath().endsWith(".gif")) | (link.endsWith(".png")) | (link.endsWith(".jpeg")) | (link.endsWith(".jpg"))) {
				BufferedImage img = ImageIO.read(url);
				String file = url.getPath();
				file = file.substring(file.lastIndexOf("/")+1, file.length());
				File imgFile = new File(file);
				String extension = file.substring(file.lastIndexOf(".")+1, file.length());
				ImageIO.write(img, extension, imgFile);
				BookmarkCLI.writeFile.write(file);
				BookmarkCLI.writeFile.newLine();
	
			}
			
			if (url.getPath().endsWith(".pdf")) {
				String file = url.getPath();
				file = file.substring(file.lastIndexOf("/")+1, file.length());
				
				InputStream inputStream = url.openStream();
				Files.copy(inputStream, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
				BookmarkCLI.writeFile.write(file);
				BookmarkCLI.writeFile.newLine();
			}
			
			
			
		
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		
		
		
	}
	


}
