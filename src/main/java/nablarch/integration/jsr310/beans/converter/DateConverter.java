package nablarch.integration.jsr310.beans.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import nablarch.core.beans.Converter;
import nablarch.integration.jsr310.util.DateTimeUtil;


/**
 * Date and Time APIをサポートし、{@code java.util.Date}型への変換を行う {@link Converter} 。
 * <p>
 * 変換元の型に応じて、以下のとおり変換を行う。
 * <p>
 * <b>日付型({@code java.time.LocalDate})</b>：<br>
 * 同一日付を表す{@code java.util.Date}オブジェクトを返却する。
 * <p>
 * <b>日時型({@code java.time.LocalDateTime})</b>：<br>
 * 同一日付を表す{@code java.util.Date}オブジェクトを返却する。
 * <p>
 * <b>上記以外</b>：<br>
 * {@link nablarch.core.beans.converter.DateConverter}に委譲する。
 * @author TIS
 */
public class DateConverter extends nablarch.core.beans.converter.DateConverter {

    @Override
    public Date convert(final Object value) {
        if (value instanceof LocalDateTime) {
            return DateTimeUtil.getDate(LocalDateTime.class.cast(value));
        } else if (value instanceof LocalDate) {
            return DateTimeUtil.getDate(LocalDate.class.cast(value));
        } else {
            return super.convert(value);
        }
    }
}
