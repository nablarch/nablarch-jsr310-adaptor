package nablarch.integration.jsr310.beans;

import nablarch.core.beans.BasicConversionManager;

/**
 * {@link nablarch.core.beans.BasicConversionManager}と同様のクラス
 * <p>
 * Date and Time APIの拡張はコア機能に取り込まれたため、基本は{@link nablarch.core.beans.BasicConversionManager}を使用すること。
 * 設定ファイルでこのクラスを直接指定している可能性を考慮し、後方互換維持のため残している。
 *
 * @author TIS
 */
public class Jsr310ConversionManager extends BasicConversionManager {

    /**
     * コンストラクタ。
     */
    public Jsr310ConversionManager() {
        super();
    }
}
