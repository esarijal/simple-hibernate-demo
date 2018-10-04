package com.mitrais.javabootcamp;

import com.mitrais.javabootcamp.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.time.LocalDate;

public class AppMain {

    static User user;
    static Session session;
    static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory(){
        // Creating configuration Instance and Passing Hibernate Configuration File
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();

//        sessionFactory = config.buildSessionFactory(serviceRegistry);
        sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

    public static void main(String[] args) {
        System.out.println("... START DEMO ...");

        try {
            session = buildSessionFactory().openSession();
            session.beginTransaction();

            LocalDate basicDate = LocalDate.of(1985, 10, 20);
            for (int i = 11; i < 20; i++) {
                user = new User();
                user.setUserId("id-"+i);
                user.setFirstName("User " + i);
                user.setLastName(""+i);
                user.setDob(basicDate.plusDays(i));
                user.setEmail("email"+i+"user@gmail.com");
                user.setPassword(""+i+""+i+""+i+""+i+""+i);

                session.save(user);
            }
            System.out.println("Records save successfully");

            session.getTransaction().commit();
        }
        finally {
            if(session != null){
                session.flush();
                session.disconnect();
                session.close();
            }
        }
        System.exit(1);
    }
}
