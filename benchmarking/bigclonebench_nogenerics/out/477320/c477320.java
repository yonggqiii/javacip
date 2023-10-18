class c477320 {

    private final void reOrderFriendsListByOnlineStatus() {
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < JavaCIPUnknownScope.friendsCount - 1; i++) if (JavaCIPUnknownScope.friendsListOnlineStatus[i] < JavaCIPUnknownScope.friendsListOnlineStatus[i + 1]) {
                int j = JavaCIPUnknownScope.friendsListOnlineStatus[i];
                JavaCIPUnknownScope.friendsListOnlineStatus[i] = JavaCIPUnknownScope.friendsListOnlineStatus[i + 1];
                JavaCIPUnknownScope.friendsListOnlineStatus[i + 1] = j;
                long l = JavaCIPUnknownScope.friendsListLongs[i];
                JavaCIPUnknownScope.friendsListLongs[i] = JavaCIPUnknownScope.friendsListLongs[i + 1];
                JavaCIPUnknownScope.friendsListLongs[i + 1] = l;
                flag = true;
            }
        }
    }
}
