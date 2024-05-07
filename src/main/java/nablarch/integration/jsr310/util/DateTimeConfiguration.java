package nablarch.integration.jsr310.util;

import nablarch.core.util.DateTimeConverterConfiguration;
import nablarch.core.util.annotation.Published;

/**
 * Date and Time APIに関する共通的なフォーマッタ、タイムゾーンを扱うためのインターフェース。
 * {@link nablarch.core.util.DateTimeConverterConfiguration}と同様のクラス
 * <p>
 * Date and Time APIの拡張はコア機能に取り込まれたため、基本は{@link nablarch.core.util.DateTimeConverterConfiguration}を使用すること。
 * このクラスは後方互換維持のため残している。
 *
 * @author TIS
 */
@Published(tag = "architect")
public interface DateTimeConfiguration extends DateTimeConverterConfiguration { }
