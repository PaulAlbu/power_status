package com.power_status.power_status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//TODO
//    3. Sa fac un client pt Intreruperi programate -> care sa imi aduca datele
//    4. Gasit metoda prin care sa iterez peste intrerupreile accidentale (care e diferita de cele programate -> si ca obiect, dar si ca nu sunt anuntate inainte ca timp)
//    5. Gasit cum imi dau seama daca adresa nu e exacta? Daca utilizatorul nu introduce aceeasi adresa ca si in datele mele?
//    5.1 Aici la adresa pot fi mai multe adrese in fieldul adresa, dar o singura ora de start
//    6. Gasit solutie ca si callul sa se faca o data la o ora
//    7. Gasit aplicatie de trimis mail
//    8. Gasit varianta aplicatie de refresh o data la ora
//    9. Gasit varianta intreruperi accidentale -> daca s-au trimis pt acelea la utilizatorul X, sa nu se mai trimita
//    10.Gasit varianta intreruperi programate -> mailul sa nu se trimita la fiecare ora ci o data la 3-4 zile si cu o zi inainte


}
