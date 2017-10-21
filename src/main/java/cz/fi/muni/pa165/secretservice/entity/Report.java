package cz.fi.muni.pa165.secretservice.entity;


import java.util.HashMap;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Peter Koberling
 */

@Entity
public class Report {
    
    @Id
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String text;
    
    @NotNull
    @Column(nullable = false)
    private java.time.LocalDate created;
    
    @NotNull
    @Column(nullable = false)
    @ManyToOne(targetEntity = Agent.class)
    @JoinColumn
    private Agent creator;
    
    @NotNull
    @Column(nullable = false)
    @ManyToOne(targetEntity = Mission.class)
    @JoinColumn
    private Mission mission;
    
    @JoinTable(
            name = "report_agent_performance",
            joinColumns = {
                    @JoinColumn(name = "report_id"),
                    @JoinColumn(name = "agent_id"),
                    @JoinColumn(name = "performance")
            }
    )
    @Column(name = "report_agent_performance_id")
    private final HashMap<Agent, String> agentsPerformance = new HashMap();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null){
            return Objects.hashCode(this.text) + Objects.hashCode(this.created);
        }
        return Objects.hashCode(this.getId());
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the created
     */
    public java.time.LocalDate getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(java.time.LocalDate created) {
        this.created = created;
    }

    /**
     * @return the creator
     */
    public Agent getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(Agent creator) {
        this.creator = creator;
    }

    /**
     * @return the mission
     */
    public Mission getMission() {
        return mission;
    }

    /**
     * @param mission the mission to set
     */
    public void setMission(Mission mission) {
        this.mission = mission;
    }
    
    public String toString(){
        return "Report("
                + "id="+ this.id + ","
                + "created="+ this.created + ","
                + "creator="+ this.creator.toString() + ","
                + "mission="+ this.mission.toString() + ""
                + ")";
    }
    
}
