package nablarch.integration.jsr310.beans.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import nablarch.core.beans.ConversionException;
import nablarch.core.beans.Converter;
import nablarch.core.util.DateUtil;
import org.junit.Rule;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * {@link LocalDateConverter}のテスト
 */
@RunWith(Enclosed.class)
public class LocalDateConverterTest {
    static java.sql.Date newSqlDate(String date, String pattern) {
        return new java.sql.Date(DateUtil.getParsedDate(date, pattern).getTime());
    }

    @RunWith(Theories.class)
    public static class LocalDateConvertSuccessTest {
        @DataPoints
        public static Object[][] params = {
                {"20170601", LocalDate.of(2017, 6, 1)},
                {LocalDate.of(2017, 6, 12), LocalDate.of(2017, 6, 12)},
                {LocalDateTime.of(2017, 6, 13, 12, 30, 15), LocalDate.of(2017, 6, 13)},
                {DateUtil.getParsedDate("20170621000000000", "yyyyMMddHHmmssSSS"), LocalDate.of(2017, 6, 21)},
                {DateUtil.getParsedDate("20170622235011300", "yyyyMMddHHmmssSSS"), LocalDate.of(2017, 6, 22)},
                {newSqlDate("19490403000000", "yyyyMMddHHmmss"), LocalDate.of(1949, 4, 3)}
        };

        @Theory
        public void test(Object[] testParams) {
            Object value = testParams[0];
            LocalDate expected = (LocalDate) testParams[1];

            Converter converter = new LocalDateConverter();
            assertThat(converter.convert(value), is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class DateConvertParseFailTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {"abc", "201706251045123"};

        @Theory
        public void test(Object value) {
            expectedException.expect(DateTimeParseException.class);
            Converter converter = new LocalDateConverter();
            converter.convert(value);
        }
    }

    @RunWith(Theories.class)
    public static class DateConvertUnsupportedTypeTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {10};

        @Theory
        public void test(Object value) {
            expectedException.expect(ConversionException.class);
            Converter converter = new LocalDateConverter();
            converter.convert(value);
        }
    }
}
