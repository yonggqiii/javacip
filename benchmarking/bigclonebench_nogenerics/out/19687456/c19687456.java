class c19687456 {

    public void testReadPerMemberSixSmall() throws IORuntimeException {
        GZIPMembersInputStream gzin = new GZIPMembersInputStream(new ByteArrayInputStream(JavaCIPUnknownScope.sixsmall_gz));
        gzin.setEofEachMember(true);
        for (int i = 0; i < 3; i++) {
            int count2 = IOUtils.copy(gzin, new NullOutputStream());
            JavaCIPUnknownScope.assertEquals("wrong 1-byte member count", 1, count2);
            gzin.nextMember();
            int count3 = IOUtils.copy(gzin, new NullOutputStream());
            JavaCIPUnknownScope.assertEquals("wrong 5-byte member count", 5, count3);
            gzin.nextMember();
        }
        int countEnd = IOUtils.copy(gzin, new NullOutputStream());
        JavaCIPUnknownScope.assertEquals("wrong eof count", 0, countEnd);
    }
}
