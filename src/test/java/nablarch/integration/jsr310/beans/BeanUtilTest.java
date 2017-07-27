package nablarch.integration.jsr310.beans;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import nablarch.core.beans.BeanUtil;
import nablarch.core.util.DateUtil;
import nablarch.test.support.SystemRepositoryResource;

import org.junit.Rule;
import org.junit.Test;

/**
 * {@link Jsr310ConversionManager}を適用したBeanUtilのテスト
 */
public class BeanUtilTest {
    
    @Rule
    public SystemRepositoryResource repositoryResource = new SystemRepositoryResource("JSR310.xml");
    
    @Test
    public void test() {
        SrcClass src = new SrcClass();
        src.setName("Taro");
        src.setDate1(LocalDate.of(2017, 6, 13));
        src.setDateTime1(LocalDateTime.of(2017, 6, 13, 11, 30, 15));
        src.setDate2(new Date(DateUtil.getDate("20170614").getTime()));
        src.setDateTime2(new Timestamp(DateUtil.getParsedDate("20170614154520", "yyyyMMddHHmmss").getTime()));
        src.setDate3("20170615");
        src.setDateTime3("2017-06-22T10:22:30.100Z");
        src.setDateTimes(Arrays.asList(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 2)));

        DestClass dest = BeanUtil.createAndCopy(DestClass.class, src);

        assertThat(dest.getName(), is("Taro"));
        assertThat(dest.getDate1(), is(LocalDate.of(2017, 6, 13)));
        assertThat(dest.getDateTime1(), is(LocalDateTime.of(2017, 6, 13, 11, 30, 15)));
        assertThat(dest.getDate2(), is(LocalDate.of(2017, 6, 14)));
        assertThat(dest.getDateTime2(), is(LocalDateTime.of(2017, 6, 14, 15, 45, 20)));
        assertThat(dest.getDate3(), is(LocalDate.of(2017, 6, 15)));
        assertThat(dest.getDateTime3(), is(LocalDateTime.of(2017, 6, 22, 10, 22, 30, 100000000)));
        assertThat(dest.getDateTimes(), is(Arrays.asList(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 2))));
    }
}
