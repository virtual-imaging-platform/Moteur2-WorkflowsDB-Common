package fr.insalyon.creatis.moteur.plugins.workflowsdb;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.DataType;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Input;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.InputID;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Output;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.OutputID;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Processor;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.ProcessorID;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Stats;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Workflow;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.WorkflowStatus;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.InputDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.OutputDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.ProcessorDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.StatsDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowsDBDAOException;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowsDBDAOFactory;

public class WorkflowsDBDAOFactoryTest {

    public static MariaDBContainer<?> mariadb = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.5-ubi"));

    @Mock
    private PluginConfiguration mockConfig;

    private WorkflowsDBDAOFactory factory;

    @BeforeAll
    static void startContainer() throws SQLException {
        mariadb.start();
        
        try (var connection = mariadb.createConnection("")) {
            try (var stmt = connection.createStatement()) {
                stmt.execute("ALTER DATABASE " + mariadb.getDatabaseName() + " CHARACTER SET latin1;");
            }
        }
    }

    @BeforeEach
	void testInit() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(mockConfig.getSchema()).thenReturn(mariadb.getDatabaseName());
        when(mockConfig.getDriverClass()).thenReturn("org.mariadb.jdbc.Driver");
        when(mockConfig.getUrl()).thenReturn(mariadb.getJdbcUrl());
        when(mockConfig.getDialect()).thenReturn("org.hibernate.dialect.MySQLDialect");
        when(mockConfig.getUser()).thenReturn(mariadb.getUsername());
        when(mockConfig.getPassword()).thenReturn(mariadb.getPassword());

        factory = new WorkflowsDBDAOFactory(mockConfig);
	}

    @Test
    void factory() {
       assertNotNull(factory);
    }

    @Test
    void daosNotNull() {
        assertNotNull(factory.getInputDAO());
        assertNotNull(factory.getOutputDAO());
        assertNotNull(factory.getProcessorDAO());
        assertNotNull(factory.getStatsDAO());
        assertNotNull(factory.getWorkflowDAO());
    }

    @Test
    void inputTest() throws WorkflowsDBDAOException {
        InputDAO dao = factory.getInputDAO();
        InputID id = new InputID("non", "superpath", "oui");
        Input test = new Input(id, DataType.String);

        dao.add(test);
        assertNotNull(dao.get(id.getWorkflowID()));
        
        dao.removeById(id.getWorkflowID());
        assertTrue(dao.get(id.getWorkflowID()).isEmpty());
    }

    @Test
    void outputTest() throws WorkflowsDBDAOException {
        OutputDAO dao = factory.getOutputDAO();
        OutputID id = new OutputID("non", "paspath", "non");
        Output test = new Output(id, DataType.String, "123");

        factory.getOutputDAO().add(test);
        assertNotNull(dao.get(id.getWorkflowID()));

        dao.removeById(id.getWorkflowID());
        assertTrue(dao.get(id.getWorkflowID()).isEmpty());
    }

    @Test
    void processorTest() throws WorkflowsDBDAOException {
        ProcessorDAO dao = factory.getProcessorDAO();
        ProcessorID id = new ProcessorID("non", "blabli");
        ProcessorID id2 = new ProcessorID("non", "blibla");
        Processor test = new Processor(id, 0, 0, 0);
        Processor test2 = new Processor(id2, 0, 0, 0);

        dao.add(test);
        dao.add(test2);
        assertNotNull(dao.get(id.getWorkflowID(), id.getProcessor()));
        assertEquals(2, dao.get(id.getWorkflowID()).size());

        test.setFailed(1);
        dao.update(test);
        assertEquals(1, dao.get(id.getWorkflowID(), id.getProcessor()).getFailed());

        dao.remove(test);
        assertNull(dao.get(id.getWorkflowID(), id.getProcessor()));

        dao.removeById(id2.getWorkflowID());
        assertTrue(dao.get(id2.getWorkflowID()).isEmpty());
    }

    @Test
    void statsTest() throws WorkflowsDBDAOException {
        StatsDAO dao = factory.getStatsDAO();
        Stats test = new Stats("workflou");
        Stats test2 = new Stats("workfleau");

        dao.add(test);
        dao.add(test2);
        assertNotNull(dao.get(test.getWorkflowID()));

        test.setCancelled(1);
        dao.update(test);
        assertEquals(1, dao.get(test.getWorkflowID()).getCancelled());

        dao.remove(test);
        assertNull(dao.get(test.getWorkflowID()));

        dao.removeById(test.getWorkflowID());
        assertNull(dao.get(test.getWorkflowID()));
    }

    @Test
    void workflowsTest() throws WorkflowsDBDAOException {
        WorkflowDAO dao = factory.getWorkflowDAO();
        Date dateABefore = Date.from(Instant.now().minusSeconds(60));
        Date dateA = Date.from(Instant.now().minusSeconds(50));
        Date dateAEnd = Date.from(Instant.now().plusSeconds(100));
        Date dateBetween = Date.from(Instant.now().minusSeconds(10));
        Date dateB = Date.from(Instant.now());
        Date dateBEnd = Date.from(Instant.now().plusSeconds(500));

        Workflow test = new Workflow("workflow", "moi", 
            WorkflowStatus.Unknown, 
            dateA,
            dateAEnd,
            "wow incroyable ce workflow",
            "superscanner",
            "0.1",
            "scannersdesyeux",
            "MoteurLitePro");

        Workflow test2 = new Workflow("flowwork", "pasmoi", 
            WorkflowStatus.Unknown, 
            dateB,
            dateBEnd,
            "wow incroyable ce workflow",
            "superscanner",
            "0.1",
            "scannersdesyeux",
            "MoteurLitePro");
        
        // public void add(Workflow workflow)
        dao.add(test);
        dao.add(test2);

        // public void update(Workflow workflow) | Workflow get(String workflowID)
        test.setStatus(WorkflowStatus.Failed);
        dao.update(test);
        assertEquals(WorkflowStatus.Failed, dao.get(test.getId()).getStatus());

        // others gets except the one with userList (too complex)
        assertEquals(2, dao.get().size());
        assertEquals(1, dao.get(test.getUsername(), dateBetween).size());
        assertEquals(1, dao.get(test.getUsername(), test.getApplication(), test.getStatus(), test.getApplicationClass(), dateABefore, dateB).size());
        assertEquals(1, dao.getByUsername(test2.getUsername()).size());

        test.setStatus(WorkflowStatus.Running);
        test2.setStatus(WorkflowStatus.Running);
        dao.update(test);
        dao.update(test2);

        // public long getNumberOfRunning(String username) | public long getNumberOfRunningPerEngine(String engine)
        assertEquals(1, dao.getNumberOfRunning(test.getUsername()));
        assertEquals(2, dao.getNumberOfRunningPerEngine(test.getEngine()));

        // public List<Workflow> getRunning()
        test.setStatus(WorkflowStatus.Killed);
        dao.update(test);
        assertEquals(1, dao.getRunning().size());

        // public void updateUsername(String newUser, String currentUser)
        dao.updateUsername("migouel", "moi");
        assertEquals(1, dao.getByUsername("migouel").size());

        // public void remove(Workflow workflow) | public void removeById(String id)
        dao.remove(test);
        assertNull(dao.get(test.getId()));

        dao.removeById(test2.getId());
        assertNull(dao.get(test2.getId()));
    }

    @AfterEach
    void testClean() {
        factory.close();
    }

    @AfterAll
    static void containerStop() {
        mariadb.stop();
    }
}

