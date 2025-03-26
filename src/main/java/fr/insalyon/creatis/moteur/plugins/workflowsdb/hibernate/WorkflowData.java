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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(workflow);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void update(Workflow workflow) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(workflow);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void remove(Workflow workflow) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(workflow);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void removeById(String id) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNamedQuery("Workflows.removeById")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get() throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Workflow> list = session.createNamedQuery("Workflows.findAll", Workflow.class)
                    .list();
            session.getTransaction().commit();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public Workflow get(String workflowID) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Workflow workflow = session.createNamedQuery("Workflows.findById", Workflow.class)
                    .setParameter("id", workflowID)
                    .uniqueResult();
            session.getTransaction().commit();

            return workflow;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get(String username, Date startedTime) throws WorkflowsDBDAOException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Workflow> criteriaQuery = criteriaBuilder.createQuery(Workflow.class);
            Root<Workflow> root = criteriaQuery.from(Workflow.class);
            List<Predicate> predicates = new ArrayList<>();

            if (username != null) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }

            if (startedTime != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("startedTime"), startedTime));
            }

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("startedTime")));

            List<Workflow> list = session.createQuery(criteriaQuery).setMaxResults(10).list();
            session.getTransaction().commit();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> get(String username, String applicationName,
            WorkflowStatus status, String applicationClass, Date startDate, Date endDate, String tag)
            throws WorkflowsDBDAOException {

        List<String> users = username == null ? null : Collections.singletonList(username);
        List<String> applications = applicationName == null ? null : Collections.singletonList(applicationName);

        return get(users, applications, status, applicationClass, startDate, endDate, tag);
    }

    @Override
    public List<Workflow> get(List<String> usersList,  List<String> applications,
            WorkflowStatus status, String applicationClass, Date startDate, Date endDate, String tag)
            throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Workflow> criteriaQuery = criteriaBuilder.createQuery(Workflow.class);
            Root<Workflow> root = criteriaQuery.from(Workflow.class);
            List<Predicate> predicates = new ArrayList<>();

            if (usersList != null && !usersList.isEmpty()) {
                predicates.add(root.get("username").in(usersList));
            }

            if (applications != null && !applications.isEmpty()) {
                predicates.add(root.get("application").in(applications));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (applicationClass != null) {
                predicates.add(criteriaBuilder.equal(root.get("applicationClass"), applicationClass));
            }

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("startedTime"), startDate));
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("startedTime"), endDate));
            }

            if (tag != null) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("tags")),
                    tag.toLowerCase()));
            }

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("startedTime")));

            List<Workflow> list = session.createQuery(criteriaQuery).list();

            session.getTransaction().commit();
            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> getByUsername(String username) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Workflow> list = session.createNamedQuery("Workflows.findByUser", Workflow.class)
                    .setParameter("username", username)
                    .list();
            session.getTransaction().commit();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public long getNumberOfRunning(String username) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long running = session.createNamedQuery("Workflows.NumberOfRunning", Long.class)
                    .setParameter("username", username)
                    .setParameter("status", WorkflowStatus.Running)
                    .uniqueResult();
            session.getTransaction().commit();

            return running;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public long getNumberOfRunningPerEngine(String engine) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long running = session.createNamedQuery("Workflows.NumberOfRunningPerEngine", Long.class)
                    .setParameter("engine", engine)
                    .setParameter("status", WorkflowStatus.Running)
                    .uniqueResult();
            session.getTransaction().commit();

            return running;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public void updateUsername(String newUser, String currentUser) throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNamedQuery("Workflows.updateUser")
                    .setParameter("newUser", newUser)
                    .setParameter("currentUser", currentUser)
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }

    @Override
    public List<Workflow> getRunning() throws WorkflowsDBDAOException {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Workflow> list = (List<Workflow>) session.createNamedQuery("Workflows.getRunning", Workflow.class)
                    .setParameter("status", WorkflowStatus.Running)
                    .list();
            session.getTransaction().commit();

            return list;

        } catch (HibernateException ex) {
            throw new WorkflowsDBDAOException(ex);
        }
    }
}
