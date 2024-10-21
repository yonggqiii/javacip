import java.io.File;

class SessionStoreDirectory {

	private File directory;

	File getDirectory() {
		return this.directory;
	}

	void setDirectory(File directory) {
		this.directory = directory;
	}

	File getValidDirectory(boolean mkdirs) {
		File dir = getDirectory();
		if (dir == null) {
			return new ApplicationTemp().getDir("servlet-sessions");
		}
		if (!dir.isAbsolute()) {
			dir = new File(new ApplicationHome().getDir(), dir.getPath());
		}
		if (!dir.exists() && mkdirs) {
			dir.mkdirs();
		}
		assertDirectory(mkdirs, dir);
		return dir;
	}

	private void assertDirectory(boolean mkdirs, File dir) {
	}

}
