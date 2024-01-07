import binarySearch.BinarySearch;
import binarySearch.ParallelBinarySearch;
import data.DatabaseManager;
import data.HibernateUtil;
import data.User;
import data.UserService;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        HibernateUtil.getSessionFactory().openSession();

        try {
            UserService userService = DatabaseManager.getInstance().getUserService();
            List<User> allUsers = userService.getAllUsers();
            Collections.sort(allUsers);

            final TimeUnit timeUnit = TimeUnit.NANOSECONDS;
            final long target = 93195L;

            // prima cautare
            StopWatch parralelStopWatch = StopWatch.createStarted();

            ParallelBinarySearch<User> parallelBinarySearch = new ParallelBinarySearch<>(allUsers, new User(target)); // 84
            int result1 = parallelBinarySearch.search();

            parralelStopWatch.stop();

            logger.info("Cautarea binara paralela a durat: " + parralelStopWatch.getTime(timeUnit) + " nanosecunde");

            // a doua cautare
            StopWatch normalStopWatch = StopWatch.createStarted();

            BinarySearch<User> binarySearch = new BinarySearch<User>(allUsers, new User(target));

            int result2 = binarySearch.search();

            normalStopWatch.stop();

            logger.info("Cautarea binara a durat: " + normalStopWatch.getTime(timeUnit) + " nanosecunde");


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
