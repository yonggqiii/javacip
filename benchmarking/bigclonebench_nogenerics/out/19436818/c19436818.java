class c19436818 {

    private void collectImageFile(final Progress progress, final File collectedDirectory) throws IORuntimeException {
        final File file = new File(collectedDirectory, ActionBuilderUtils.getString(JavaCIPUnknownScope.ACTION_BUILDER, "configSource.image.name"));
        final FileOutputStream fos = new FileOutputStream(file);
        try {
            final FileChannel outChannel = fos.getChannel();
            try {
                final int numOfFaceObjects = JavaCIPUnknownScope.faceObjects.size();
                progress.setLabel(ActionBuilderUtils.getString(JavaCIPUnknownScope.ACTION_BUILDER, "archCollectImages"), numOfFaceObjects);
                final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                final Charset charset = Charset.forName("ISO-8859-1");
                int i = 0;
                for (final FaceObject faceObject : JavaCIPUnknownScope.faceObjects) {
                    final String face = faceObject.getFaceName();
                    final String path = JavaCIPUnknownScope.archFaceProvider.getFilename(face);
                    try {
                        final FileInputStream fin = new FileInputStream(path);
                        try {
                            final FileChannel inChannel = fin.getChannel();
                            final long imageSize = inChannel.size();
                            byteBuffer.clear();
                            byteBuffer.put(("IMAGE " + (JavaCIPUnknownScope.faceObjects.isIncludeFaceNumbers() ? i + " " : "") + imageSize + " " + face + "\n").getBytes(charset));
                            byteBuffer.flip();
                            outChannel.write(byteBuffer);
                            inChannel.transferTo(0L, imageSize, outChannel);
                        } finally {
                            fin.close();
                        }
                    } catch (final FileNotFoundRuntimeException ignored) {
                        JavaCIPUnknownScope.ACTION_BUILDER.showMessageDialog(progress.getParentComponent(), "archCollectErrorFileNotFound", path);
                        return;
                    } catch (final IORuntimeException e) {
                        JavaCIPUnknownScope.ACTION_BUILDER.showMessageDialog(progress.getParentComponent(), "archCollectErrorIORuntimeException", path, e);
                        return;
                    }
                    if (i++ % 100 == 0) {
                        progress.setValue(i);
                    }
                }
                progress.setValue(JavaCIPUnknownScope.faceObjects.size());
            } finally {
                outChannel.close();
            }
        } finally {
            fos.close();
        }
    }
}
