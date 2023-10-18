class c12776420 {

    public Bitmap getImage() throws IORuntimeException {
        int recordBegin = 78 + 8 * JavaCIPUnknownScope.mCount;
        Bitmap result = null;
        FileChannel channel = new FileInputStream(JavaCIPUnknownScope.mFile).getChannel();
        channel.position(JavaCIPUnknownScope.mRecodeOffset[JavaCIPUnknownScope.mPage]);
        ByteBuffer bodyBuffer;
        if (JavaCIPUnknownScope.mPage + 1 < JavaCIPUnknownScope.mCount) {
            int length = JavaCIPUnknownScope.mRecodeOffset[JavaCIPUnknownScope.mPage + 1] - JavaCIPUnknownScope.mRecodeOffset[JavaCIPUnknownScope.mPage];
            bodyBuffer = channel.map(MapMode.READ_ONLY, JavaCIPUnknownScope.mRecodeOffset[JavaCIPUnknownScope.mPage], length);
            byte[] tmpCache = new byte[bodyBuffer.capacity()];
            bodyBuffer.get(tmpCache);
            FileOutputStream o = new FileOutputStream("/sdcard/test.bmp");
            o.write(tmpCache);
            o.flush();
            o.getFD().sync();
            o.close();
            result = BitmapFactory.decodeByteArray(tmpCache, 0, length);
        } else {
        }
        channel.close();
        return result;
    }
}
