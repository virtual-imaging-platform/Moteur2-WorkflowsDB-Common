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

package fr.insalyon.creatis.moteur.plugins.workflowsdb.hibernate;

import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Output;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.OutputDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowsDBDAOException;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Rafael Ferreira da Silva
 */
public class OutputData implements OutputDAO {

    private SessionFactory sessionFactory;

    public OutputData(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Output output) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(output);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Output> get(String workflowID) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Output> list = session.createNamedQuery("Outputs.findById", Output.class)
                    .setParameter("workflowID", workflowID)
                    .getResultList();
            session.getTransaction().commit();

            return list;
        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void removeById(String workflowID) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNamedQuery("Outputs.deleteById")
                    .setParameter("workflowID", workflowID)
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }
}
