package cz.fi.muni.pa165.secretservice.entity;

import cz.fi.muni.pa165.secretservice.enums.Language;
import cz.fi.muni.pa165.secretservice.enums.PayGrade;
import cz.fi.muni.pa165.secretservice.enums.Training;
import cz.fi.muni.pa165.secretservice.enums.Weapon;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;
import java.util.EnumSet;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tomco
 */
@Entity
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String codename;

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private final Set<Training> training = EnumSet.noneOf(Training.class);

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private final Set<Language> spokenLanguages = EnumSet.noneOf(Language.class);

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private final Set<Weapon> weaponSkills = EnumSet.noneOf(Weapon.class);

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private PayGrade payGrade;

    @NotNull
    @Column(nullable = false)
    private Boolean isActive;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private java.time.LocalDate joined;

    @Temporal(TemporalType.DATE)
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

    public Set<Language> getSpokenLanguages() {
        return Collections.unmodifiableSet(spokenLanguages);
    }

    public void addSpokenLanguages(Language spokenLanguage) {
        this.spokenLanguages.add(spokenLanguage);
    }

    public Set<Weapon> getWeaponSkills() {
        return Collections.unmodifiableSet(weaponSkills);
    }

    public void addWeaponSkills(Weapon weaponSkill) {
        this.weaponSkills.add(weaponSkill);
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
        return "cz.fi.muni.pa165.secretservice.entity.Agent[ id=" + id + ", codename=" + codename+" ]";
    }

}
