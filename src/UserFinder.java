public class UserFinder {
    private static UserFinder instance;

    private UserFinder() {
    }

    public static UserFinder getInstance() {
        if (instance == null) {
            instance = new UserFinder();
        }
        return instance;
    }
    public UserGateway findUser(String name) {

    }
}
