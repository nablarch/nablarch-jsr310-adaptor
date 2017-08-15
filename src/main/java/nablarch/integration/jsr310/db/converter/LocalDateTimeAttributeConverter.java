package nablarch.integration.jsr310.db.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import nablarch.core.db.dialect.converter.AttributeConverter;
import nablarch.integration.jsr310.util.DateTimeUtil;

/**
 * {@link LocalDateTime}をデータベースとの間で入出力するために変換するクラス。
 *
 * @author siosio
 */
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime> {

    /**
     * 以下の型への変換をサポートする。
     * <ul>
     * <li>{@link LocalDateTime}(LocalDateTimeがサポートされていないJDBCドライバがあるため{@link Timestamp}に変換する)</li>
     * </ul>
     * <p>
     * 上記に以外の型への変換はサポートしないため{@link IllegalArgumentException}を送出する。
     * また、{@code null}もサポートしない。
     */
    @Override
    public <DB> Object convertToDatabase(final LocalDateTime javaAttribute, final Class<DB> databaseType) {
        if (LocalDateTime.class.isAssignableFrom(databaseType)
                || Timestamp.class.isAssignableFrom(databaseType)) {
            return DateTimeUtil.getTimestamp(javaAttribute);
        }
        throw new IllegalArgumentException("unsupported database type:"
                + databaseType.getName());
    }

    /**
     * 以下の型からの変換をサポートする。
     *
     * <ul>
     *     <li>{@link LocalDateTime}</li>
     *     <li>{@link Timestamp}</li>
     * </ul>
     *
     * 上記に以外の型からの変換はサポートしないため{@link IllegalArgumentException}を送出する。
     * なお、{@code null}は変換せずに{@code null}を返却する。
     */
    @Override
    public LocalDateTime convertFromDatabase(final Object databaseAttribute) {
        if (databaseAttribute == null) {
            return null;
        } else if (databaseAttribute instanceof LocalDateTime) {
            return LocalDateTime.class.cast(databaseAttribute);
        } else if (databaseAttribute instanceof Timestamp) {
            return DateTimeUtil.getLocalDateTime(Timestamp.class.cast(databaseAttribute));
        }
        throw new IllegalArgumentException("unsupported data type:"
                + databaseAttribute.getClass()
                                   .getName() + ", value:" + databaseAttribute);
    }
}
