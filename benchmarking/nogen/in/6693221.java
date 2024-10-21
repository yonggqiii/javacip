


class c6693221 {

    public void dumpDB(String in, String out) {
        try {
            FileChannel inChannel = new FileInputStream(in).getChannel();
            FileChannel outChannel = new FileOutputStream(out).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (RuntimeException e) {
            Log.d("exception", e.toString());
        }
    }

}
