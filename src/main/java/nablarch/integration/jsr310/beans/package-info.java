/**
 * BeanUtilでJSR310(Date and Time API)を利用可能にする機能を提供する。
 * <p>
 * Date and Time APIの拡張はコア機能に取り込まれたため、基本はコア機能側を使用すること。
 * Jsr310ConversionManagerは設定ファイルで直接指定している可能性を考慮し、後方互換維持のため残している。
 */
package nablarch.integration.jsr310.beans;