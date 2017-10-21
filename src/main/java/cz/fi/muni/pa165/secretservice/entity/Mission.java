package cz.fi.muni.pa165.secretservice.entity;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

/**
 * Represents a secret mission
 *
 * @author Juraj Noge
 */
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A unique mission identifier
     */
    @Column(unique = true, nullable = false)
    @NotNull
    @Max(value = 12)
    private String codename;

    /**
     * List of agents assigned to this mission
     */
    @ManyToMany(targetEntity = Agent.class)
    @JoinColumn
    private Set<Agent> agents;

    /**
     * Mission target coutnry
     */
    @ManyToOne(targetEntity = Country.class)
    private Country country;

    /**
     * Collections of mission reports (one from each agent)
     */
    @OneToOne(targetEntity = Report.class, mappedBy = "mission", cascade = CascadeType.REMOVE)
    private Report report;

    /**
     * Date when mission started
     */
    @Column(name = "start")
    private LocalDate missionStart;

    /**
     * Date when mission was finished
     */
    @Column(name = "end")
    private LocalDate missionEnd;

    /**
     * Duration of mission (this is calculated from start and end of the mission after loading from database)
     */
    @Transient
    private Duration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MissionType missionType;

    /**
     * Specific objective of mission
     */
    @Basic
    @Column(columnDefinition = "TEXT")
    private String objective;

    /**
     * Current status of mission
     */
    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    /**
     * Budget in $ allocated for this mission
     */
    @Basic
    private BigDecimal budget;


    @PostLoad
    public void calculateDuration() {
        if (missionStart != null && missionEnd != null) {
            duration = Duration.between(missionStart, missionEnd);
        }
    }

    /**
     * Types of missions, each with short description about purpose
     */
    public enum MissionType {
        RESCUE("Rescue hostage"), ASSASINATION("Eliminate target"), SPY("Collect intelligence"),
        INFILTRATION("Infiltrate enemy lines"), OTHER("Other");

        private String target;

        MissionType(String target) {
            this.target = target;
        }

        public String getTarget() {
            return target;
        }
    }

    /**
     * Status of mission
     * <p>
     * - ASSIGNED - mission was assigned, waiting for start
     * - CANCELLED - mission was cancelled before it started
     * - ONGOING - mission is in progress
     * - SUCESSFUL - mission was finished and objective was completed
     * - FAILED - mission was finished, but objective was not completed
     */
    public enum MissionStatus {
        ASSIGNED, ONGOING, SUCESSFUL, FAILED, CANCELLED
    }

    public Mission() {
    }

    public Mission(@NotNull @Max(value = 12) String codename) {
        this.codename = codename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public Set<Agent> getAgents() {
        return Collections.unmodifiableSet(agents);
    }

    public void addAgent(Agent agent) {
        this.agents.add(agent);
    }

    public void removeAgent(Agent agent) {
        if (agents.contains(agent)) {
            agents.remove(agent);
        } else {
            throw new IllegalArgumentException("Agent " + agent.getCodename() +
                    " is not assigned to mission " + this.getCodename());
        }
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public LocalDate getMissionStart() {
        return missionStart;
    }

    public void setMissionStart(LocalDate missionStart) {
        this.missionStart = missionStart;
    }

    public LocalDate getMissionEnd() {
        return missionEnd;
    }

    public void setMissionEnd(LocalDate missionEnd) {
        this.missionEnd = missionEnd;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;

        Mission mission = (Mission) o;

        return getCodename().equals(mission.getCodename());
    }

    @Override
    public int hashCode() {
        return getCodename().hashCode();
    }

    @Override
    public String toString() {
        return "Mission{" +
                "codename='" + codename + '\'' +
                ", agents=" + agents +
                ", missionStart=" + missionStart +
                ", missionEnd=" + missionEnd +
                ", objective='" + objective + '\'' +
                ", status=" + status +
                '}';
    }
}
