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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author Rafael Ferreira da Silva
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Stats.findById", query = "FROM Stats s WHERE s.workflowID = :workflowID"),
    @NamedQuery(name = "Stats.removeById", query = "DELETE FROM Stats s WHERE s.workflowID = :workflowID")
})
@Table(name = "Stats")
public class Stats {

    private String workflowID;
    private int completed;
    private long completedWaitingTime;
    private long completedExecutionTime;
    private long completedInputTime;
    private long completedOutputTime;
    private int cancelled;
    private long cancelledWaitingTime;
    private long cancelledExecutionTime;
    private long cancelledInputTime;
    private long cancelledOutputTime;
    private int failedApplication;
    private long failedApplicationWaitingTime;
    private long failedApplicationExecutionTime;
    private long failedApplicationInputTime;
    private long failedApplicationOutputTime;
    private int failedInput;
    private long failedInputWaitingTime;
    private long failedInputExecutionTime;
    private long failedInputInputTime;
    private long failedInputOutputTime;
    private int failedOutput;
    private long failedOutputWaitingTime;
    private long failedOutputExecutionTime;
    private long failedOutputInputTime;
    private long failedOutputOutputTime;
    private int failedStalled;
    private long failedStalledWaitingTime;
    private long failedStalledExecutionTime;
    private long failedStalledInputTime;
    private long failedStalledOutputTime;

    public Stats() {
    }

    public Stats(String workflowID) {
        this(workflowID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Stats(String workflowID, int completed, long completedWaitingTime,
            long completedExecutionTime, long completedInputTime,
            long completedOutputTime, int cancelled, long cancelledWaitingTime,
            long cancelledExecutionTime, long cancelledInputTime,
            long cancelledOutputTime, int failedApplication,
            long failedApplicationWaitingTime, long failedApplicationExecutionTime,
            long failedApplicationInputTime, long failedApplicationOutputTime,
            int failedInput, long failedInputWaitingTime, long failedInputExecutionTime,
            long failedInputInputTime, long failedInputOutputTime, int failedOutput,
            long failedOutputWaitingTime, long failedOutputExecutionTime,
            long failedOutputInputTime, long failedOutputOutputTime, int failedStalled,
            long failedStalledWaitingTime, long failedStalledExecutionTime,
            long failedStalledInputTime, long failedStalledOutputTime) {

        this.workflowID = workflowID;
        this.completed = completed;
        this.completedWaitingTime = completedWaitingTime;
        this.completedExecutionTime = completedExecutionTime;
        this.completedInputTime = completedInputTime;
        this.completedOutputTime = completedOutputTime;
        this.cancelled = cancelled;
        this.cancelledWaitingTime = cancelledWaitingTime;
        this.cancelledExecutionTime = cancelledExecutionTime;
        this.cancelledInputTime = cancelledInputTime;
        this.cancelledOutputTime = cancelledOutputTime;
        this.failedApplication = failedApplication;
        this.failedApplicationWaitingTime = failedApplicationWaitingTime;
        this.failedApplicationExecutionTime = failedApplicationExecutionTime;
        this.failedApplicationInputTime = failedApplicationInputTime;
        this.failedApplicationOutputTime = failedApplicationOutputTime;
        this.failedInput = failedInput;
        this.failedInputWaitingTime = failedInputWaitingTime;
        this.failedInputExecutionTime = failedInputExecutionTime;
        this.failedInputInputTime = failedInputInputTime;
        this.failedInputOutputTime = failedInputOutputTime;
        this.failedOutput = failedOutput;
        this.failedOutputWaitingTime = failedOutputWaitingTime;
        this.failedOutputExecutionTime = failedOutputExecutionTime;
        this.failedOutputInputTime = failedOutputInputTime;
        this.failedOutputOutputTime = failedOutputOutputTime;
        this.failedStalled = failedStalled;
        this.failedStalledWaitingTime = failedStalledWaitingTime;
        this.failedStalledExecutionTime = failedStalledExecutionTime;
        this.failedStalledInputTime = failedStalledInputTime;
        this.failedStalledOutputTime = failedStalledOutputTime;
    }

    @Id
    @Column(name = "workflow_id")
    public String getWorkflowID() {
        return workflowID;
    }

    public void setWorkflowID(String workflowID) {
        this.workflowID = workflowID;
    }

    @Column(name = "completed")
    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @Column(name = "completed_wt")
    public long getCompletedWaitingTime() {
        return completedWaitingTime;
    }

    public void setCompletedWaitingTime(long completedWaitingTime) {
        this.completedWaitingTime = completedWaitingTime;
    }

    @Column(name = "completed_et")
    public long getCompletedExecutionTime() {
        return completedExecutionTime;
    }

    public void setCompletedExecutionTime(long completedExecutionTime) {
        this.completedExecutionTime = completedExecutionTime;
    }

    @Column(name = "completed_it")
    public long getCompletedInputTime() {
        return completedInputTime;
    }

    public void setCompletedInputTime(long completedInputTime) {
        this.completedInputTime = completedInputTime;
    }

    @Column(name = "completed_ot")
    public long getCompletedOutputTime() {
        return completedOutputTime;
    }

    public void setCompletedOutputTime(long completedOutputTime) {
        this.completedOutputTime = completedOutputTime;
    }

    @Column(name = "cancelled")
    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    @Column(name = "cancelled_wt")
    public long getCancelledWaitingTime() {
        return cancelledWaitingTime;
    }

    public void setCancelledWaitingTime(long cancelledWaitingTime) {
        this.cancelledWaitingTime = cancelledWaitingTime;
    }

    @Column(name = "cancelled_et")
    public long getCancelledExecutionTime() {
        return cancelledExecutionTime;
    }

    public void setCancelledExecutionTime(long cancelledExecutionTime) {
        this.cancelledExecutionTime = cancelledExecutionTime;
    }

    @Column(name = "cancelled_it")
    public long getCancelledInputTime() {
        return cancelledInputTime;
    }

    public void setCancelledInputTime(long cancelledInputTime) {
        this.cancelledInputTime = cancelledInputTime;
    }

    @Column(name = "cancelled_ot")
    public long getCancelledOutputTime() {
        return cancelledOutputTime;
    }

    public void setCancelledOutputTime(long cancelledOutputTime) {
        this.cancelledOutputTime = cancelledOutputTime;
    }

    @Column(name = "failed_app")
    public int getFailedApplication() {
        return failedApplication;
    }

    public void setFailedApplication(int failedApplication) {
        this.failedApplication = failedApplication;
    }

    @Column(name = "failed_app_wt")
    public long getFailedApplicationWaitingTime() {
        return failedApplicationWaitingTime;
    }

    public void setFailedApplicationWaitingTime(long failedApplicationWaitingTime) {
        this.failedApplicationWaitingTime = failedApplicationWaitingTime;
    }

    @Column(name = "failed_app_et")
    public long getFailedApplicationExecutionTime() {
        return failedApplicationExecutionTime;
    }

    public void setFailedApplicationExecutionTime(long failedApplicationExecutionTime) {
        this.failedApplicationExecutionTime = failedApplicationExecutionTime;
    }

    @Column(name = "failed_app_it")
    public long getFailedApplicationInputTime() {
        return failedApplicationInputTime;
    }

    public void setFailedApplicationInputTime(long failedApplicationInputTime) {
        this.failedApplicationInputTime = failedApplicationInputTime;
    }

    @Column(name = "failed_app_ot")
    public long getFailedApplicationOutputTime() {
        return failedApplicationOutputTime;
    }

    public void setFailedApplicationOutputTime(long failedApplicationOutputTime) {
        this.failedApplicationOutputTime = failedApplicationOutputTime;
    }

    @Column(name = "failed_in")
    public int getFailedInput() {
        return failedInput;
    }

    public void setFailedInput(int failedInput) {
        this.failedInput = failedInput;
    }

    @Column(name = "failed_in_wt")
    public long getFailedInputWaitingTime() {
        return failedInputWaitingTime;
    }

    public void setFailedInputWaitingTime(long failedInputWaitingTime) {
        this.failedInputWaitingTime = failedInputWaitingTime;
    }

    @Column(name = "failed_in_et")
    public long getFailedInputExecutionTime() {
        return failedInputExecutionTime;
    }

    public void setFailedInputExecutionTime(long failedInputExecutionTime) {
        this.failedInputExecutionTime = failedInputExecutionTime;
    }

    @Column(name = "failed_in_it")
    public long getFailedInputInputTime() {
        return failedInputInputTime;
    }

    public void setFailedInputInputTime(long failedInputInputTime) {
        this.failedInputInputTime = failedInputInputTime;
    }

    @Column(name = "failed_in_ot")
    public long getFailedInputOutputTime() {
        return failedInputOutputTime;
    }

    public void setFailedInputOutputTime(long failedInputOutputTime) {
        this.failedInputOutputTime = failedInputOutputTime;
    }

    @Column(name = "failed_out")
    public int getFailedOutput() {
        return failedOutput;
    }

    public void setFailedOutput(int failedOutput) {
        this.failedOutput = failedOutput;
    }

    @Column(name = "failed_out_wt")
    public long getFailedOutputWaitingTime() {
        return failedOutputWaitingTime;
    }

    public void setFailedOutputWaitingTime(long failedOutputWaitingTime) {
        this.failedOutputWaitingTime = failedOutputWaitingTime;
    }

    @Column(name = "failed_out_et")
    public long getFailedOutputExecutionTime() {
        return failedOutputExecutionTime;
    }

    public void setFailedOutputExecutionTime(long failedOutputExecutionTime) {
        this.failedOutputExecutionTime = failedOutputExecutionTime;
    }

    @Column(name = "failed_out_it")
    public long getFailedOutputInputTime() {
        return failedOutputInputTime;
    }

    public void setFailedOutputInputTime(long failedOutputInputTime) {
        this.failedOutputInputTime = failedOutputInputTime;
    }

    @Column(name = "failed_out_ot")
    public long getFailedOutputOutputTime() {
        return failedOutputOutputTime;
    }

    public void setFailedOutputOutputTime(long failedOutputOutputTime) {
        this.failedOutputOutputTime = failedOutputOutputTime;
    }

    @Column(name = "failed_sta")
    public int getFailedStalled() {
        return failedStalled;
    }

    public void setFailedStalled(int failedStalled) {
        this.failedStalled = failedStalled;
    }

    @Column(name = "failed_sta_wt")
    public long getFailedStalledWaitingTime() {
        return failedStalledWaitingTime;
    }

    public void setFailedStalledWaitingTime(long failedStalledWaitingTime) {
        this.failedStalledWaitingTime = failedStalledWaitingTime;
    }

    @Column(name = "failed_sta_et")
    public long getFailedStalledExecutionTime() {
        return failedStalledExecutionTime;
    }

    public void setFailedStalledExecutionTime(long failedStalledExecutionTime) {
        this.failedStalledExecutionTime = failedStalledExecutionTime;
    }

    @Column(name = "failed_sta_it")
    public long getFailedStalledInputTime() {
        return failedStalledInputTime;
    }

    public void setFailedStalledInputTime(long failedStalledInputTime) {
        this.failedStalledInputTime = failedStalledInputTime;
    }

    @Column(name = "failed_sta_ot")
    public long getFailedStalledOutputTime() {
        return failedStalledOutputTime;
    }

    public void setFailedStalledOutputTime(long failedStalledOutputTime) {
        this.failedStalledOutputTime = failedStalledOutputTime;
    }
}
