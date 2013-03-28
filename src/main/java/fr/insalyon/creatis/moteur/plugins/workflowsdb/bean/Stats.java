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

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Rafael Ferreira da Silva
 */
@Entity
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
    
    
}
