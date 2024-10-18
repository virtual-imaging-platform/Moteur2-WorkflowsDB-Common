package fr.insalyon.creatis.moteur.plugins.workflowsdb;

import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowsDBDAOException;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowsDBDAOFactory;
import lombok.Getter;

@Getter
public abstract class Database {

    protected String            schema;
    protected String            driverClass;
    protected String            url;
    protected String            dialect;
    protected String            user;
    protected String            password;

    protected JdbcDataSource    source;

    private WorkflowsDBDAOFactory factory;

    @Mock
    private PluginConfiguration     mockConfig;

    public abstract void delete();

    public void create() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConfig.getSchema()).thenReturn(getSchema());
        when(mockConfig.getDriverClass()).thenReturn(getDriverClass());
        when(mockConfig.getUrl()).thenReturn(getUrl());
        when(mockConfig.getDialect()).thenReturn(getDialect());
        when(mockConfig.getUser()).thenReturn(getUser());
        when(mockConfig.getPassword()).thenReturn(getPassword());
        when(mockConfig.getHbm2ddl()).thenReturn("update");
        when(mockConfig.getShowSql()).thenReturn("true");

        try {
            factory = new WorkflowsDBDAOFactory(mockConfig);
        } catch (WorkflowsDBDAOException e) { }
    }
}
