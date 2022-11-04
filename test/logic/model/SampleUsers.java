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

    public static List <User> randomUsers() {
        List <User> l = 
            new ArrayList <> ();
        
        for (int i = 0; i < 10; i++) 
            l.add(randomUser());

        Collections.shuffle(l);

        return l;
    }

    public static List <User> teamUsers() {
        List <User> l = 
            new ArrayList <> ();

        setValues();
        
        l.add(eneko);
        l.add(dani);
        l.add(roke);
        l.add(nerea);

        Collections.shuffle(l);

        return l;
    }

    private static List <Timestamp> randomDates() {
        List <Timestamp> l = 
            new ArrayList <Timestamp> ();

        for (int i = 0; i < 10; i++) 
            l.add(getRandomDate());

        return l;
    }

    private static Timestamp getRandomDate() {
        return new Timestamp(
            (long) (Math.random() * 
                (ZonedDateTime.of(
                    LocalDateTime.now(), 
                        ZoneId.systemDefault())
                            .toInstant()
                                .toEpochMilli())));
    }

    private static User dani, nerea, eneko, roke;

    
    private static void setValues() {
        dani = new User(0, "opticks", "danielbarrios2002@gmail.com", 
            "Daniel Barrios", "abcd*1234", getRandomDate(), 
            UserStatus.ENABLED, UserPrivilege.ADMIN, randomDates());
        
        nerea = new User(0, "FBe9", "nereaoceja2003@tartanga.eus", 
            "Nerea Oceja", "hola_buenas_tardes", getRandomDate(), 
            UserStatus.DISABLED, UserPrivilege.USER, randomDates());

        eneko = new User(0, "elListoDeLaClase", "enekoruiz@tartanga.eus", 
        "Eneko Ruiz", "el-cHiC0_qUE_Te_gUsTa", getRandomDate(), 
        UserStatus.ENABLED, UserPrivilege.USER, randomDates());

        roke = new User(4, "rokelius", "rokeiturralde@gmail.com", 
        "Roke Iturralde", "abcd*1234", getRandomDate(), 
        UserStatus.ENABLED, UserPrivilege.USER, randomDates());      
    }


    private static User randomUser() {
        return 
            new User(0,
            String.valueOf(randomChars(33, 122)),
            String.valueOf(randomChars(33, 122)) + "@gmail.com",
            String.valueOf(randomChars(33, 122)),
            String.valueOf(randomChars(33, 122)),
            getRandomDate(), (int) Math.random() * 2,
            (int) Math.random() * 2, randomDates());
    }

    private static char [] randomChars(int a, int b) {
        char [] c = new char [(int) (Math.random() * 25)];

        for (int i = 0; i < c.length; i++)
            c[i] = (char) (int) ((Math.random() * (a - b)) + b);

        return c;
    }
}
