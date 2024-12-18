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
package fr.insalyon.creatis.moteur.plugins.workflowsdb;

import java.io.File;
import java.io.IOException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Rafael Ferreira da Silva
 */
public class PluginConfiguration {

    private static PluginConfiguration instance;
    private String schema;
    private String user;
    private String password;
    private String dialect;
    private String driverClass;
    private String url;

    public static PluginConfiguration getInstance() throws WorkflowsDBException {

        if (instance == null) {
            instance = new PluginConfiguration();
        }
        return instance;
    }

    private PluginConfiguration() throws WorkflowsDBException {

        try {
            File configFile = new File(System.getProperty("user.home") + "/.moteur2/moteur2plugins.conf");
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            PropertiesConfiguration config = new PropertiesConfiguration(configFile);

            schema = config.getString(Constants.LAB_SCHEMA, "workflowsdb");
            user = config.getString(Constants.LAB_USER, "workflowsdb");
            password = config.getString(Constants.LAB_PASSWORD, "workflowsdb");
            dialect = config.getString(Constants.LAB_DIALECT, "org.hibernate.dialect.MySQLDialect");
            driverClass = config.getString(Constants.LAB_DRIVER_CLASS, "com.mysql.jdbc.Driver");
            url = config.getString(Constants.LAB_URL, "jdbc:mysql://localhost:3306/" + schema);

            config.setProperty(Constants.LAB_SCHEMA, schema);
            config.setProperty(Constants.LAB_USER, user);
            config.setProperty(Constants.LAB_PASSWORD, password);
            config.setProperty(Constants.LAB_DIALECT, dialect);
            config.setProperty(Constants.LAB_DRIVER_CLASS, driverClass);
            config.setProperty(Constants.LAB_URL, url);

            config.save();

        } catch (ConfigurationException ex) {
            throw new WorkflowsDBException(ex);
        } catch (IOException ex) {
            throw new WorkflowsDBException(ex);
        }
    }

    public String getSchema() {
        return schema;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDialect() {
        return dialect;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getHbm2ddl() {
        return "validate";
    }

    public String getShowSql() {
        return "false";
    }
}
