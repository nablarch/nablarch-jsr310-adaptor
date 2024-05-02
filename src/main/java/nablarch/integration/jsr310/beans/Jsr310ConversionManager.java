package nablarch.integration.jsr310.beans;

import nablarch.core.beans.BasicConversionManager;

/**
 * デフォルトのコンバータと同様のクラス
 * <p>
 * Date and Time APIの拡張はコア機能に取り込まれたため、基本は{@link nablarch.core.beans.BasicConversionManager}を使用すること。
 * ただし、直接設定ファイルでこのクラスを使用している可能性があるので、後方互換を保つために残している。
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
