package nablarch.integration.jsr310.beans;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import nablarch.core.beans.CopyOptions;
import nablarch.core.repository.SystemRepository;
import nablarch.test.support.SystemRepositoryResource;

/**
 * {@link Jsr310ConvertersProvider}のテスト。
 * 
 * @author Taichi Uragami
 *
 */
public class Jsr310ConvertersProviderTest {

    //SystemRepositoryのクリーンアップをしたいだけなのでnullを渡しているF
    @Rule
    public SystemRepositoryResource resource = new SystemRepositoryResource(null);

    @Test
    public void パターンの設定() {
        SystemRepository.load(() -> Collections.singletonMap("convertersProvider",
                new Jsr310ConvertersProvider()));

        CopyOptions copyOptions = CopyOptions.options()
                .datePatterns(Arrays.asList("yyyy/MM/dd", "yyyy/MM/dd HH:mm"))
                .build();

        assertEquals("2018/02/21",
                copyOptions.convertByType(String.class, LocalDate.of(2018, 2, 21)));
        assertEquals("2018/02/21",
                copyOptions.convertByType(String.class, LocalDateTime.of(2018, 2, 21, 0, 0)));

        assertEquals(LocalDate.of(2018, 2, 21),
                copyOptions.convertByType(LocalDate.class, "2018/02/21"));
        assertEquals(LocalDateTime.of(2018, 2, 21, 0, 0),
                copyOptions.convertByType(LocalDateTime.class, "2018/02/21 00:00"));
    }
}
