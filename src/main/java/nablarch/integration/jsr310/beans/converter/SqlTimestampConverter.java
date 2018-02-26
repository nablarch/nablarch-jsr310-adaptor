package nablarch.integration.jsr310.beans.converter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import nablarch.core.beans.Converter;

/**
 * Date and Time APIをサポートし、{@code java.sql.Timestamp}型への変換を行う {@link Converter} 。
 * <p>
 * 変換元の型に応じて、以下のとおり変換を行う。
 * <p>
 * <b>日付型({@code java.time.LocalDate})</b>：<br>
 * 同一日付を表す{@code java.sql.Timestamp}オブジェクトを返却する。
 * <p>
 * <b>日時型({@code java.time.LocalDateTime})</b>：<br>
 * 同一日付・時刻を表す{@code java.sql.Timestamp}オブジェクトを返却する。
 * <p>
 * <b>上記以外</b>：<br>
 * {@link nablarch.core.beans.converter.SqlTimestampConverter}に委譲する。
 * @author TIS
 */
public class SqlTimestampConverter extends nablarch.core.beans.converter.SqlTimestampConverter {

    /**
     * デフォルトコンストラクタ
     */
    public SqlTimestampConverter() {
        super();
    }

    /**
     * 日付パターンを設定してインスタンスを構築する。
     * 
     * @param patterns 日付パターン
     */
    public SqlTimestampConverter(List<String> patterns) {
        super(patterns);
    }

    @Override
    public Timestamp convert(final Object value) {
        if (value instanceof LocalDate) {
            return Timestamp.valueOf(LocalDate.class.cast(value).atStartOfDay());
        } else if (value instanceof LocalDateTime) {
            return Timestamp.valueOf(LocalDateTime.class.cast(value));
        } else {
            return super.convert(value);
        }
    }

}
