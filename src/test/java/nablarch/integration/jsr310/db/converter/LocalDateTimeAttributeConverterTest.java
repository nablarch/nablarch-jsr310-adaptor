package nablarch.integration.jsr310.db.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link LocalDateTimeAttributeConverter｝テストクラス。
 */
@RunWith(Enclosed.class)
public class LocalDateTimeAttributeConverterTest {

    public static class ConvertToDatabase {

        private final LocalDateTimeAttributeConverter sut = new LocalDateTimeAttributeConverter();

        @Test
        public void DBの型にLocalDateTimeを指定した場合Timestampとなること() throws Exception {
            assertThat(sut.convertToDatabase(LocalDateTime.of(2017, 8, 9, 1, 2, 3, 123321123), LocalDateTime.class))
                    .isEqualTo(Timestamp.valueOf("2017-08-09 01:02:03.123321123"));
        }
        
        @Test
        public void DBの型にTimestampをしていたい場合Timestampとなること() throws Exception {
            assertThat(sut.convertToDatabase(LocalDateTime.of(1980, 2, 29, 11, 22, 33, 333222111), Timestamp.class))
                    .isEqualTo(Timestamp.valueOf("1980-02-29 11:22:33.333222111"));
        }
        
        @Test
        public void サポート対象外の型を指定した場合例外が送出されること() throws Exception {
            assertThatThrownBy(() -> sut.convertToDatabase(LocalDateTime.of(2017, 1, 2, 3, 4), Integer.class))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("unsupported database type:java.lang.Integer");
        }
    }
    
    public static class ConvertFromDatabase {

        private final LocalDateTimeAttributeConverter sut = new LocalDateTimeAttributeConverter();

        @Test
        public void nullはnullが戻される() throws Exception {
            assertThat(sut.convertFromDatabase(null))
                    .isNull();
        }

        @Test
        public void LocalDateTimeはそのまま戻されること() throws Exception {
            assertThat(sut.convertFromDatabase(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 123456789)))
                    .isEqualTo(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 123456789));
        }
        
        @Test
        public void TimestampがLocalDateTimeに変換されて戻されること() throws Exception {
            assertThat(sut.convertFromDatabase(Timestamp.valueOf("2010-01-02 11:22:33.111222333")))
                    .isEqualTo(LocalDateTime.of(2010, 1, 2, 11, 22, 33, 111222333));
        }

        @Test
        public void サポート対象外の型の場合例外が送出されること() throws Exception {
            assertThatThrownBy(() -> sut.convertFromDatabase("20170101"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("unsupported data type:java.lang.String, value:20170101");
        }
    }
}