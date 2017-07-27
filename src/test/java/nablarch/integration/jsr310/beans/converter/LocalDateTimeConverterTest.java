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
 * {@link LocalDateTimeConverter}のテスト
 */
@RunWith(Enclosed.class)
public class LocalDateTimeConverterTest {
    static java.sql.Date newSqlDate(String date, String pattern) {
        return new java.sql.Date(DateUtil.getParsedDate(date, pattern).getTime());
    }

    @RunWith(Theories.class)
    public static class LocalDateTimeConvertSuccessTest {
        @DataPoints
        public static Object[][] params = {
                {"2017-06-01T10:22:30.100Z", LocalDateTime.of(2017, 6, 1, 10, 22, 30, 100000000)},
                {LocalDate.of(2017, 6, 12), LocalDateTime.of(2017, 6, 12, 0, 0, 0)},
                {LocalDateTime.of(2017, 6, 13, 12, 30, 15), LocalDateTime.of(2017, 6, 13, 12, 30, 15)},
                {DateUtil.getParsedDate("20170621030530500", "yyyyMMddHHmmssSSS"), LocalDateTime.of(2017, 6, 21, 3, 5, 30, 500000000)},
                {DateUtil.getParsedDate("20170622235011300", "yyyyMMddHHmmssSSS"), LocalDateTime.of(2017, 6, 22, 23, 50, 11, 300000000)},
                {newSqlDate("20170623123015", "yyyyMMddHHmmss"), LocalDateTime.of(2017, 6, 23, 0, 0, 0)}
        };

        @Theory
        public void test(Object[] testParams) {
            Object value = testParams[0];
            LocalDateTime expected = (LocalDateTime) testParams[1];

            Converter converter = new LocalDateTimeConverter();
            assertThat(converter.convert(value), is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class DateConvertParseFailTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {"abc", "20170625"};

        @Theory
        public void test(Object value) {
            expectedException.expect(DateTimeParseException.class);
            Converter converter = new LocalDateTimeConverter();
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
            Converter converter = new LocalDateTimeConverter();
            converter.convert(value);
        }
    }
}