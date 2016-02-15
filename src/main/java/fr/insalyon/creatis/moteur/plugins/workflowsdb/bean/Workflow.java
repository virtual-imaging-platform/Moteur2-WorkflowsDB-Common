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
package fr.insalyon.creatis.moteur.plugins.workflowsdb.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Ferreira da Silva
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Workflows.findAll", query = "FROM Workflow w ORDER BY w.startedTime DESC"),
    @NamedQuery(name = "Workflows.findById", query = "FROM Workflow w WHERE w.id = :id"),
    @NamedQuery(name = "Workflows.findByUser", query = "FROM Workflow w WHERE w.username = :username ORDER BY w.startedTime DESC"),
    @NamedQuery(name = "Workflows.NumberOfRunning", query = "SELECT COUNT(id) FROM Workflow w WHERE w.username = :username AND w.status = :status"),
    @NamedQuery(name = "Workflows.NumberOfRunningPerEngine", query = "SELECT COUNT(id) FROM Workflow w WHERE w.engine = :engine AND w.status = :status"),
    @NamedQuery(name = "Workflows.getRunning", query = "FROM Workflow w WHERE w.status = :status ORDER BY w.startedTime DESC"),
    @NamedQuery(name = "Workflows.removeById", query = "DELETE FROM Workflow w WHERE w.id = :id"),
    @NamedQuery(name = "Workflows.updateUser", query = "UPDATE Workflow w SET w.username = :newUser WHERE w.username = :currentUser")
})
@Table(name = "Workflows")
public class Workflow {

    private String id;
    private String username;
    private WorkflowStatus status;
    private Date startedTime;
    private Date finishedTime;
    private String description;
    private String application;
    private String applicationVersion;
    private String applicationClass;
    private String engine;

    public Workflow() {
    }

    public Workflow(String id, String username, WorkflowStatus status,
            Date startedTime, Date finishedTime, String description,
            String application, String applicationVersion, String applicationClass, String engine) {

        this.id = id;
        this.username = username;
        this.status = status;
        this.startedTime = startedTime;
        this.finishedTime = finishedTime;
        this.description = description;
        this.application = application;
        this.applicationVersion = applicationVersion;
        this.applicationClass = applicationClass;
        this.engine = engine;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public WorkflowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "launched")
    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_time")
    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    @Column(name = "simulation_name")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "application")
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Column(name = "application_version")
    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    @Column(name = "application_class")
    public String getApplicationClass() {
        return applicationClass;
    }

    public void setApplicationClass(String applicationClass) {
        this.applicationClass = applicationClass;
    }
      
    @Column(name = "engine")
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
    
    public String toString(){
      return "id: "+id+
             "username: "+username+
             ", status: "+status.toString()+
             ", startedTime: "+startedTime.toString()+
             ", finishedTieme: "+(finishedTime == null ? "" : finishedTime.toString())+
             ", description: "+description+
             ", application: "+application+
             ", applicationVersion: "+applicationVersion+
             ", applicationClass: "+applicationClass+
             ", engine: "+engine;
    }
    
}
