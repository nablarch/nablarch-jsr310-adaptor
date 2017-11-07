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

import nablarch.common.dao.EntityList;
import nablarch.common.dao.UniversalDao;
import nablarch.core.db.connection.ConnectionFactory;
import nablarch.core.db.connection.DbConnectionContext;
import nablarch.core.db.connection.TransactionManagerConnection;
import nablarch.core.repository.SystemRepository;
import nablarch.core.transaction.TransactionContext;
import nablarch.core.util.DateUtil;
import nablarch.integration.jsr310.util.DateTimeUtil;
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
 * UniversalDaoでJSR310が使えることを確認するテストクラス。
 */
@RunWith(DatabaseTestRunner.class)
public class UniversalDaoTest {

    @Rule
    public SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource(
            "nablarch/integration/jsr310/db/jsr310.xml");

    /** テストで使用するコネクション */
    private TransactionManagerConnection connection;

    @BeforeClass
    public static void setUpClass() throws Exception {
        VariousDbTestHelper.createTable(TestEntity.class);
    }

    @Before
    public void setUp() throws Exception {
        final ConnectionFactory connectionFactory = SystemRepository.get("connectionFactory");
        connection = connectionFactory.getConnection(TransactionContext.DEFAULT_TRANSACTION_CONTEXT_KEY);
        DbConnectionContext.setConnection(connection);

        VariousDbTestHelper.delete(TestEntity.class);
    }

    @After
    public void tearDown() throws Exception {
        DbConnectionContext.removeConnection();
        if (connection != null) {
            connection.terminate();
        }
    }

    @Test
    public void 登録できること() throws Exception {
        final LocalDate date = LocalDate.now();
        final LocalDateTime dateTime = LocalDateTime.now()
                .withNano(123321000);
        final DaoEntity entity = new DaoEntity(1L, date, dateTime);

        UniversalDao.insert(entity);
        connection.commit();

        final List<TestEntity> actual = VariousDbTestHelper.findAll(TestEntity.class);

        assertThat(actual)
                .extracting("id", "d", "t")
                .containsExactly(tuple(1L, DateTimeUtil.getDate(date), DateTimeUtil.getTimestamp(dateTime)));
    }

    @Test
    public void 検索結果として取得できること() throws Exception {
        VariousDbTestHelper.setUpTable(
                new TestEntity(1L, DateUtil.getDate("20110101"), Timestamp.valueOf("2012-01-02 11:22:33.123321000")),
                new TestEntity(2L, DateUtil.getDate("20110102"), Timestamp.valueOf("2012-02-02 11:22:33.123321000")),
                new TestEntity(3L, DateUtil.getDate("20110103"), Timestamp.valueOf("2012-03-02 11:22:33.123321000"))
        );

        final DaoEntity actual = UniversalDao.findById(DaoEntity.class, 2L);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("dateCol", LocalDate.of(2011, 1, 2))
                .hasFieldOrPropertyWithValue("timestampCol", LocalDateTime.of(2012, 2, 2, 11, 22, 33, 123321000));
    }

    @Test
    public void 条件として使用できること() throws Exception {
        VariousDbTestHelper.setUpTable(
                new TestEntity(1L, DateUtil.getDate("20110101"), Timestamp.valueOf("2012-01-02 11:22:33.123321000")),
                new TestEntity(2L, DateUtil.getDate("20110102"), Timestamp.valueOf("2012-02-02 11:22:33.123321000")),
                new TestEntity(3L, DateUtil.getDate("20110103"), Timestamp.valueOf("2012-03-02 11:22:33.123321000"))
        );

        final EntityList<DaoEntity> actual = UniversalDao.findAllBySqlFile(DaoEntity.class,
                "nablarch.integration.jsr310.db.test#find",
                new DaoEntity(2L, LocalDate.of(2011, 1, 2), LocalDateTime.of(2012, 2, 2, 11, 22, 33, 123321000)));

        assertThat(actual)
                .hasSize(1)
                .extracting("id", "dateCol", "timestampCol")
                .containsExactly(tuple(2L, LocalDate.of(2011, 1, 2), LocalDateTime.of(2012, 2, 2, 11, 22, 33, 123321000)));
    }

    @Entity
    @Table(name = "test_entity")
    public static class TestEntity {

        @Column(name = "id")
        @Id
        public Long id;

        @Column(name = "date_col")
        @Temporal(TemporalType.DATE)
        public Date d;

        @Column(name = "timestamp_col")
        public Timestamp t;

        public TestEntity() {
        }

        public TestEntity(final Long id, final Date d, final Timestamp t) {
            this.id = id;
            this.d = d;
            this.t = t;
        }
    }

    @Entity
    @Table(name = "test_entity")
    public static class DaoEntity {

        private Long id;

        private LocalDate dateCol;

        private LocalDateTime timestampCol;

        public DaoEntity() {
        }

        public DaoEntity(final Long id, final LocalDate dateCol, final LocalDateTime timestampCol) {
            this.id = id;
            this.dateCol = dateCol;
            this.timestampCol = timestampCol;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public void setDateCol(final LocalDate dateCol) {
            this.dateCol = dateCol;
        }

        public void setTimestampCol(final LocalDateTime timestampCol) {
            this.timestampCol = timestampCol;
        }

        @Id
        public Long getId() {
            return id;
        }

        public LocalDate getDateCol() {
            return dateCol;
        }

        public LocalDateTime getTimestampCol() {
            return timestampCol;
        }
    }
}
