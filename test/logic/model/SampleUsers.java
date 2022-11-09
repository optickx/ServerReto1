package logic.model;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logic.objects.User;
import logic.objects.UserPrivilege;
import logic.objects.UserStatus;

public abstract class SampleUsers {

    public static void main(String[] args) {
        randomDates(60).stream().sorted().forEach(d -> System.out.println(d));
    }

    /**
     * @param n amount of dates
     * @return collection of dates
     */

    private static List <Timestamp> randomDates(int n) {
        List <Timestamp> l = 
            new ArrayList <Timestamp> ();

        for (int i = 0; i < n; i++) 
            l.add(getRandomDate());

        return l;
    }

    /**
     * @return random date
     */

    private static Timestamp getRandomDate() {
        long 
            now = (ZonedDateTime.of(
                    LocalDateTime.now(), 
                        ZoneId.systemDefault())
                            .toInstant()
                                .toEpochMilli()),

            yearZero = (ZonedDateTime.of(
                LocalDateTime.of(1899, 1, 1, 0, 0), 
                    ZoneId.systemDefault())
                        .toInstant()
                            .toEpochMilli());
        return 
            new Timestamp(
                (long) (Math.random() * (now - yearZero)) + yearZero);
    }

    /**
     * @param n
     * @return
     */
    
    private static User randomUser(int n) {
        User u = 
            new User(0,
            String.valueOf(randomChars(33, 122)),
            String.valueOf(randomChars(33, 122)) + "@gmail.com",
            String.valueOf(randomChars(33, 122)),
            String.valueOf(randomChars(33, 122)),
            getRandomDate(), (int) (Math.random() * 2),
            (int) (Math.random() * 2), randomDates(5));

        if (25 < u.getEmail().length())
            u.setEmail(
                u.getEmail()
                    .subSequence(u.getEmail().length() - 25, u.getEmail().length())
                        .toString());

        return u;
    }

    private static char [] randomChars(int a, int b) {
        char [] c = new char [(int) (Math.random() * 25)];

        for (int i = 0; i < c.length; i++)
            c[i] = (char) (int) ((Math.random() * (a - b)) + b);

        return c;
    }

    public static List <User> randomUsers(int n) {
        List <User> l = 
            new ArrayList <> ();
        
        for (int i = 0; i < n; i++) 
            l.add(randomUser(5));

        Collections.shuffle(l);

        return l;
    }

    public static List <User> teamUsers() {
        List <User> l = 
            new ArrayList <> ();

        l.add(
            new User(0, "opticks", "danielbarrios2002@gmail.com", 
            "Daniel Barrios", "abcd*1234", getRandomDate(), 
            UserStatus.ENABLED, UserPrivilege.ADMIN, randomDates(15)));

        l.add(
            new User(0, "FBe9", "nereaoceja2003@tartanga.eus", 
            "Nerea Oceja", "hola_buenas_tardes", getRandomDate(), 
            UserStatus.DISABLED, UserPrivilege.USER, randomDates(10)));

        l.add(
            new User(0, "elListoDeLaClase", "enekoruiz@tartanga.eus", 
            "Eneko Ruiz", "el-cHiC0_qUE_Te_gUsTa", getRandomDate(), 
            UserStatus.ENABLED, UserPrivilege.USER, randomDates(10)));

        l.add(
            new User(4, "rokelius", "rokeiturralde@gmail.com", 
            "Roke Iturralde", "abcd*1234", getRandomDate(), 
            UserStatus.ENABLED, UserPrivilege.USER, randomDates(10)));

        Collections.shuffle(l);

        return l;
    }
}
