package nablarch.integration.jsr310.beans.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * {@link SqlTimestampConverter}のテスト
 */
@RunWith(Enclosed.class)
public class SqlTimestampConverterTest {
    static Timestamp newSqlTimestamp(String date, String pattern) {
        return new Timestamp(DateUtil.getParsedDate(date, pattern).getTime());
    }

    @RunWith(Theories.class)
    public static class SqlTimestampConvertSuccessTest {
        @DataPoints
        public static Object[][] params = {
                {"20170601", newSqlTimestamp("20170601000000000", "yyyyMMddHHmmssSSS")},
                {LocalDate.of(2017, 6, 12), newSqlTimestamp("20170612000000000", "yyyyMMddHHmmssSSS")},
                {LocalDateTime.of(2017, 6, 13, 12, 30, 15), newSqlTimestamp("20170613123015000", "yyyyMMddHHmmssSSS")},
                {DateUtil.getParsedDate("20170621030501200", "yyyyMMddHHmmssSSS"), newSqlTimestamp("20170621030501200", "yyyyMMddHHmmssSSS")}
        };

        @Theory
        public void test(Object[] testParams) {
            Object value = testParams[0];
            Timestamp expected = (Timestamp) testParams[1];

            Converter converter = new SqlTimestampConverter();
            assertThat(converter.convert(value), is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class SqlTimestampConvertParseFailTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {"abc"};

        @Theory
        public void test(Object value) {
            expectedException.expect(IllegalArgumentException.class);
            Converter converter = new SqlTimestampConverter();
            converter.convert(value);
        }
    }

    @RunWith(Theories.class)
    public static class SqlTimestampConvertUnsupportedTypeTest {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @DataPoints
        public static Object[] params = {10};

        @Theory
        public void test(Object value) {
            expectedException.expect(ConversionException.class);
            Converter converter = new SqlTimestampConverter();
            converter.convert(value);
        }
    }
}
