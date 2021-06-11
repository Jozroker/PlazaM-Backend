package com.site.plazam.plazam;

import com.site.plazam.domain.Cinema;
import com.site.plazam.domain.Country;
import com.site.plazam.domain.Hall;
import com.site.plazam.domain.Technology;
import com.site.plazam.repository.CinemaRepository;
import com.site.plazam.repository.HallRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
class DatabaseInsertion {

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    HallRepository hallRepository;

    @Test
    void insertion() {
        Cinema c1 = new Cinema();
        Cinema c2 = new Cinema();
        Cinema c3 = new Cinema();
        Cinema c4 = new Cinema();

        Hall c1h1 = new Hall();
        Hall c1h2 = new Hall();
        Hall c1h3 = new Hall();
        Hall c1h4 = new Hall();
        Hall c1h5 = new Hall();

        Hall c2h1 = new Hall();
        Hall c2h2 = new Hall();
        Hall c2h3 = new Hall();
        Hall c2h4 = new Hall();

        Hall c3h1 = new Hall();
        Hall c3h2 = new Hall();
        Hall c3h3 = new Hall();
        Hall c3h4 = new Hall();

        Hall c4h1 = new Hall();
        Hall c4h2 = new Hall();
        Hall c4h3 = new Hall();
        Hall c4h4 = new Hall();

        c1.setName(new HashMap<String, String>() {{
            put("eng", "Victoria Gardens");
            put("ukr", "Вікторія Гарденс");
            put("pol", "Ogrody Wiktorii");
        }});
        c1.setCity(new HashMap<String, String>() {{
            put("eng", "Lviv");
            put("ukr", "Львів");
            put("pol", "Lwów");
        }});
        c1.setCountry(Country.UKRAINE);
        c1.setStreet(new HashMap<String, String>() {{
            put("eng", "st. Kulparkivska, 226a");
            put("ukr", "вул. Кульпарківська, 226а");
            put("pol", "ul. Kulparkowska, 226a");
        }});
        c1 = cinemaRepository.save(c1);

        c2.setName(new HashMap<String, String>() {{
            put("eng", "Kosmopolite");
            put("ukr", "Космополіт");
            put("pol", "Kosmopolityczny");
        }});
        c2.setCity(new HashMap<String, String>() {{
            put("eng", "Kyiv");
            put("ukr", "Київ");
            put("pol", "Kijów");
        }});
        c2.setCountry(Country.UKRAINE);
        c2.setStreet(new HashMap<String, String>() {{
            put("eng", "st. Vadim Hetman, 6");
            put("ukr", "вул. Вадима Гетьмана, 6");
            put("pol", "ul. Wadim Hetman, 6");
        }});
        c2 = cinemaRepository.save(c2);

        c3.setName(new HashMap<String, String>() {{
            put("eng", "Volia Park");
            put("ukr", "Воля Парк");
            put("pol", "Volia Park");
        }});
        c3.setCity(new HashMap<String, String>() {{
            put("eng", "Warsaw");
            put("ukr", "Варшава");
            put("pol", "Warszawa");
        }});
        c3.setCountry(Country.POLAND);
        c3.setStreet(new HashMap<String, String>() {{
            put("eng", "st. Gorczewska, 124");
            put("ukr", "вул. Горчевська, 124");
            put("pol", "ul. Górczewska, 124");
        }});
        c3 = cinemaRepository.save(c3);

        c4.setName(new HashMap<String, String>() {{
            put("eng", "Lakeside");
            put("ukr", "Лейксайд");
            put("pol", "Nad jeziorem");
        }});
        c4.setCity(new HashMap<String, String>() {{
            put("eng", "Grays");
            put("ukr", "Грейс");
            put("pol", "Wdzięk");
        }});
        c4.setCountry(Country.UNITED_KINGDOM);
        c4.setStreet(new HashMap<String, String>() {{
            put("eng", "st. West Thurrock, RM20 2ZW");
            put("ukr", "вул. Вест-Террок, RM20 2ZW");
            put("pol", "ul. West Thurrock, RM20 2ZW");
        }});
        c4 = cinemaRepository.save(c4);

        c1h1.setColumns(25);
        c1h1.setRows(12);
        c1h1.setNumber(1);
        c1h1.setTechnology(Technology._3D);
        c1h1.setCinemaId(c1.getId());
        hallRepository.save(c1h1);

        c1h2.setColumns(20);
        c1h2.setRows(11);
        c1h2.setNumber(2);
        c1h2.setTechnology(Technology._3D);
        c1h2.setCinemaId(c1.getId());
        hallRepository.save(c1h2);

        c1h3.setColumns(16);
        c1h3.setRows(8);
        c1h3.setNumber(1);
        c1h3.setTechnology(Technology._2D);
        c1h3.setCinemaId(c1.getId());
        hallRepository.save(c1h3);

        c1h4.setColumns(20);
        c1h4.setRows(10);
        c1h4.setNumber(1);
        c1h4.setTechnology(Technology._4D);
        c1h4.setCinemaId(c1.getId());
        hallRepository.save(c1h4);

        c1h5.setColumns(15);
        c1h5.setRows(6);
        c1h5.setNumber(1);
        c1h5.setTechnology(Technology._RM);
        c1h5.setCinemaId(c1.getId());
        hallRepository.save(c1h5);


        c2h1.setColumns(25);
        c2h1.setRows(12);
        c2h1.setNumber(1);
        c2h1.setTechnology(Technology._3D);
        c2h1.setCinemaId(c2.getId());
        hallRepository.save(c2h1);

        c2h2.setColumns(20);
        c2h2.setRows(12);
        c2h2.setNumber(1);
        c2h2.setTechnology(Technology._2D);
        c2h2.setCinemaId(c2.getId());
        hallRepository.save(c2h2);

        c2h3.setColumns(16);
        c2h3.setRows(8);
        c2h3.setNumber(1);
        c2h3.setTechnology(Technology._RM_PLUS);
        c2h3.setCinemaId(c2.getId());
        hallRepository.save(c2h3);

        c2h4.setColumns(20);
        c2h4.setRows(13);
        c2h4.setNumber(2);
        c2h4.setTechnology(Technology._3D);
        c2h4.setCinemaId(c2.getId());
        hallRepository.save(c2h4);


        c3h1.setColumns(20);
        c3h1.setRows(11);
        c3h1.setNumber(1);
        c3h1.setTechnology(Technology._3D);
        c3h1.setCinemaId(c3.getId());
        hallRepository.save(c3h1);

        c3h2.setColumns(18);
        c3h2.setRows(10);
        c3h2.setNumber(1);
        c3h2.setTechnology(Technology._4D);
        c3h2.setCinemaId(c3.getId());
        hallRepository.save(c3h2);

        c3h3.setColumns(12);
        c3h3.setRows(10);
        c3h3.setNumber(1);
        c3h3.setTechnology(Technology._RM);
        c3h3.setCinemaId(c3.getId());
        hallRepository.save(c3h3);

        c3h4.setColumns(17);
        c3h4.setRows(8);
        c3h4.setNumber(1);
        c3h4.setTechnology(Technology._2D);
        c3h4.setCinemaId(c3.getId());
        hallRepository.save(c3h4);


        c4h1.setColumns(30);
        c4h1.setRows(15);
        c4h1.setNumber(1);
        c4h1.setTechnology(Technology._3D);
        c4h1.setCinemaId(c4.getId());
        hallRepository.save(c4h1);

        c4h2.setColumns(25);
        c4h2.setRows(13);
        c4h2.setNumber(1);
        c4h2.setTechnology(Technology._2D);
        c4h2.setCinemaId(c4.getId());
        hallRepository.save(c4h2);

        c4h3.setColumns(23);
        c4h3.setRows(10);
        c4h3.setNumber(2);
        c4h3.setTechnology(Technology._2D);
        c4h3.setCinemaId(c4.getId());
        hallRepository.save(c4h3);

        c4h4.setColumns(15);
        c4h4.setRows(13);
        c4h4.setNumber(1);
        c4h4.setTechnology(Technology._RM_PLUS);
        c4h4.setCinemaId(c4.getId());
        hallRepository.save(c4h4);
    }
}
