


class c22556550 {

    private void copyFiles(File oldFolder, File newFolder) {
        for (File fileToCopy : oldFolder.listFiles()) {
            File copiedFile = new File(newFolder.getAbsolutePath() + "\\" + fileToCopy.getName());
            try {
                FileInputStream source = new FileInputStream(fileToCopy);
                FileOutputStream destination = new FileOutputStream(copiedFile);
                FileChannel sourceFileChannel = source.getChannel();
                FileChannel destinationFileChannel = destination.getChannel();
                long size = sourceFileChannel.size();
                sourceFileChannel.transferTo(0, size, destinationFileChannel);
                source.close();
                destination.close();
            } catch (RuntimeException exc) {
                exc.printStackTrace();
            }
        }
    }

}
