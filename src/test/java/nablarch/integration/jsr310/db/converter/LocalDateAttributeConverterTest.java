package nablarch.integration.jsr310.db.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.time.LocalDate;

import org.eclipse.persistence.internal.descriptors.InteractionArgument;

import nablarch.core.util.DateUtil;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link LocalDateAttributeConverter}のテスト。
 */
@RunWith(Enclosed.class)
public class LocalDateAttributeConverterTest {

    public static class ConvertToDatabaseTest {

        private final LocalDateAttributeConverter sut = new LocalDateAttributeConverter();

        @Test
        public void DBの型にLocalDateを指定した場合はsqlDateになる() throws Exception {
            assertThat(sut.convertToDatabase(LocalDate.of(2017, 1, 2), LocalDate.class))
                    .isEqualTo(DateUtil.getDate("20170102"));
        }

        @Test
        public void DBの型にsqlDateを指定した場合はsqlDateになる() throws Exception {
            assertThat(sut.convertToDatabase(LocalDate.of(2017, 2, 3), java.sql.Date.class))
                    .isEqualTo(DateUtil.getDate("20170203"));
        }

        @Test
        public void サポート対象外の型を指定した場合は例外が送出される() throws Exception {
            assertThatThrownBy(() -> sut.convertToDatabase(LocalDate.of(2017, 1, 1), Integer.class))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("unsupported database type:java.lang.Integer");

        }
    }

    public static class ConvertFromDatabaseTest {

        private final LocalDateAttributeConverter sut = new LocalDateAttributeConverter();

        @Test
        public void nullはnullが返されること() throws Exception {
            assertThat(sut.convertFromDatabase(null))
                    .isNull();
        }

        @Test
        public void LocalDateはLocalDateが戻されること() throws Exception {
            assertThat(sut.convertFromDatabase(LocalDate.of(2016, 12, 31)))
                    .isEqualTo(LocalDate.of(2016, 12, 31));
        }

        @Test
        public void sqlDateからLocalDateに変換されること() throws Exception {
            assertThat(sut.convertFromDatabase(Date.valueOf("2017-01-02")))
                    .isEqualTo(LocalDate.of(2017, 1, 2));
        }

        @Test
        public void utilDateからLocalDateに変換されること() throws Exception {
            assertThat(sut.convertFromDatabase(DateUtil.getDate("20000229")))
                    .isEqualTo(LocalDate.of(2000, 2, 29));
        }

        @Test
        public void サポート対象外の型の場合例外が送出されること() throws Exception {
            assertThatThrownBy(() -> sut.convertFromDatabase(100))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("unsupported data type:java.lang.Integer, value:100");
        }
    }
}