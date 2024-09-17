/* Copyright CNRS-CREATIS
 *
 * Rafael Ferreira da Silva
 * rafael.silva@creatis.insa-lyon.fr
 * http://www.rafaelsilva.com
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package fr.insalyon.creatis.moteur.plugins.workflowsdb.dao;

import fr.insalyon.creatis.moteur.plugins.workflowsdb.PluginConfiguration;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.WorkflowsDBException;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Input;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.InputID;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Output;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.OutputID;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Processor;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.ProcessorID;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Stats;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Workflow;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.hibernate.InputData;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.hibernate.OutputData;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.hibernate.ProcessorData;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.hibernate.StatsData;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.hibernate.WorkflowData;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Rafael Ferreira da Silva
 */
public class WorkflowsDBDAOFactory {

    private SessionFactory sessionFactory;

    public WorkflowsDBDAOFactory() throws WorkflowsDBDAOException, WorkflowsDBException {
        this(PluginConfiguration.getInstance());
    }

    public WorkflowsDBDAOFactory(PluginConfiguration conf) throws WorkflowsDBDAOException {

        try {

            Configuration cfg = new Configuration();
            cfg.setProperty("hibernate.default_schema", conf.getSchema());
            cfg.setProperty("hibernate.connection.driver_class", conf.getDriverClass());
            cfg.setProperty("hibernate.connection.url", conf.getUrl());
            // cfg.setProperty("hibernate.dialect", conf.getDialect());
            cfg.setProperty("hibernate.connection.username", conf.getUser());
            cfg.setProperty("hibernate.connection.password", conf.getPassword());
            cfg.setProperty("javax.persistence.validation.mode", "none");
            cfg.setProperty("hibernate.validator.apply_to_ddl", "false");
            cfg.setProperty("hibernate.validator.autoregister_listeners", "false");
            cfg.setProperty("hibernate.hbm2ddl.auto", "update");
            cfg.setProperty("hibernate.show_sql", "true");
            cfg.setProperty("hibernate.format_sql", "true");
            cfg.setProperty("use_sql_comments", "true");

            cfg.addAnnotatedClass(Workflow.class);
            cfg.addAnnotatedClass(Processor.class);
            cfg.addAnnotatedClass(ProcessorID.class);
            cfg.addAnnotatedClass(Input.class);
            cfg.addAnnotatedClass(InputID.class);
            cfg.addAnnotatedClass(Output.class);
            cfg.addAnnotatedClass(OutputID.class);
            cfg.addAnnotatedClass(Stats.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            this.sessionFactory = cfg.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    public void close() {
        sessionFactory.close();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WorkflowDAO getWorkflowDAO() {
        return new WorkflowData(sessionFactory);
    }

    public ProcessorDAO getProcessorDAO() {
        return new ProcessorData(sessionFactory);
    }

    public InputDAO getInputDAO() {
        return new InputData(sessionFactory);
    }

    public OutputDAO getOutputDAO() {
        return new OutputData(sessionFactory);
    }

    public StatsDAO getStatsDAO() {
        return new StatsData(sessionFactory);
    }
}
