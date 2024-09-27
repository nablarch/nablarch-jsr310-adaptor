package nablarch.integration.jsr310.util;

import nablarch.core.beans.converter.BasicDateTimeConverterConfiguration;

/**
 * {@link DateTimeConfiguration}のデフォルト実装クラス
 * {@link nablarch.core.beans.converter.BasicDateTimeConverterConfiguration}の代替として、デフォルトのコンバータのうち日付関連の処理をDate and Time APIをサポートしたものに差し替えたクラス
 * <p>
 * 本アダプタで提供される機能はNablarch本体に取り込まれており、本アダプタは後方互換を維持するために残している。
 * 新しく使用する場合は、Nablarch本体の{@link nablarch.core.beans.converter.BasicDateTimeConverterConfiguration}を使用すること。
 *
 * @author TIS
 */
public class BasicDateTimeConfiguration extends BasicDateTimeConverterConfiguration implements DateTimeConfiguration { }
