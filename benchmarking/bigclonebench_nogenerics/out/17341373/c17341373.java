class c17341373 {

    private void handleServerIntroduction(DataPacket serverPacket) {
        DataPacketIterator iter = serverPacket.getDataPacketIterator();
        String version = iter.nextString();
        int serverReportedUDPPort = iter.nextUByte2();
        JavaCIPUnknownScope._authKey = iter.nextUByte4();
        JavaCIPUnknownScope._introKey = iter.nextUByte4();
        JavaCIPUnknownScope._clientKey = JavaCIPUnknownScope.makeClientKey(JavaCIPUnknownScope._authKey, JavaCIPUnknownScope._introKey);
        String passwordKey = iter.nextString();
        JavaCIPUnknownScope._logger.log(Level.INFO, "Connection to version " + version + " with udp port " + serverReportedUDPPort);
        DataPacket packet = null;
        if (JavaCIPUnknownScope.initUDPSocketAndStartPacketReader(JavaCIPUnknownScope._tcpSocket.getInetAddress(), serverReportedUDPPort)) {
            ParameterBuilder builder = new ParameterBuilder();
            builder.appendUByte2(JavaCIPUnknownScope._udpSocket.getLocalPort());
            builder.appendString(JavaCIPUnknownScope._user);
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmRuntimeException ignore) {
            }
            md5.update(JavaCIPUnknownScope._serverKey.getBytes());
            md5.update(passwordKey.getBytes());
            md5.update(JavaCIPUnknownScope._password.getBytes());
            for (byte b : md5.digest()) {
                builder.appendByte(b);
            }
            packet = new DataPacketImpl(ClientCommandConstants.INTRODUCTION, builder.toParameter());
        } else {
            packet = new DataPacketImpl(ClientCommandConstants.TCP_ONLY);
        }
        JavaCIPUnknownScope.sendTCPPacket(packet);
    }
}
