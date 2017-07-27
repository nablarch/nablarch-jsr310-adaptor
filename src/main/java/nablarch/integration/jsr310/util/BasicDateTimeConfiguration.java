package nablarch.integration.jsr310.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * {@link DateTimeConfiguration}のデフォルト実装クラス
 *
 * @author TIS
 */
public class BasicDateTimeConfiguration implements DateTimeConfiguration {

    @Override
    public DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.BASIC_ISO_DATE;
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ISO_INSTANT;
    }

    @Override
    public ZoneId getSystemZoneId() {
        return ZoneId.systemDefault();
    }
}
