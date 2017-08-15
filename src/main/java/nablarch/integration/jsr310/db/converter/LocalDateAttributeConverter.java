package nablarch.integration.jsr310.db.converter;

import java.time.LocalDate;
import java.util.Calendar;

import nablarch.core.db.dialect.converter.AttributeConverter;
import nablarch.core.db.util.DbUtil;
import nablarch.integration.jsr310.util.DateTimeUtil;

/**
 * {@link LocalDate}をデータベースとの間で入出力するために変換するクラス。
 *
 * @author siosio
 */
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate> {

    /**
     * 以下の型への変換をサポートする。
     *
     * <ul>
     *     <li>{@link LocalDate}(LocalDateがサポートされていないJDBCドライバがあるため{@link java.sql.Date}に変換する)</li>
     *     <li>{@link java.sql.Date}</li>
     * </ul>
     *
     * 上記に以外の型への変換はサポートしないため{@link IllegalArgumentException}を送出する。
     * また、{@code null}もサポートしない。
     */
    @SuppressWarnings("unchecked")
    @Override
    public <DB> Object convertToDatabase(final LocalDate javaAttribute, final Class<DB> databaseType) {
        if (databaseType.isAssignableFrom(LocalDate.class)
                || databaseType.isAssignableFrom(java.sql.Date.class)) {
            return new java.sql.Date(DateTimeUtil.getDate(javaAttribute).getTime());
        }
        throw new IllegalArgumentException("unsupported database type:"
                + databaseType.getName());
    }

    /**
     * 以下の型からの変換をサポートする。
     *
     * <ul>
     *     <li>{@link LocalDate}</li>
     *     <li>{@link java.util.Date}</li>
     * </ul>
     *
     * 上記に以外の型からの変換はサポートしないため{@link IllegalArgumentException}を送出する。
     * なお、{@code null}は変換せずに{@code null}を返却する。
     */
    @Override
    public LocalDate convertFromDatabase(final Object databaseAttribute) {
        if (databaseAttribute == null) {
            return null;
        } else if (databaseAttribute instanceof LocalDate) {
            return LocalDate.class.cast(databaseAttribute);
        } else if (databaseAttribute instanceof java.util.Date) {
            final Calendar calendar = DbUtil.trimTime((java.util.Date) databaseAttribute);
            return DateTimeUtil.getLocalDate(calendar);
        }
        throw new IllegalArgumentException("unsupported data type:"
                + databaseAttribute.getClass()
                                   .getName() + ", value:" + databaseAttribute);
    }
}
