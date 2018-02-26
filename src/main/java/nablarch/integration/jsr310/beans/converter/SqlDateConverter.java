package nablarch.integration.jsr310.beans.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import nablarch.core.beans.Converter;

/**
 * Date and Time APIをサポートし、{@code java.sql.Date}型への変換を行う {@link Converter} 。
 * <p>
 * 変換元の型に応じて、以下のとおり変換を行う。
 * <p>
 * <b>日付型({@code java.time.LocalDate})</b>：<br>
 * 同一日付を表す{@code java.sql.Date}オブジェクトを返却する。
 * <p>
 * <b>日時型({@code java.time.LocalDateTime})</b>：<br>
 * 同一日付を表す{@code java.sql.Date}オブジェクトを返却する。
 * (時刻は切り捨て)
 * <p>
 * <b>上記以外</b>：<br>
 * {@link nablarch.core.beans.converter.SqlDateConverter}に委譲する。
 *
 * @author TIS
 */
public class SqlDateConverter extends nablarch.core.beans.converter.SqlDateConverter {

    /**
     * デフォルトコンストラクタ
     */
    public SqlDateConverter() {
        super();
    }

    /**
     * 日付パターンを設定してインスタンスを構築する。
     * 
     * @param patterns 日付パターン
     */
    public SqlDateConverter(List<String> patterns) {
        super(patterns);
    }

    @Override
    public java.sql.Date convert(final Object value) {
        if (value instanceof LocalDate) {
            return java.sql.Date.valueOf(LocalDate.class.cast(value));
        } else if (value instanceof LocalDateTime) {
            return java.sql.Date.valueOf(LocalDateTime.class.cast(value).toLocalDate());
        } else {
            return super.convert(value);
        }
    }
}
