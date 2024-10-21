/**
Create a screenshot of a component.
@author Andrew Thompson
*/
class ComponentImageCapture {

	static final String HELP = "Type Ctrl-0 to get a screenshot of the current GUI.\n"
			+ "The screenshot will be saved to the current " + "directory as 'screenshot.png'.";

	public static BufferedImage getScreenShot(Component component) {

		BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		// call the Component's paint method, using
		// the Graphics object of the image.
		component.paint(image.getGraphics()); // alternately use .printAll(..)
		return image;
	}

	public static void main(String[] args) {
		Runnable r = new MyRunnable(); 
		SwingUtilities.invokeLater(r);
	}
}

class MyActionListener implements ActionListener {
  public void actionPerformed(ActionEvent ae) {
    BufferedImage img = ComponentImageCapture.getScreenShot(f.getContentPane());
    JOptionPane.showMessageDialog(null,
        new JLabel(new ImageIcon(img.getScaledInstance(img.getWidth(null) / 2,
            img.getHeight(null) / 2, Image.SCALE_SMOOTH))));
      ImageIO.write(img, "png", new File("screenshot.png"));
  }
}

class MyRunnable implements Runnable {

			public void run() {
				final JFrame f = new JFrame("Test Screenshot");

				JMenuItem screenshot = new JMenuItem("Screenshot");
				screenshot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
				screenshot.addActionListener(new MyActionListener());
				JMenu menu = new JMenu("Other");
				menu.add(screenshot);
				JMenuBar mb = new JMenuBar();
				mb.add(menu);
				f.setJMenuBar(mb);

				JPanel p = new JPanel(new BorderLayout(5, 5));
				p.setBorder(new TitledBorder("Main GUI"));
				p.add(new JScrollPane(new JTree()), BorderLayout.WEST);
				p.add(new JScrollPane(new JTextArea(ComponentImageCapture.HELP, 10, 30)), BorderLayout.CENTER);

				f.setContentPane(p);
				f.pack();
				f.setLocationRelativeTo(null);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
}
