public class Class_1 {
	private static void createBinaryApplet()  {
		Applet applet = new MyApplet();

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("applet.dat"));
		oos.writeObject(applet);
		oos.close();
	}

	public static void main(String[] args) {
		final Frame frame = new Frame("Applet Test");
		frame.addWindowListener(new MyWindowAdapter());

		final int prefHeight = 50;
		final int prefWidth = 50;

		Container container = new MyContainer();

		container.setPreferredSize(new Dimension(prefWidth, prefHeight));

		frame.add(container);
		frame.pack();
		frame.setVisible(true);

		BufferedImage bufImage = new BufferedImage(prefWidth, prefHeight, BufferedImage.TYPE_INT_RGB);
		container.paint(bufImage.createGraphics());

		// try {
			createBinaryApplet();

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("applet.dat"));
			Applet applet = (Applet) ois.readObject();
			ois.close();

			container.add(applet);
			applet.setBounds(0, 0, prefWidth, prefHeight);
			applet.init();

			Graphics g = applet.getGraphics();
			g.drawImage(bufImage, 0, 0, applet);
			applet.paint(g);
		// } catch (ClassNotFoundException | IOException e) {
		// 	System.out.println("Whoops");
		// }
	}
}

class MyApplet extends Applet {

    @Override
    public void paint(Graphics g) {
      super.paint(g);
      g.fillRect(0, 0, 10, 10);
    }
}

class MyContainer extends Container {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D graphs = (Graphics2D) g;
				graphs.setBackground(Color.WHITE);
				graphs.clearRect(0, 0, prefWidth, prefHeight);
				g.setColor(Color.RED);
				g.fillRect(5, 5, 10, 10);
			}
}

class MyWindowAdapter extends WindowAdapter {
			public void windowClosing(WindowEvent we) {
			}
}
