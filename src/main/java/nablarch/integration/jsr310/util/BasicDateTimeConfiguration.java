package nablarch.integration.jsr310.util;

import nablarch.core.util.BasicDateTimeConverterConfiguration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * {@link DateTimeConfiguration}のデフォルト実装クラス
 * {@link nablarch.core.util.BasicDateTimeConverterConfiguration}と同様のクラス
 * <p>
 * Date and Time APIの拡張はコア機能に取り込まれたため、基本は{@link nablarch.core.util.BasicDateTimeConverterConfiguration}を使用すること。
 * このクラスは後方互換維持のため残している。
 *
 * @author TIS
 */
public class BasicDateTimeConfiguration implements DateTimeConfiguration {

    @Override
    public DateTimeFormatter getDateFormatter() {
        return new BasicDateTimeConverterConfiguration().getDateFormatter();
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return new BasicDateTimeConverterConfiguration().getDateTimeFormatter();
    }

    @Override
    public ZoneId getSystemZoneId() {
        return new BasicDateTimeConverterConfiguration().getSystemZoneId();
    }
}
