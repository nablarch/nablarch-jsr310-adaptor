package nablarch.integration.jsr310.beans.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import nablarch.core.beans.ConversionException;
import nablarch.core.beans.Converter;
import nablarch.core.util.DateUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * {@link SqlDateConverter}のテスト
 */
@RunWith(Enclosed.class)
public class SqlDateConverterTest {
    
    static Date newSqlDate(String date, String pattern) {
        return new Date(DateUtil.getParsedDate(date, pattern).getTime());
    }

    @RunWith(Theories.class)
    public static class SqlDateConvertSuccessTest {
        @DataPoints
        public static Object[][] params = {
                {"20170601", newSqlDate("20170601000000000", "yyyyMMddHHmmssSSS")},
                {LocalDate.of(2017, 6, 12), newSqlDate("20170612000000000", "yyyyMMddHHmmssSSS")},
                {LocalDateTime.of(2017, 6, 13, 12, 30, 15, 500000000), newSqlDate("20170613000000000", "yyyyMMddHHmmssSSS")},
                {DateUtil.getParsedDate("20170621030515300", "yyyyMMddHHmmssSSS"), newSqlDate("20170621000000000", "yyyyMMddHHmmssSSS")}
        };

        @Theory
        public void test(Object[] testParams) {
            Object value = testParams[0];
            Date expected = (Date) testParams[1];

            Converter converter = new SqlDateConverter();
            assertThat(converter.convert(value), is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class SqlDateConvertParseFailTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {"abc"};

        @Theory
        public void test(Object value) {
            expectedException.expect(IllegalArgumentException.class);
            Converter converter = new SqlDateConverter();
            converter.convert(value);
        }
    }

    @RunWith(Theories.class)
    public static class SqlDateConvertUnsupportedTypeTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {10};

        @Theory
        public void test(Object value) {
            expectedException.expect(ConversionException.class);
            Converter converter = new SqlDateConverter();
            converter.convert(value);
        }
    }

    /**
     * 日付パターンのテスト。
     *
     */
    public static class PatternTest {

        @Test
        public void デフォルト() {
            final SqlDateConverter sut = new SqlDateConverter();
            assertEquals(Date.valueOf("2018-02-21"), sut.convert("20180221"));
        }

        @Test
        public void パターン指定() {
            final SqlDateConverter sut = new SqlDateConverter(
                    Collections.singletonList("yyyy/MM/dd"));
            assertEquals(Date.valueOf("2018-02-21"), sut.convert("2018/02/21"));
        }
    }
}
