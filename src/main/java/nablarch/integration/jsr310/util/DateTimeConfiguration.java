package nablarch.integration.jsr310.util;

import nablarch.core.util.DateTimeConverterConfiguration;
import nablarch.core.util.annotation.Published;

/**
 * Date and Time APIに関する共通的なフォーマッタ、タイムゾーンを扱うためのインターフェース。
 * {@link nablarch.core.util.DateTimeConverterConfiguration}と同様のクラス
 * <p>
 * 本アダプタで提供される機能はNablarch本体に取り込まれており、本アダプタは後方互換を維持するために残している。
 * 新しく使用する場合は、Nablarch本体の{@link nablarch.core.util.DateTimeConverterConfiguration}を使用すること。
 *
 * @author TIS
 */
@Published(tag = "architect")
public interface DateTimeConfiguration extends DateTimeConverterConfiguration { }
