package nablarch.integration.jsr310.beans;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nablarch.core.beans.Converter;
import nablarch.core.beans.CopyOptions;
import nablarch.core.beans.CopyOptions.DefaultConvertersProvider;
import nablarch.integration.jsr310.beans.converter.DateConverter;
import nablarch.integration.jsr310.beans.converter.Jsr310StringConverter;
import nablarch.integration.jsr310.beans.converter.LocalDateConverter;
import nablarch.integration.jsr310.beans.converter.LocalDateTimeConverter;
import nablarch.integration.jsr310.beans.converter.SqlDateConverter;
import nablarch.integration.jsr310.beans.converter.SqlTimestampConverter;

/**
 * {@link CopyOptions}を構築する際、日付関連の{@link Converter}にDate and Time APIをサポートしたものを加える{@link ConvertersProvider}の実装クラス。
 * 
 * @author Taichi Uragami
 *
 */
public class Jsr310ConvertersProvider extends DefaultConvertersProvider {

    @Override
    public Map<Class<?>, Converter<?>> provideDateConverters(List<String> patterns) {
        Map<Class<?>, Converter<?>> converters = new HashMap<>();
        converters.put(String.class, new Jsr310StringConverter(patterns.get(0), null));
        converters.put(LocalDate.class, new LocalDateConverter(patterns));
        converters.put(LocalDateTime.class, new LocalDateTimeConverter(patterns));
        converters.put(java.util.Date.class, new DateConverter(patterns));
        converters.put(java.sql.Date.class, new SqlDateConverter(patterns));
        converters.put(Timestamp.class, new SqlTimestampConverter(patterns));
        return converters;
    }

    @Override
    public Map<Class<?>, Converter<?>> provideNumberConverters(List<String> patterns) {
        Map<Class<?>, Converter<?>> converters = super.provideNumberConverters(patterns);
        converters.put(String.class, new Jsr310StringConverter(null, patterns.get(0)));
        return converters;
    }
}
