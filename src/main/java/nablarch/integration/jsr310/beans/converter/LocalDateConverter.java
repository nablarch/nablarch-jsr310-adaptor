package nablarch.integration.jsr310.beans.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nablarch.integration.jsr310.util.DateTimeUtil;
import nablarch.core.beans.ConversionException;
import nablarch.core.beans.Converter;
import nablarch.core.beans.converter.SingleValueExtracter;

/**
 * {@code java.time.LocalDate}型への変換を行う {@link Converter} 。
 * <p>
 * 変換元の型に応じて、以下のとおり変換を行う。
 * <p>
 * <b>日付型({@code java.time.LocalDate})</b>：<br>
 * 同一日付を表す{@code java.time.LocalDate}オブジェクトを返却する。
 * <p>
 * <b>日時型({@code java.time.LocalDateTime})</b>：<br>
 * 同一日付を表す{@code java.time.LocalDate}オブジェクトを返却する。
 * (時刻は切り捨て)
 * <p>
 * <b>日付型</b>：<br>
 * 同一日付を表す{@code java.time.LocalDate}オブジェクトを返却する。
 * (時刻は切り捨て)
 * <p>
 * <b>文字列型</b>：<br>
 * 日付文字列と同一日付を表す{@code java.time.LocalDate}オブジェクトを返却する。
 * (時刻は切り捨て)
 * <p>
 * <b>文字列型の配列</b>：<br>
 * 要素数が1であれば、その要素を{@code java.time.LocalDate}オブジェクトに変換して返却する。
 * 要素数が1以外であれば、{@link ConversionException}を送出する。
 * <p>
 * <b>上記以外</b>：<br>
 * {@link ConversionException}を送出する。
 *
 * @author TIS
 */
public class LocalDateConverter implements Converter<LocalDate> {

    /** 日付パターン */
    private final List<DateTimeFormatter> formatters;

    /**
     * デフォルトコンストラクタ
     */
    public LocalDateConverter() {
        this.formatters = Collections.emptyList();
    }

    /**
     * 日付パターンを設定してインスタンスを構築する。
     * 
     * @param patterns 日付パターン
     */
    public LocalDateConverter(List<String> patterns) {
        this.formatters = patterns.stream()
                .map(DateTimeFormatter::ofPattern)
                .collect(Collectors.toList());
    }

    @Override
    public LocalDate convert(final Object value) {
        if (value instanceof LocalDate) {
            return LocalDate.class.cast(value);
        } else if (value instanceof LocalDateTime) {
            return LocalDate.from(LocalDateTime.class.cast(value));
        } else if (value instanceof java.sql.Date) {
            return DateTimeUtil.getLocalDateAsSqlDate((java.sql.Date) value);
        } else if (value instanceof Date) {
            return DateTimeUtil.getLocalDate(Date.class.cast(value));
        } else if (value instanceof Calendar) {
            return DateTimeUtil.getLocalDate(Calendar.class.cast(value));
        } else if (value instanceof String) {
            return convertFromString(String.class.cast(value));
        } else if (value instanceof String[]) {
            return SingleValueExtracter.toSingleValue((String[]) value, this, LocalDate.class);
        } else {
            throw new ConversionException(LocalDate.class, value);
        }
    }

    private LocalDate convertFromString(String value) {
        if (formatters.isEmpty() == false) {
            DateTimeParseException lastThrownException = null;
            for (DateTimeFormatter formatter : formatters) {
                try {
                    return LocalDate.parse(value, formatter);
                } catch (DateTimeParseException ignore) {
                    //複数のパターンを順番に試すのでParseExceptionは無視する
                    lastThrownException = ignore;
                }
            }
            //すべてのパターンが失敗した場合は例外をスロー
            throw new IllegalArgumentException(
                    "the string was not formatted " + formatters + ". date = " + value + ".",
                    lastThrownException);
        }
        return DateTimeUtil.getLocalDate(value);
    }
}
