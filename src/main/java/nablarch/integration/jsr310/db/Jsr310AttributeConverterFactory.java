package nablarch.integration.jsr310.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import nablarch.core.db.dialect.AttributeConverterFactory;
import nablarch.core.db.dialect.BasicAttributeConverterFactory;
import nablarch.core.db.dialect.converter.AttributeConverter;
import nablarch.core.db.dialect.converter.IntegerAttributeConverter;
import nablarch.core.db.dialect.converter.ShortAttributeConverter;
import nablarch.core.db.dialect.converter.StringAttributeConverter;
import nablarch.integration.jsr310.db.converter.LocalDateAttributeConverter;
import nablarch.integration.jsr310.db.converter.LocalDateTimeAttributeConverter;

/**
 * JSR310に対応した{@link AttributeConverterFactory}クラス。
 * <p>
 * このクラスでは、{@link LocalDate}と{@link java.time.LocalDateTime}をサポートする。
 * それ以外は、{@link BasicAttributeConverterFactory}に処理を委譲する。
 *
 * @author siosio
 */
public class Jsr310AttributeConverterFactory implements AttributeConverterFactory {

    /** 委譲先 */
    private AttributeConverterFactory delegatee = new BasicAttributeConverterFactory();

    /** 型変換を行う{@link AttributeConverter}定義。 */
    private final Map<Class<?>, AttributeConverter<?>> attributeConverterMap;

    /**
     * コンストラクタ。
     */
    public Jsr310AttributeConverterFactory() {
        final Map<Class<?>, AttributeConverter<?>> attributeConverterMap = new HashMap<Class<?>, AttributeConverter<?>>();
        attributeConverterMap.put(LocalDate.class, new LocalDateAttributeConverter());
        attributeConverterMap.put(LocalDateTime.class, new LocalDateTimeAttributeConverter());
        this.attributeConverterMap = attributeConverterMap;
    }

    @Override
    public <T> AttributeConverter<T> factory(final Class<T> type) {
        @SuppressWarnings("unchecked") final AttributeConverter<T> converter = (AttributeConverter<T>) attributeConverterMap.get(
                type);
        if (converter == null) {
            return delegatee.factory(type);
        }
        return converter;
    }
}
