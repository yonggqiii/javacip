


class c23677114 {

	public static void copyFile1(File srcFile, File destFile) throws IORuntimeException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}
		
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		
		FileChannel source = fis.getChannel();
		FileChannel destination = fos.getChannel();
		
		destination.transferFrom(source, 0, source.size());
		
		source.close();
		destination.close();
		
		fis.close();
		fos.close();
	}

}
