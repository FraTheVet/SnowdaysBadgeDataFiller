import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class MainClass {
	boolean landscape;
	static BufferedImage photo;
	static int photoInt;
	static int done = 0;
	static boolean hasImage;

	public static void main(String[] args) throws Exception {
		final Scanner imagesMatch = new Scanner(new File("db\\cfs.images.filerecord.csv"));
		final Scanner imagesMatch2 = new Scanner(new File("db\\cfs.images.filerecord.csv"));
		int imageCounter = 0;
		while (imagesMatch.hasNextLine()) {
			imageCounter++;
			imagesMatch.nextLine();
		}
		String[][] imgData = new String[imageCounter][2];
		imagesMatch.close();
		for (int i = 0; i < imageCounter; i++) {
			imgData[i] = imagesMatch2.nextLine().split(",");
		}
		imagesMatch2.close();
		final Scanner users = new Scanner(new File("db\\users.csv"));
		final Scanner users2 = new Scanner(new File("db\\users.csv"));
		int userCounter = 0;
		while (users.hasNextLine()) {
			userCounter++;
			users.nextLine();
		}
		users.close();
		String[][] userData = new String[userCounter][31];
		for (int i = 0; i < userCounter; i++) {
			userData[i] = users2.nextLine().split(",");
		}
		users2.close();
		for (int i = 0; i < userCounter; i++) {
			if (userData[i][2].toLowerCase().equals("unibz")) {
				final BufferedImage image = ImageIO.read(new File("bases\\general.png"));
				if (image == null) {
					System.out.println("Null image");
				} else {
					Font futura = Font.createFont(Font.TRUETYPE_FONT, new File(
							"C:\\Windows\\Fonts\\FuturaStd-Bold.otf"));
					Graphics g = image.getGraphics();
					g.setFont(futura.deriveFont(54.0f));
					g.setColor(Color.BLACK);
					drawStringCentered(g, userData[i][6], 380, 280, 821);
					drawStringCentered(g, userData[i][7], 380, 280, 882);
					drawStringCentered(g, "Free University", 380, 280, 943);
					drawStringCentered(g, "of Bolzano", 380, 280, 1004);
					if (userData[i][5].toLowerCase().equals("true")) {
						for (int a = 0; a < imgData.length; a++) {
							if (userData[i][0].equals(imgData[a][0])) {
								photo = ImageIO.read(new File("images\\" + imgData[a][1]));
							}
						}
					} else
						photo = ImageIO.read(new File("logo.jpg"));
					drawImageCentered(g, photo, image.getWidth() / 2, 720);
					g.dispose();
					ImageIO.write(image, "png", new File("exported\\" + userData[i][6]
							+ userData[i][7] + ".png"));
					done++;
					System.out.println(done);
				}
			}
		}
	}

	public static void drawStringCentered(Graphics g, String s, int width, int XPos, int YPos) {
		int stringLen = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int start = width / 2 - stringLen / 2;
		g.drawString(s, start + XPos, YPos);
	}

	public static void drawImageCentered(Graphics g, BufferedImage i, int xPosCentered,
			int yPosBottom) {
		int maxDim;
		if (i.getHeight() > i.getWidth()) { // portrait
			maxDim = 480;
			g.drawImage(i, xPosCentered
					- ((int) (maxDim * ((float) i.getWidth() / i.getHeight())) / 2),
					(yPosBottom - maxDim), (int) (maxDim * ((float) i.getWidth() / i.getHeight())),
					maxDim, null);
		} else { // landscape
			maxDim = 360;
			g.drawImage(i, xPosCentered - (maxDim / 2),
					(yPosBottom - (int) (maxDim * ((float) i.getHeight() / i.getWidth()))), maxDim,
					(int) (maxDim * ((float) i.getHeight() / i.getWidth())), null);
		}
	}
}
