package nablarch.integration.jsr310.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.annotation.Published;
import nablarch.core.beans.converter.DateTimeConverterUtil;

/**
 * Date and Time API向けのユーティリティ。
 * <p>
 * 本ユーティリティが使用する日付文字列の形式などは、{@link SystemRepository}より取得する。
 * {@link SystemRepository}からキー名:dateTimeConfigurationで{@link DateTimeConfiguration}が取得出来た場合はそのオブジェクトを、
 * 取得出来ない場合は{@link BasicDateTimeConfiguration}を使用する。
 * <p>
 * 本アダプタで提供される機能はNablarch本体に取り込まれており、本アダプタは後方互換を維持するために残している。
 * 新しく使用する場合は、Nablarch本体の{@link nablarch.core.beans.converter.DateTimeConverterUtil}を使用すること。
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
        return DateTimeConverterUtil.getLocalDate(date);
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
        return DateTimeConverterUtil.getLocalDate(date);
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
        return DateTimeConverterUtil.getLocalDateAsSqlDate(date);
    }

    /**
     * {@code java.util.Calendar}のインスタンスを、{@code java.time.LocalDate}に変換する。
     *
     * @param calendar 変換対象の{@code java.util.Calendar}のインスタンス
     * @return 変換後の{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDate getLocalDate(final Calendar calendar) {
        return DateTimeConverterUtil.getLocalDate(calendar);
    }

    /**
     * 日時文字列を{@link java.time.LocalDateTime}に変換する。
     *
     * @param date 変換対象の日時文字列
     * @return 変換後の値
     */
    public static LocalDateTime getLocalDateTime(final String date) {
        return DateTimeConverterUtil.getLocalDateTime(date);
    }

    /**
     * {@code java.util.Date}のインスタンスを、{@code java.time.LocalDateTime}に変換する
     *
     * @param date 変換対象の{@code java.util.Date}のインスタンス
     * @return 変換後の{@code java.time.LocalDate}のインスタンス
     */
    public static LocalDateTime getLocalDateTime(final Date date) {
        return DateTimeConverterUtil.getLocalDateTime(date);
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
        return DateTimeConverterUtil.getLocalDateTimeAsSqlDate(date);
    }

    /**
     * {@code java.util.Calendar}のインスタンスを、{@code java.time.LocalDateTime}に変換する
     *
     * @param calendar 変換対象の{@code java.util.Calendar}のインスタンス
     * @return 変換後の{@code java.time.LocalDateTime}のインスタンス
     */
    public static LocalDateTime getLocalDateTime(final Calendar calendar) {
        return DateTimeConverterUtil.getLocalDateTime(calendar);
    }

    /**
     * {@code java.time.LocalDateTime}のインスタンスを{@code java.util.Date}に変換する
     *
     * @param dateTime 変換対象の{@code java.time.LocalDateTime}のインスタンス
     * @return 変換後の{@code java.util.Date}のインスタンス
     */
    public static Date getDate(final LocalDateTime dateTime) {
        return DateTimeConverterUtil.getDate(dateTime);
    }

    /**
     * {@code java.time.LocalDateTime}のインスタンスを{@code java.sql.Timestamp}に変換する
     *
     * @param dateTime 変換対象の{@code java.time.LocalDateTime}のインスタンス
     * @return 変換後の{@code Timestamp}のインスタンス
     */
    public static Timestamp getTimestamp(final LocalDateTime dateTime) {
        return DateTimeConverterUtil.getTimestamp(dateTime);
    }

    /**
     * {@code java.time.LocalDate}のインスタンスを{@code java.util.Date}に変換する
     *
     * @param date 変換対象の{@code java.time.LocalDate}のインスタンス
     * @return 変換後の{@code java.util.Date}のインスタンス
     */
    public static Date getDate(final LocalDate date) {
        return DateTimeConverterUtil.getDate(date);
    }
}
