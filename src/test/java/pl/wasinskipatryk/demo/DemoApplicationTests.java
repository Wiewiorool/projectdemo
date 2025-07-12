package pl.wasinskipatryk.demo;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Profile("test")
@SpringBootTest
class DemoApplicationTests {

    @Test
    public void test() {
        //given
        // wszystkie potrzebne  zmienne, obiekty, mocki

        //when
        //wywoływanie metody testowanie

        //then
        //sprawdzenie, czy zwrocne przez metode dane sa spodziewane i poprawne

    }

}
