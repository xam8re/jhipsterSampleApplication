package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.OfferState;

/**
 * A ServiceOffer.
 */
@Entity
@Table(name = "service_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "serviceoffer")
public class ServiceOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prize")
    private Double prize;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OfferState state;

    @Column(name = "period")
    private Instant period;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private ServiceRequest serviceRequest;

    @OneToMany(mappedBy = "serviceOffer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaskOffer> chosenoffers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private AMSAUser amsaUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrize() {
        return prize;
    }

    public ServiceOffer prize(Double prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public OfferState getState() {
        return state;
    }

    public ServiceOffer state(OfferState state) {
        this.state = state;
        return this;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    public Instant getPeriod() {
        return period;
    }

    public ServiceOffer period(Instant period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Instant period) {
        this.period = period;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public ServiceOffer serviceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
        return this;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Set<TaskOffer> getChosenoffers() {
        return chosenoffers;
    }

    public ServiceOffer chosenoffers(Set<TaskOffer> taskOffers) {
        this.chosenoffers = taskOffers;
        return this;
    }

    public ServiceOffer addChosenoffer(TaskOffer taskOffer) {
        this.chosenoffers.add(taskOffer);
        taskOffer.setServiceOffer(this);
        return this;
    }

    public ServiceOffer removeChosenoffer(TaskOffer taskOffer) {
        this.chosenoffers.remove(taskOffer);
        taskOffer.setServiceOffer(null);
        return this;
    }

    public void setChosenoffers(Set<TaskOffer> taskOffers) {
        this.chosenoffers = taskOffers;
    }

    public AMSAUser getAmsaUser() {
        return amsaUser;
    }

    public ServiceOffer amsaUser(AMSAUser aMSAUser) {
        this.amsaUser = aMSAUser;
        return this;
    }

    public void setAmsaUser(AMSAUser aMSAUser) {
        this.amsaUser = aMSAUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceOffer serviceOffer = (ServiceOffer) o;
        if (serviceOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOffer{" +
            "id=" + getId() +
            ", prize=" + getPrize() +
            ", state='" + getState() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }
}
