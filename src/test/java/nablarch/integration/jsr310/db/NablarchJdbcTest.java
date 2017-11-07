package nablarch.integration.jsr310.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nablarch.core.db.connection.ConnectionFactory;
import nablarch.core.db.connection.TransactionManagerConnection;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.repository.SystemRepository;
import nablarch.core.transaction.TransactionContext;
import nablarch.core.util.DateUtil;
import nablarch.test.support.SystemRepositoryResource;
import nablarch.test.support.db.helper.DatabaseTestRunner;
import nablarch.test.support.db.helper.VariousDbTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Nablarchのデータベースアクセス機能でJSR310が使えることを確認するテストクラス。
 */
@RunWith(DatabaseTestRunner.class)
public class NablarchJdbcTest {

    @Rule
    public SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource(
            "nablarch/integration/jsr310/db/jsr310.xml");

    /** テストで使用するコネクション */
    private TransactionManagerConnection connection;

    @BeforeClass
    public static void setUpClass() throws Exception {
        VariousDbTestHelper.createTable(JSR310.class);
    }

    @Before
    public void setUp() throws Exception {
        final ConnectionFactory connectionFactory = SystemRepository.get("connectionFactory");
        connection = connectionFactory.getConnection(TransactionContext.DEFAULT_TRANSACTION_CONTEXT_KEY);
        VariousDbTestHelper.delete(JSR310.class);
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.terminate();
        }
    }

    @Test
    public void LocalDateとLocalDateTimeを登録できること() throws Exception {
        final SqlPStatement statement = connection.prepareStatement("insert into jsr310 (id, date_col, timestamp_col) values (1, ?, ?) ");
        statement.setObject(1, LocalDate.of(2017, 1, 2));
        statement.setObject(2, LocalDateTime.of(1980, 2, 29, 12, 13, 14, 999999000));
        statement.executeUpdate();
        connection.commit();

        final List<JSR310> actual = VariousDbTestHelper.findAll(JSR310.class);
        assertThat(actual)
                  .extracting("date", "timestamp")
                  .containsExactly(tuple(DateUtil.getDate("20170102"), Timestamp.valueOf("1980-02-29 12:13:14.999999000")));
    }

    @Test
    public void LocalDateを条件にできること() throws Exception {
        VariousDbTestHelper.setUpTable(
                new JSR310(1L, DateUtil.getDate("20170809"), Timestamp.valueOf("2016-01-01 11:22:33.123321123")),
                new JSR310(2L, DateUtil.getDate("20170810"), Timestamp.valueOf("2016-01-01 11:22:33.123321123")),
                new JSR310(3L, DateUtil.getDate("20170811"), Timestamp.valueOf("2016-01-01 11:22:33.123321123"))
        );
        final SqlPStatement statement = connection.prepareStatement("select * from jsr310 where date_col = ?");
        statement.setObject(1, LocalDate.of(2017, 8, 10));

        assertThat(statement.retrieve())
                  .extracting(input -> tuple(input.getLong("id"), input.getDate("dateCol")))
                  .containsExactly(tuple(2L, DateUtil.getDate("20170810")));
    }

    @Test
    public void LocalDateTimeを条件にできること() throws Exception {

        VariousDbTestHelper.setUpTable(
                new JSR310(1L, DateUtil.getDate("20170809"), Timestamp.valueOf("2016-01-01 11:22:33.123321000")),
                new JSR310(2L, DateUtil.getDate("20170810"), Timestamp.valueOf("2016-01-01 11:22:34.123321000")),
                new JSR310(3L, DateUtil.getDate("20170811"), Timestamp.valueOf("2016-01-01 11:22:35.123321000"))
        );
        final SqlPStatement statement = connection.prepareStatement("select * from jsr310 where timestamp_col = ?");
        statement.setObject(1, LocalDateTime.of(2016, 1, 1, 11, 22, 34, 123321000));

        assertThat(statement.retrieve())
                  .extracting(input -> tuple(input.getLong("id"), input.getTimestamp("timestampCol")))
                  .containsExactly(tuple(2L, Timestamp.valueOf("2016-01-01 11:22:34.123321000")));
    }

    @Entity
    @Table(name = "jsr310")
    public static class JSR310 {

        @Id
        @Column(name = "id", length = 15)
        public Long id;

        @Column(name = "date_col")
        @Temporal(TemporalType.DATE)
        public Date date;

        @Column(name = "timestamp_col")
        public Timestamp timestamp;

        public JSR310() {
        }

        public JSR310(final Long id, final Date date, final Timestamp timestamp) {
            this.id = id;
            this.date = date;
            this.timestamp = timestamp;
        }
    }
}
