package nablarch.integration.jsr310.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.annotation.Published;

/**
 * Date and Time API向けのユーティリティ。
 * <p>
 * 本ユーティリティが使用する日付文字列の形式などは、{@link SystemRepository}より取得する。
 * {@link SystemRepository}からキー名:dateTimeConfigurationで{@link DateTimeConfiguration}が取得出来た場合はそのオブジェクトを、
 * 取得出来ない場合は{@link BasicDateTimeConfiguration}を使用する。
 *
 * @author TIS
 * @see DateTimeConfiguration
 */
@Published
public final class DateTimeUtil {

    /** 日付変換に使用する設定 */
    private static final DateTimeConfiguration DEFAULT_DATE_TIME_CONFIGURATION = new BasicDateTimeConfiguration();

    /**
     * 隠蔽コンストラクタ。
     */
    private DateTimeUtil() {
    }

    /**
     * 日付変換に使用する設定を返す。
     * <p>
     * リポジトリに{@link DateTimeConfiguration}が設定されている場合はそのオブジェクトを、
     * 設定されていない場合は{@link #DEFAULT_DATE_TIME_CONFIGURATION}を返す。
     *
     * @return 日付変換の設定
     */
    static DateTimeConfiguration getDateTimeConfiguration() {
        final DateTimeConfiguration dateTimeConfiguration = SystemRepository.get("dateTimeConfiguration");

        if (dateTimeConfiguration != null) {
            return dateTimeConfiguration;
        } else {
            return DEFAULT_DATE_TIME_CONFIGURATION;
        }
    }

    /**
     * 日付文字列を{@link LocalDate}に変換する。
     * <p>
     * 日付文字列のフォーマットは、{@link DateTimeConfiguration#getDateFormatter()} より取得する。
     *
     * @param date 日付文字列(yyyyMMdd形式)
     * @return 日付文字列をパースして生成した{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDate getLocalDate(final String date) {
        return LocalDate.parse(date, getDateTimeConfiguration().getDateFormatter());
    }

    /**
     * {@code java.util.Date}のインスタンスを、{@code java.time.LocalDate}に変換する。
     * <p>
     * ゾーンIDは、{@link DateTimeConfiguration#getSystemZoneId()}から取得する。
     *
     * @param date 変換対象の{@code java.util.Date}のインスタンス
     * @return 変換後の{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDate getLocalDate(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), getDateTimeConfiguration().getSystemZoneId())
                            .toLocalDate();
    }

    /**
     * {@code java.sql.Date}のインスタンスを、{@code java.time.LocalDate}に変換する。
     * <p>
     * ※{@code java.sql.Date}は、toInstantメソッドをサポートしていないため
     *
     * @param date 変換対象の{@code java.sql.Date}のインスタンス
     * @return 変換後の{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDate getLocalDateAsSqlDate(final java.sql.Date date) {
        return date.toLocalDate();
    }

    /**
     * {@code java.util.Calendar}のインスタンスを、{@code java.time.LocalDate}に変換する。
     *
     * @param calendar 変換対象の{@code java.util.Calendar}のインスタンス
     * @return 変換後の{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDate getLocalDate(final Calendar calendar) {
        return getLocalDate(calendar.getTime());
    }

    /**
     * 日時文字列を{@link java.time.LocalDateTime}に変換する。
     *
     * @param date 変換対象の日時文字列
     * @return 変換後の値
     */
    public static LocalDateTime getLocalDateTime(final String date) {
        if (date.endsWith("Z")) {
            final Instant instant = Instant.from(
                    getDateTimeConfiguration().getDateTimeFormatter().parse(date));
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        } else {
            return LocalDateTime.parse(date, getDateTimeConfiguration().getDateTimeFormatter());
        }
    }

    /**
     * {@code java.util.Date}のインスタンスを、{@code java.time.LocalDateTime}に変換する
     *
     * @param date 変換対象の{@code java.util.Date}のインスタンス
     * @return 変換後の{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDateTime getLocalDateTime(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), getDateTimeConfiguration().getSystemZoneId());
    }

    /**
     * {@code java.sql.Date}のインスタンスを、{@code java.time.LocalDateTime}に変換する
     * <p>
     * ※{@code java.sql.Date}は、toInstantメソッドをサポートしていないため
     *
     * @param date 変換対象の{@code java.sql.Date}のインスタンス
     * @return 変換後の{@code java.time.LocalDateTime}のインスタンス
     */
    public static LocalDateTime getLocalDateTimeAsSqlDate(final java.sql.Date date) {
        return date.toLocalDate()
                   .atStartOfDay();
    }

    /**
     * {@code java.util.Calendar}のインスタンスを、{@code java.time.LocalDateTime}に変換する
     *
     * @param calendar 変換対象の{@code java.util.Calendar}のインスタンス
     * @return 変換後の{@code java.time.LocalDateTime}のインスタンス
     */
    public static LocalDateTime getLocalDateTime(final Calendar calendar) {
        return getLocalDateTime(calendar.getTime());
    }

    /**
     * {@code java.time.LocalDateTime}のインスタンスを{@code java.util.Date}に変換する
     *
     * @param dateTime 変換対象の{@code java.time.LocalDate}のインスタンス
     * @return 変換後の{@code java.util.Date}のインスタンス
     */
    public static Date getDate(final LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(getDateTimeConfiguration().getSystemZoneId())
                                 .toInstant());
    }

    /**
     * {@code java.time.LocalDate}のインスタンスを{@code java.util.Date}に変換する
     *
     * @param date 変換対象の{@code java.time.LocalDate}のインスタンス
     * @return 変換後の{@code java.util.Date}のインスタンス
     */
    public static Date getDate(final LocalDate date) {
        return Date.from(LocalDate.class.cast(date)
                                        .atStartOfDay(getDateTimeConfiguration().getSystemZoneId())
                                        .toInstant());
    }
}
