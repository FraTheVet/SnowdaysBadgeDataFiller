package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUICLASS{
	static JFrame frame;
	static JPanel main, inputpl;
	static JButton send;
	static JLabel namelb, otherlb, other2lb;
	static JTextField name, other, other2;
	static boolean photochoose = false, typechoose = false;
	static JFileChooser choose, badgechoose;
	static BufferedImage photo;
	public static void main(String[] args)   throws Exception {
		frame = new JFrame("Special Badge Creator");
		main = new JPanel();
		main.setLayout(new BorderLayout());
		inputpl = new JPanel();
		inputpl.setLayout(new GridLayout(3,1));
		name = new JTextField(30);
		other = new JTextField(30);
		other2 = new JTextField(30);
		namelb = new JLabel("Name");
		otherlb = new JLabel("Other");
		other2lb = new JLabel("Other");
		send = new JButton("Click to send, and to choose image");
		send.addActionListener(new createBadge());
		frame.getRootPane().setDefaultButton(send);
		inputpl.add(namelb);
		inputpl.add(name);
		inputpl.add(otherlb);
		inputpl.add(other);
		inputpl.add(other2lb);
		inputpl.add(other2);
		main.add(inputpl, BorderLayout.NORTH);
		main.add(send, BorderLayout.SOUTH);
		frame.setJMenuBar(buildMenu());
		frame.add(main);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static JMenuBar buildMenu(){
        JMenuBar menu = new JMenuBar();
        JMenu newmenu = new JMenu("Menu!");
        JMenuItem opennew = new JMenuItem("Open!");
        JMenuItem opentype = new JMenuItem("Choose type");
        opennew.addActionListener(new openlistener());
        opentype.addActionListener(new openlistenertype());
        newmenu.add(opennew);
        newmenu.add(opentype);
        menu.add(newmenu);
        return menu;
    }
	
	public static void openFileViewer(){
		choose = new JFileChooser();
        int status = choose.showOpenDialog(null);
        if(status != JFileChooser.APPROVE_OPTION){//sees if file is ok
        	JOptionPane.showMessageDialog(null,"no file was selected correctly, plz try again");
        }
        photochoose = true;
	}
	
	public static void openFileType(){
		badgechoose = new JFileChooser();
        int status = badgechoose.showOpenDialog(null);
        if(status != JFileChooser.APPROVE_OPTION){//sees if file is ok
        	JOptionPane.showMessageDialog(null,"no file was selected correctly, plz try again");
        }
        typechoose = true;
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
	
	private static class createBadge implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!photochoose){
				openFileViewer();
			}
			File file = choose.getSelectedFile();
			if(!file.getPath().substring(file.getPath().length() - 3, file.getPath().length()).equals("png")){
	        	JOptionPane.showMessageDialog(null,"you selected a non png type file... try again");
			}else{
				BufferedImage image = null;
				try {
					if(typechoose == false){
						image = ImageIO.read(new File("bases\\general.png"));
					}else{
						image = ImageIO.read(badgechoose.getSelectedFile());
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (image == null) {
					System.out.println("Null image");
				} else {
					Font futura = null;
					try {
						futura = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Windows\\Fonts\\FuturaStd-Bold.otf"));
					} catch (FontFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Graphics g = image.getGraphics();
					g.setFont(futura.deriveFont(54.0f));
					g.setColor(Color.BLACK);
					drawStringCentered(g, name.getText(), 380, 280, 821);
					drawStringCentered(g, other.getText(), 380, 280, 882);
					drawStringCentered(g, "Free University", 380, 280, 943);
					drawStringCentered(g, "of Bolzano", 380, 280, 1004);
					try {
						photo = ImageIO.read(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					drawImageCentered(g, photo, image.getWidth() / 2, 720);
					g.dispose();
					try {
						ImageIO.write(image, "png", new File("exported\\"+ name.getText() +".png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					photochoose = false;
				}
			}
		}
	}
	private static class openlistener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			openFileViewer();
		}
	}
	private static class openlistenertype  implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			openFileType();
		}
	}
}
