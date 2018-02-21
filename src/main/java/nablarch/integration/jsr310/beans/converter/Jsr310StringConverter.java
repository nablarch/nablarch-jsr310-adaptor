package nablarch.integration.jsr310.beans.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import nablarch.core.beans.Converter;
import nablarch.core.beans.Mergeable;
import nablarch.core.beans.converter.StringConverter;

/**
 * {@code String}型への変換を行う {@link Converter} 。
 * <p>
 * 変換ルールは{@link StringConverter}に準ずるが{@link LocalDate}と{@link LocalDateTime}にも対応している。
 * </p>
 * 
 * @author Taichi Uragami
 *
 */
public class Jsr310StringConverter implements Mergeable<String, Jsr310StringConverter> {

    private final StringConverter converter;
    private final DateTimeFormatter formatter;

    /**
     * デフォルトコンストラクタ。
     */
    public Jsr310StringConverter() {
        this(new StringConverter(), null);
    }

    /**
     * 日付パターンか数値パターン、もしくはその両方を設定してインスタンスを構築する。
     * 
     * @param datePattern 日付パターン
     * @param numberPattern 数値パターン
     */
    public Jsr310StringConverter(String datePattern, String numberPattern) {
        this(new StringConverter(datePattern, numberPattern),
                datePattern != null ? DateTimeFormatter.ofPattern(datePattern) : null);
    }

    private Jsr310StringConverter(StringConverter converter, DateTimeFormatter formatter) {
        this.converter = converter;
        this.formatter = formatter;
    }

    @Override
    public String convert(Object value) {
        if (formatter != null && value instanceof LocalDate) {
            return LocalDate.class.cast(value).format(formatter);
        }
        if (formatter != null && value instanceof LocalDateTime) {
            return LocalDateTime.class.cast(value).format(formatter);
        }
        return converter.convert(value);
    }

    @Override
    public Jsr310StringConverter merge(Jsr310StringConverter other) {
        return new Jsr310StringConverter(
                converter.merge(other.converter),
                formatter != null ? formatter : other.formatter);
    }
}
