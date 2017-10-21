package cz.fi.muni.pa165.secretservice.entity;

import cz.fi.muni.pa165.secretservice.enums.Language;
import cz.fi.muni.pa165.secretservice.enums.PayGrade;
import cz.fi.muni.pa165.secretservice.enums.Training;
import cz.fi.muni.pa165.secretservice.enums.Weapon;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Entity representing an agent of the secret service.
 *
 * @author tomco
 */
@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String codename;

    /**
     * Set of training the agent is proficient in. This training needs to be
     * reapplied after a certain amount of time in order to be positive it is
     * still usable by the agent.
     */
    @ElementCollection(fetch = FetchType.LAZY, targetClass = Training.class)
    @JoinTable(
            name = "agent_training",
            joinColumns = {
                    @JoinColumn(name = "agent_id")}
    )

    @Column(name = "training_id")
    @Enumerated(EnumType.STRING)
    private final Set<Training> training = EnumSet.noneOf(Training.class);

    /**
     * Set of languages the agent is fluent in. The agent needs to test this
     * fluency after a certain amount of time to ensure his capabilities.
     */
    @ElementCollection(fetch = FetchType.LAZY, targetClass = Language.class)
    @JoinTable(
            name = "agent_spoken_languages",
            joinColumns = {
                    @JoinColumn(name = "agent_id")}
    )
    @Column(name = "spoken_languages_id")
    @Enumerated(EnumType.STRING)
    private final Set<Language> spokenLanguages = EnumSet.noneOf(Language.class);

    /**
     * Set of weapons the agent can use during his mission. The agent needs to
     * pass a certain amount of psychological profile in order to be permitted
     * to use these.
     */
    @ElementCollection(fetch = FetchType.LAZY, targetClass = Weapon.class)
    @JoinTable(
            name = "agent_weapons",
            joinColumns = {
                    @JoinColumn(name = "agent_id")}
    )
    @Column(name = "weapons_id")
    @Enumerated(EnumType.STRING)
    private final Set<Weapon> weaponSkills = EnumSet.noneOf(Weapon.class);

    /**
     * A fixed amount of salary range the agent receives in addition to his
     * regular income. It is set to basic by default.
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private PayGrade payGrade = PayGrade.BASIC;

    /**
     * An indicator of whether the agent is currently available to be signed to
     * a mission. It is set as active by default.
     */
    @NotNull
    @Column(nullable = false)
    private Boolean isActive = Boolean.TRUE;

    @NotNull
    @Column(nullable = false)
    private java.time.LocalDate joined;

    private java.time.LocalDate leftAgency;

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

    public Set<Training> getTraining() {
        return Collections.unmodifiableSet(training);
    }

    public void addTraining(Training training) {
        this.training.add(training);
    }

    /**
     * @throws IllegalArgumentException upon trying to remove a training not in
     *                                  the set
     */
    public void removeTraining(Training training) {
        if (!this.training.remove(training)) {
            throw new IllegalArgumentException("Cannot remove " + training
                    + ". Agent " + this.codename + " is not profficient in it.");
        }
    }

    public Set<Language> getSpokenLanguages() {
        return Collections.unmodifiableSet(spokenLanguages);
    }

    public void addSpokenLanguages(Language spokenLanguage) {
        this.spokenLanguages.add(spokenLanguage);
    }

    /**
     * @throws IllegalArgumentException upon trying to remove a language not in
     *                                  the set
     */
    public void removeSpokenLanguages(Language spokenLanguage) {
        if (this.spokenLanguages.remove(spokenLanguage)) {
            throw new IllegalArgumentException("Cannot remove " + spokenLanguage
                    + ". Agent " + this.codename + " is not fluent in it.");
        }
    }

    public Set<Weapon> getWeaponSkills() {
        return Collections.unmodifiableSet(weaponSkills);
    }

    public void addWeaponSkills(Weapon weaponSkill) {
        this.weaponSkills.add(weaponSkill);
    }

    /**
     * @throws IllegalArgumentException upon trying to remove a weapon not in
     *                                  the set
     */
    public void removeWeaponSkills(Weapon weaponSkill) {
        if (this.weaponSkills.remove(weaponSkill)) {
            throw new IllegalArgumentException("Cannot remove " + weaponSkill
                    + " skill. Agent " + this.codename + " is not profficient in it.");
        }
    }

    public PayGrade getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(PayGrade payGrade) {
        this.payGrade = payGrade;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
        this.joined = joined;
    }

    public LocalDate getLeftAgency() {
        return leftAgency;
    }

    public void setLeftAgency(LocalDate leftAgency) {
        this.leftAgency = leftAgency;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + codename.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) obj;
        if (codename == null) {
            if (other.getCodename() != null) {
                return false;
            }
        } else if (!codename.equals(other.getCodename())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.fi.muni.pa165.secretservice.entity.Agent[ id=" + id + ", codename=" + codename + " ]";
    }

}
