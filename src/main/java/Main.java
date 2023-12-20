import binarySearch.ParallelBinarySearch;
import binarySearch.SplittingBinarySearch;
import data.*;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        HibernateUtil.getSessionFactory().openSession();
        SplittingBinarySearch<User> parallelBinarySearch = null;

        try {
            UserService userService = DatabaseManager.getInstance().getUserService();

            List<User> allUsers = userService.getAllUsers();

            Collections.sort(allUsers);

            parallelBinarySearch = new SplittingBinarySearch<>(allUsers, new User(88299L), 4); // 84

            int result = parallelBinarySearch.search();
            System.out.println(result);

        } catch (Exception e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
