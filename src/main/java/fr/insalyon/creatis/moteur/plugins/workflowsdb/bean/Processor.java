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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rafael Ferreira da Silva
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Processors.findById", query = "FROM Processor p WHERE p.processorID.workflowID = :workflowID"),
    @NamedQuery(name = "Processors.findByIdAndProcessor", query = "FROM Processor p WHERE p.processorID.workflowID = :workflowID AND p.processorID.processor = :processor"),
    @NamedQuery(name = "Processors.removeById", query = "DELETE FROM Processor p WHERE p.processorID.workflowID = :workflowID")})
@Table(name = "Processors")
public class Processor {

    private ProcessorID processorID;
    private int completed;
    private int queued;
    private int failed;

    public Processor() {
    }

    public Processor(ProcessorID processorID, int completed, int queued, int failed) {
        this.processorID = processorID;
        this.completed = completed;
        this.queued = queued;
        this.failed = failed;
    }

    @EmbeddedId
    public ProcessorID getProcessorID() {
        return processorID;
    }

    public void setProcessorID(ProcessorID processorID) {
        this.processorID = processorID;
    }

    @Column(name = "completed")
    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @Column(name = "queued")
    public int getQueued() {
        return queued;
    }

    public void setQueued(int queued) {
        this.queued = queued;
    }

    @Column(name = "failed")
    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}
