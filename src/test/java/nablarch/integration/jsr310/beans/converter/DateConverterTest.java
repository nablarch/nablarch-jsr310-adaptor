package nablarch.integration.jsr310.beans.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import nablarch.core.beans.ConversionException;
import nablarch.core.beans.Converter;
import nablarch.core.util.DateUtil;

/**
 * {@link DateConverter}のテスト
 */
@RunWith(Enclosed.class)
public class DateConverterTest {
    @RunWith(Theories.class)
    public static class DateConvertSuccessTest {
        @DataPoints
        public static Object[][] params = {
                {"20170601", DateUtil.getParsedDate("20170601000000000", "yyyyMMddHHmmssSSS")},
                {LocalDate.of(2017, 6, 12), DateUtil.getParsedDate("20170612000000000", "yyyyMMddHHmmssSSS")},
                {LocalDateTime.of(2017, 6, 13, 12, 30, 15, 100000000), DateUtil.getParsedDate("20170613123015100", "yyyyMMddHHmmssSSS")},
                {DateUtil.getParsedDate("20170621030501200", "yyyyMMddHHmmssSSS"), DateUtil.getParsedDate("20170621030501200", "yyyyMMddHHmmssSSS")}
        };

        @Theory
        public void test(Object[] testParams) {
            Object value = testParams[0];
            Date expected = (Date) testParams[1];

            Converter sut = new DateConverter();
            assertThat(sut.convert(value), is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class DateConvertParseFailTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {"abc"};

        @Theory
        public void test(Object value) {
            expectedException.expect(IllegalArgumentException.class);
            Converter converter = new DateConverter();
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
            Converter converter = new DateConverter();
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
            assertEquals(new Date(Timestamp.valueOf("2018-02-21 00:00:00").getTime()),
                    sut.convert("20180221"));
        }

        @Test
        public void パターン指定() {
            final SqlDateConverter sut = new SqlDateConverter(
                    Collections.singletonList("yyyy/MM/dd"));
            assertEquals(new Date(Timestamp.valueOf("2018-02-21 00:00:00").getTime()),
                    sut.convert("2018/02/21"));
        }
    }
}
