package nablarch.integration.jsr310.beans.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import nablarch.integration.jsr310.util.DateTimeUtil;
import nablarch.core.beans.ConversionException;
import nablarch.core.beans.Converter;
import nablarch.core.beans.converter.SingleValueExtracter;

/**
 * {@code java.time.LocalDateTime}型への変換を行う {@link Converter} 。
 * <p>
 * 変換元の型に応じて、以下のとおり変換を行う。
 * <p>
 * <b>日付型({@code java.time.LocalDate})</b>：<br>
 * 同一日付を表す{@code java.time.LocalDateTime}オブジェクトを返却する。
 * <p>
 * <b>日時型({@code java.time.LocalDateTime})</b>：<br>
 * 同一日時を表す{@code java.time.LocalDateTime}オブジェクトを返却する。
 * <p>
 * <b>日付型</b>：<br>
 * 同一日付を表す{@code java.time.LocalDateTime}オブジェクトを返却する。
 * <p>
 * <b>文字列型</b>：<br>
 * 日付文字列と同一日付を表す{@code java.time.LocalDateTime}オブジェクトを返却する。
 * <p>
 * <b>文字列型の配列</b>：<br>
 * 要素数が1であれば、その要素を{@code java.time.LocalDateTime}オブジェクトに変換して返却する。
 * 要素数が1以外であれば、{@link ConversionException}を送出する。
 * <p>
 * <b>上記以外</b>：<br>
 * {@link ConversionException}を送出する。
 * @author TIS
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public LocalDateTime convert(final Object value) {
        if (value instanceof LocalDate) {
            return LocalDateTime.of(LocalDate.class.cast(value), LocalTime.of(0, 0, 0));
        } else if (value instanceof LocalDateTime) {
            return LocalDateTime.class.cast(value);
        } else if (value instanceof java.sql.Date) {
            return DateTimeUtil.getLocalDateTimeAsSqlDate((java.sql.Date) value);
        } else if (value instanceof Date) {
            return DateTimeUtil.getLocalDateTime(Date.class.cast(value));
        } else if (value instanceof Calendar) {
            return DateTimeUtil.getLocalDateTime(Calendar.class.cast(value));
        } else if (value instanceof String) {
            return DateTimeUtil.getLocalDateTime(String.class.cast(value));
        } else if (value instanceof String[]) {
            return SingleValueExtracter.toSingleValue((String[]) value, this, LocalDateTime.class);
        } else {
            throw new ConversionException(LocalDate.class, value);
        }
    }
}
