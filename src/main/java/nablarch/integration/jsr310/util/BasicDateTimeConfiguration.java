package nablarch.integration.jsr310.util;

import nablarch.core.util.DateTimeConverterConfiguration;
import util.BasicDateTimeConverterConfiguration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * {@link DateTimeConfiguration}のデフォルト実装クラス
 * {@link nablarch.core.util.DateTimeConverterConfiguration}と同様のクラス
 * <p>
 * Date and Time APIの拡張はコア機能に取り込まれたため、基本は{@link nablarch.core.util.BasicDateTimeConverterConfiguration}を使用すること。
 * ただし、後方互換を保つために残している。
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
