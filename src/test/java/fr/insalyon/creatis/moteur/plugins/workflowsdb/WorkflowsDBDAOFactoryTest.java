package fr.insalyon.creatis.moteur.plugins.workflowsdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    private WorkflowsDBDAOFactory   factory;
    static List<Database> databases;

    static Stream<Database> setups() {
        return databases.stream();
    }

    @BeforeAll
    static void startDBs() throws SQLException {
        databases = Database.list().stream().filter((e) -> e.isAvailable()).collect(Collectors.toList());
        for (Database db : databases) {
            db.create();
        }
    }

    @AfterAll
    static void stopDBs() {
        for (Database db : databases) {
            db.delete();
        }
    }

    @ParameterizedTest @MethodSource("setups")
    void factory(Database db) {
        factory = db.getFactory();
        assertNotNull(factory);
    }

    @ParameterizedTest @MethodSource("setups")
    void daosNotNull(Database db) {
        factory = db.getFactory();

        assertNotNull(factory.getInputDAO());
        assertNotNull(factory.getOutputDAO());
        assertNotNull(factory.getProcessorDAO());
        assertNotNull(factory.getStatsDAO());
        assertNotNull(factory.getWorkflowDAO());
    }

    @ParameterizedTest @MethodSource("setups")
    void inputTest(Database db) throws WorkflowsDBDAOException {
        factory = db.getFactory();
        InputDAO dao = factory.getInputDAO();
        InputID id = new InputID("non", "superpath", "oui");
        Input test = new Input(id, DataType.String);

        dao.add(test);
        assertNotNull(dao.get(id.getWorkflowID()));
        
        dao.removeById(id.getWorkflowID());
        assertTrue(dao.get(id.getWorkflowID()).isEmpty());
    }

    @ParameterizedTest @MethodSource("setups")
    void outputTest(Database db) throws WorkflowsDBDAOException {
        factory = db.getFactory();
        OutputDAO dao = factory.getOutputDAO();
        OutputID id = new OutputID("non", "paspath", "non");
        Output test = new Output(id, DataType.String, "123");

        factory.getOutputDAO().add(test);
        assertNotNull(dao.get(id.getWorkflowID()));

        dao.removeById(id.getWorkflowID());
        assertTrue(dao.get(id.getWorkflowID()).isEmpty());
    }

    @ParameterizedTest @MethodSource("setups")
    void processorTest(Database db) throws WorkflowsDBDAOException {
        factory = db.getFactory();
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

    @ParameterizedTest @MethodSource("setups")
    void statsTest(Database db) throws WorkflowsDBDAOException {
        factory = db.getFactory();
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

    @ParameterizedTest @MethodSource("setups")
    void workflowsTest(Database db) throws WorkflowsDBDAOException {
        factory = db.getFactory();
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
            "MoteurLitePro",
            "tag");

        Workflow test2 = new Workflow("flowwork", "pasmoi", 
            WorkflowStatus.Unknown, 
            dateB,
            dateBEnd,
            "wow incroyable ce workflow",
            "superscanner",
            "0.1",
            "scannersdesyeux",
            "MoteurLitePro",
            "tag");
        
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
        assertEquals(1, dao.get(test.getUsername(), test.getApplication(), test.getStatus(), test.getApplicationClass(), dateABefore, dateB, "tag").size());
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
}

