package nablarch.integration.jsr310.beans.converter;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

public class Jsr310StringConverterTest {

    @Test
    public void LocalDate_デフォルト() {
        Jsr310StringConverter sut = new Jsr310StringConverter();
        assertEquals("2018-02-21", sut.convert(LocalDate.of(2018, 2, 21)));
    }

    @Test
    public void LocalDateTime_デフォルト() {
        Jsr310StringConverter sut = new Jsr310StringConverter();
        assertEquals("2018-02-21T15:10", sut.convert(LocalDateTime.of(2018, 2, 21, 15, 10)));
    }

    @Test
    public void LocalDate_パターン指定() {
        Jsr310StringConverter sut = new Jsr310StringConverter("yyyy/MM/dd", null);
        assertEquals("2018/02/21", sut.convert(LocalDate.of(2018, 2, 21)));
    }

    @Test
    public void LocalDateTime_パターン指定() {
        Jsr310StringConverter sut = new Jsr310StringConverter("yyyy/MM/dd", null);
        assertEquals("2018/02/21", sut.convert(LocalDateTime.of(2018, 2, 21, 15, 10)));
    }

    @Test
    public void マージ() {
        Jsr310StringConverter sut = new Jsr310StringConverter()
                .merge(new Jsr310StringConverter("yyyy/MM/dd", null));
        assertEquals("2018/02/21", sut.convert(LocalDate.of(2018, 2, 21)));
    }

    @Test
    public void マージ_レシーバが優先() {
        Jsr310StringConverter sut = new Jsr310StringConverter("yyyy/MM/dd", null)
                .merge(new Jsr310StringConverter("yyyy.MM.dd", null));
        assertEquals("2018/02/21", sut.convert(LocalDate.of(2018, 2, 21)));
    }
}
