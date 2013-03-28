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

import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.Workflow;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.bean.WorkflowStatus;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowDAO;
import fr.insalyon.creatis.moteur.plugins.workflowsdb.dao.WorkflowsDBDAOException;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Rafael Ferreira da Silva
 */
public class WorkflowData implements WorkflowDAO {

    private SessionFactory sessionFactory;

    public WorkflowData(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Workflow workflow) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(workflow);
            session.getTransaction().commit();
            session.close();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void update(Workflow workflow) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(workflow);
            session.getTransaction().commit();
            session.close();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void remove(Workflow workflow) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(workflow);
            session.getTransaction().commit();
            session.close();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get() throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<Workflow> list = (List<Workflow>) session.getNamedQuery("Workflows.findAll")
                    .list();
            session.getTransaction().commit();
            session.close();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public Workflow get(String workflowID) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Workflow workflow = (Workflow) session.getNamedQuery("Workflows.findById")
                    .setString("id", workflowID)
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();

            return workflow;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get(String username, Date startedTime) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Workflow.class);

            if (username != null) {
                criteria.add(Restrictions.eq("username", username));
            }
            if (startedTime != null) {
                criteria.add(Restrictions.ge("startedTime", startedTime));
            }
            criteria.addOrder(Order.desc("startedTime"));
            criteria.setMaxResults(10);

            List<Workflow> list = (List<Workflow>) criteria.list();
            session.getTransaction().commit();
            session.close();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get(String username, String applicationName,
            WorkflowStatus status, Date startDate, Date endDate)
            throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Workflow.class);

            if (username != null) {
                criteria.add(Restrictions.eq("username", username));
            }
            if (applicationName != null) {
                criteria.add(Restrictions.eq("application", applicationName));
            }
            if (status != null) {
                criteria.add(Restrictions.eq("status", status));
            }
            if (startDate != null) {
                criteria.add(Restrictions.ge("startedTime", startDate));
            }
            if (endDate != null) {
                criteria.add(Restrictions.le("startedTime", endDate));
            }
            criteria.addOrder(Order.desc("startedTime"));

            List<Workflow> list = (List<Workflow>) criteria.list();
            session.getTransaction().commit();
            session.close();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get(List<String> usersList, String applicationName,
            WorkflowStatus status, Date startDate, Date endDate)
            throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Workflow.class);

            if (usersList != null && !usersList.isEmpty()) {
                for (String username : usersList) {
                    criteria.add(Restrictions.eq("username", username));
                }
            }
            if (applicationName != null) {
                criteria.add(Restrictions.eq("application", applicationName));
            }
            if (status != null) {
                criteria.add(Restrictions.eq("status", status));
            }
            if (startDate != null) {
                criteria.add(Restrictions.ge("startedTime", startDate));
            }
            if (endDate != null) {
                criteria.add(Restrictions.le("startedTime", endDate));
            }
            criteria.addOrder(Order.desc("startedTime"));

            List<Workflow> list = (List<Workflow>) criteria.list();
            session.getTransaction().commit();
            session.close();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> getByUsername(String username) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<Workflow> list = (List<Workflow>) session.getNamedQuery("Workflows.findByUser")
                    .setString("username", username)
                    .list();
            session.getTransaction().commit();
            session.close();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public long getNumberOfRunning(String username) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Long running = (Long) session.getNamedQuery("Workflows.NumberOfRunning")
                    .setString("username", username)
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();

            return running;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void removeById(String id) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.getNamedQuery("Workflows.removeById")
                    .setString("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void updateUsername(String newUser, String currentUser) throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.getNamedQuery("Workflows.updateUser")
                    .setString("newUser", newUser)
                    .setString("currentUser", currentUser)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> getRunning() throws WorkflowsDBDAOException {

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<Workflow> list = (List<Workflow>) session.getNamedQuery("Workflows.getRunning")
                    .setString("status", WorkflowStatus.Running.name())
                    .list();
            session.getTransaction().commit();
            session.close();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }
}
