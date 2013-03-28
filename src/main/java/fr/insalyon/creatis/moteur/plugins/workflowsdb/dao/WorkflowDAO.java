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

import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Workflow;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.WorkflowStatus;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Ferreira da Silva
 */
public interface WorkflowDAO {

    public void add(Workflow workflow) throws WorkflowsDBDAOException;

    public void update(Workflow workflow) throws WorkflowsDBDAOException;

    public void remove(Workflow workflow) throws WorkflowsDBDAOException;

    public List<Workflow> get() throws WorkflowsDBDAOException;

    public Workflow get(String workflowID) throws WorkflowsDBDAOException;

    public List<Workflow> get(String username, Date startedTime) throws WorkflowsDBDAOException;

    public List<Workflow> get(String username, String applicationName, WorkflowStatus status, Date startDate, Date endDate) throws WorkflowsDBDAOException;
    
    public List<Workflow> get(List<String> usersList, String applicationName, WorkflowStatus status, Date startDate, Date endDate) throws WorkflowsDBDAOException;

    public List<Workflow> getByUsername(String username) throws WorkflowsDBDAOException;

    public long getNumberOfRunning(String username) throws WorkflowsDBDAOException;

    public List<Workflow> getRunning() throws WorkflowsDBDAOException;

    public void removeById(String id) throws WorkflowsDBDAOException;

    public void updateUsername(String newUser, String currentUser) throws WorkflowsDBDAOException;
}
