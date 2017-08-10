package nablarch.integration.jsr310.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.DateUtil;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link DateTimeUtil}のテスト。
 */
public class DateTimeUtilTest {

    @Before
    public void setUp() throws Exception {
        SystemRepository.clear();
    }

    @Test
    public void getLocalDateFromString() throws Exception {
        assertThat(DateTimeUtil.getLocalDate("19490403"), is(LocalDate.of(1949, Month.APRIL, 3)));
        
        SystemRepository.load(() -> Collections.singletonMap("dateTimeConfiguration", new BasicDateTimeConfiguration() {
            @Override
            public DateTimeFormatter getDateFormatter() {
                return DateTimeFormatter.ofPattern("yyyy年MM月dd日");
            }
        }));

        assertThat(DateTimeUtil.getLocalDate("2017年07月26日"), is(LocalDate.of(2017, Month.JULY, 26)));
    }

    @Test
    public void getLocalDateFromUtilDate() throws Exception {
        final Date date = DateUtil.getDate("20170401");
        assertThat(DateTimeUtil.getLocalDate(date), is(LocalDate.of(2017, Month.APRIL, 1)));
    }

    @Test
    public void getLocalDateAsSqlDate() throws Exception {
        final Date date = DateUtil.getDate("20161231");
        assertThat(DateTimeUtil.getLocalDateAsSqlDate(new java.sql.Date(date.getTime())),
                is(LocalDate.of(2016, Month.DECEMBER, 31)));
    }

    @Test
    public void getLocalDateFromCalendar() throws Exception {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        assertThat(DateTimeUtil.getLocalDate(calendar), is(LocalDate.of(2017, 6, 15)));
    }

    @Test
    public void getLocalDateTimeFromString() throws Exception {
        assertThat(DateTimeUtil.getLocalDateTime("2017-01-02T03:04:05Z"),
                is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5)));

        
        SystemRepository.load(() -> Collections.singletonMap("dateTimeConfiguration", new BasicDateTimeConfiguration() {
            @Override
            public DateTimeFormatter getDateTimeFormatter() {
                return DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
            }
        }));
        assertThat(DateTimeUtil.getLocalDateTime("2014/01/02 11:22:33"),
                is(LocalDateTime.of(2014, 1, 2, 11, 22, 33)));
    }

    @Test
    public void getLocalDateTimeFromUtilDate() throws Exception {
        final Timestamp timestamp = Timestamp.valueOf("2017-01-02 03:04:05");
        assertThat(DateTimeUtil.getLocalDateTime(new Date(timestamp.getTime())),
                is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5)));
    }

    @Test
    public void getLocalDateTimeAsSqlDate() throws Exception {
        final Timestamp timestamp = Timestamp.valueOf("2017-12-02 03:04:05");
        assertThat(DateTimeUtil.getLocalDateTimeAsSqlDate(new java.sql.Date(timestamp.getTime())),
                is(LocalDateTime.of(2017, 12, 2, 0, 0)));
    }

    @Test
    public void getLocalDAteTimeFromCalendar() throws Exception {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 35);
        calendar.set(Calendar.MILLISECOND, 100);

        assertThat(DateTimeUtil.getLocalDateTime(calendar),
                is(LocalDateTime.of(2017, 6, 15, 22, 30, 35, 100000000)));
    }

    @Test
    public void getDateFromLocalDate() throws Exception {
        assertThat(DateTimeUtil.getDate(LocalDate.of(2017, 11, 12)),
                is(DateUtil.getDate("20171112")));
    }

    @Test
    public void getDateFromLocalDateTime() throws Exception {
        assertThat(DateTimeUtil.getDate(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 123456789)),
                is(DateUtil.getParsedDate("2017/01/02 03:04:05.123", "yyyy/MM/dd hh:mm:ss.SSS")));
    }
    
    @Test
    public void getTimestampFromLocalDateTime() throws Exception {
        assertThat(DateTimeUtil.getDate(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 123456789)),
                is(Timestamp.valueOf("2017-01-02 03:04:05.123456789")));
    }
}