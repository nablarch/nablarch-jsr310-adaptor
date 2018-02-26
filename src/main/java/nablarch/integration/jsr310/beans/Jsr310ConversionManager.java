package nablarch.integration.jsr310.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nablarch.core.beans.ConversionManager;
import nablarch.core.beans.Converter;
import nablarch.core.beans.ExtensionConverter;
import nablarch.core.beans.converter.ArrayExtensionConverter;
import nablarch.core.beans.converter.BigDecimalConverter;
import nablarch.core.beans.converter.BooleanConverter;
import nablarch.core.beans.converter.BytesConverter;
import nablarch.core.beans.converter.IntegerConverter;
import nablarch.core.beans.converter.ListExtensionConverter;
import nablarch.core.beans.converter.LongConverter;
import nablarch.core.beans.converter.ObjectArrayConverter;
import nablarch.core.beans.converter.SetExtensionConverter;
import nablarch.core.beans.converter.ShortConverter;
import nablarch.core.beans.converter.StringArrayConverter;
import nablarch.core.beans.converter.StringConverter;
import nablarch.integration.jsr310.beans.converter.DateConverter;
import nablarch.integration.jsr310.beans.converter.Jsr310StringConverter;
import nablarch.integration.jsr310.beans.converter.LocalDateConverter;
import nablarch.integration.jsr310.beans.converter.LocalDateTimeConverter;
import nablarch.integration.jsr310.beans.converter.SqlDateConverter;
import nablarch.integration.jsr310.beans.converter.SqlTimestampConverter;

/**
 * {@link nablarch.core.beans.BasicConversionManager}の代替として、デフォルトのコンバータのうち日付関連の処理をDate and Time APIをサポートしたものに差し替えたクラス
 *
 * @author TIS
 */
public class Jsr310ConversionManager implements ConversionManager {

    /**
     * 型変換に使用する{@link Converter}を格納したMap
     */
    private Map<Class<?>, Converter<?>> converters;

    /** 拡張型変換のList */
    private final List<ExtensionConverter<?>> extensionConverters;

    /**
     * コンストラクタ。
     */
    public Jsr310ConversionManager() {
        final Map<Class<?>, Converter<?>> convertMap = new HashMap<>();
        convertMap.put(Boolean.class, new BooleanConverter());
        convertMap.put(boolean.class, new BooleanConverter());
        convertMap.put(Integer.class, new IntegerConverter());
        convertMap.put(int.class, new IntegerConverter());
        convertMap.put(Short.class, new ShortConverter());
        convertMap.put(short.class, new ShortConverter());
        convertMap.put(Long.class, new LongConverter());
        convertMap.put(long.class, new LongConverter());
        convertMap.put(BigDecimal.class, new BigDecimalConverter());
        convertMap.put(String.class, new StringConverter());
        convertMap.put(String[].class, new StringArrayConverter());
        convertMap.put(Object[].class, new ObjectArrayConverter());
        convertMap.put(Date.class, new DateConverter());
        convertMap.put(java.sql.Date.class, new SqlDateConverter());
        convertMap.put(Timestamp.class, new SqlTimestampConverter());
        convertMap.put(LocalDate.class, new LocalDateConverter());
        convertMap.put(LocalDateTime.class, new LocalDateTimeConverter());
        convertMap.put(byte[].class, new BytesConverter());
        converters = Collections.unmodifiableMap(convertMap);

        final List<ExtensionConverter<?>> extensionConverterList = new ArrayList<>();
        extensionConverterList.add(new ListExtensionConverter());
        extensionConverterList.add(new SetExtensionConverter());
        extensionConverterList.add(new ArrayExtensionConverter());

        extensionConverters = Collections.unmodifiableList(extensionConverterList);
    }

    @Override
    public Map<Class<?>, Converter<?>> getConverters() {
        return converters;
    }

    @Override
    public List<ExtensionConverter<?>> getExtensionConvertor() {
        return extensionConverters;
    }

    /**
     * 日付パターンを設定する。
     * 
     * @param patterns 日付パターン
     */
    public void setDatePatterns(List<String> patterns) {
        if (patterns.isEmpty()) {
            return;
        }
        HashMap<Class<?>, Converter<?>> convertMap = new HashMap<>(
                converters);
        convertMap.put(LocalDate.class, new LocalDateConverter(patterns));
        convertMap.put(LocalDateTime.class, new LocalDateTimeConverter(patterns));
        convertMap.put(Date.class, new DateConverter(patterns));
        convertMap.put(java.sql.Date.class, new SqlDateConverter(patterns));
        convertMap.put(Timestamp.class, new SqlTimestampConverter(patterns));

        Jsr310StringConverter stringConverter = new Jsr310StringConverter(patterns.get(0), null);
        Converter<?> converter = convertMap.get(String.class);
        if (converter != null && converter instanceof Jsr310StringConverter) {
            stringConverter = Jsr310StringConverter.class.cast(converter).merge(stringConverter);
        }
        convertMap.put(String.class, stringConverter);

        converters = Collections.unmodifiableMap(convertMap);
    }

    /**
     * 数値パターンを設定する。
     * 
     * @param patterns 数値パターン
     */
    public void setNumberPatterns(List<String> patterns) {
        if (patterns.isEmpty()) {
            return;
        }
        HashMap<Class<?>, Converter<?>> convertMap = new HashMap<>(
                converters);
        convertMap.put(Integer.class, new IntegerConverter(patterns));
        convertMap.put(int.class, new IntegerConverter(patterns));
        convertMap.put(Short.class, new ShortConverter(patterns));
        convertMap.put(short.class, new ShortConverter(patterns));
        convertMap.put(Long.class, new LongConverter(patterns));
        convertMap.put(long.class, new LongConverter(patterns));
        convertMap.put(BigDecimal.class, new BigDecimalConverter(patterns));

        Jsr310StringConverter stringConverter = new Jsr310StringConverter(null, patterns.get(0));
        Converter<?> converter = convertMap.get(String.class);
        if (converter != null && converter instanceof Jsr310StringConverter) {
            stringConverter = Jsr310StringConverter.class.cast(converter).merge(stringConverter);
        }
        convertMap.put(String.class, stringConverter);

        converters = Collections.unmodifiableMap(convertMap);
    }
}
