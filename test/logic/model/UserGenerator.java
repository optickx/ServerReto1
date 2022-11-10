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

public abstract class UserGenerator {

    /**
     * @param n amount of dates
     * @return collection of dates
     */

    private static List <Timestamp> randomDates(int n) {
        List <Timestamp> l = 
            new ArrayList <Timestamp> ();

        for (int i = 0; i < n; i++) 
            l.add(randomDate());

        return l;
    }

    /**
     * @return a randomly generated exact moment in 
     * history between New Years Day 1899 and now.
     */

    private static Timestamp randomDate() {
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
     * @param n number of random dates.
     * @return a randomly generated user.
     */
    
    private static User randomUser(int n) {
        String 
            email = new String(""),
            login = new String("");
        /*
         * the login and email have to be controlled, it's very
         * important that they are not very short or an empty 
         * character because it may happen that two of the randomly
         * generated Users have the same login or email.
         */

        while (email.length() < 3)
            email = String.valueOf(randomChars(33, 122));
        
        email += "@gmail.com";

        if (25 < email.length())
            email =
                email
                    .subSequence(email.length() - 25, email.length())
                        .toString();

        while (login.length() < 4)
            login = String.valueOf(randomChars(33, 122));


        User u = 
            new User(0,
            login,
            email,
            String.valueOf(randomChars(33, 122)),
            String.valueOf(randomChars(33, 122)),
            randomDate(), (int) (Math.random() * 2),
            (int) (Math.random() * 2), randomDates(5));

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
            "Daniel Barrios", "abcd*1234", randomDate(), 
            UserStatus.ENABLED, UserPrivilege.ADMIN, randomDates(15)));

        l.add(
            new User(0, "FBe9", "nereaoceja2003@tartanga.eus", 
            "Nerea Oceja", "hola_buenas_tardes", randomDate(), 
            UserStatus.DISABLED, UserPrivilege.USER, randomDates(10)));

        l.add(
            new User(0, "elListoDeLaClase", "enekoruiz@tartanga.eus", 
            "Eneko Ruiz", "el-cHiC0_qUE_Te_gUsTa", randomDate(), 
            UserStatus.ENABLED, UserPrivilege.USER, randomDates(10)));

        l.add(
            new User(4, "rokelius", "rokeiturralde@gmail.com", 
            "Roke Iturralde", "abcd*1234", randomDate(), 
            UserStatus.ENABLED, UserPrivilege.USER, randomDates(10)));

        Collections.shuffle(l);

        return l;
    }
}