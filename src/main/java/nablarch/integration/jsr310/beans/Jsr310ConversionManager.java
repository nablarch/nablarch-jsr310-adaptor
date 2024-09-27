package nablarch.integration.jsr310.beans;

import nablarch.core.beans.BasicConversionManager;

/**
 * {@link nablarch.core.beans.BasicConversionManager}と同様のクラス
 * <p>
 * 本アダプタで提供される機能はNablarch本体に取り込まれており、本アダプタは後方互換を維持するために残している。
 * 新しく使用する場合は、Nablarch本体の{@link nablarch.core.beans.BasicConversionManager}を使用すること。
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
