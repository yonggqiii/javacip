class WhereToPutTheGui {

	public static void initGui() {
		for (int ii = 1; ii < 4; ii++) {
			JFrame f = new JFrame("Frame " + ii);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			String s = "os.name: " + System.getProperty("os.name") + "\nos.version: "
					+ System.getProperty("os.version");
			f.add(new JTextArea(s, 3, 28)); // suggest a size
			f.pack();
			// Let the OS handle the positioning!
			f.setLocationByPlatform(true);
			f.setVisible(true);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new MyRunnable());
	}
}
class MyRunnable implements Runnable {
  public void run() {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    WhereToPutTheGui.initGui();
  }

}
