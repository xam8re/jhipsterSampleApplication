package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.OfferState;

/**
 * A TaskOffer.
 */
@Entity
@Table(name = "task_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "taskoffer")
public class TaskOffer implements Serializable {

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
    private TaskRequest taskRequest;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private AMSAUser supplier;

    @ManyToOne
    @JsonIgnoreProperties("chosenoffers")
    private ServiceOffer serviceOffer;

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

    public TaskOffer prize(Double prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public OfferState getState() {
        return state;
    }

    public TaskOffer state(OfferState state) {
        this.state = state;
        return this;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    public Instant getPeriod() {
        return period;
    }

    public TaskOffer period(Instant period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Instant period) {
        this.period = period;
    }

    public TaskRequest getTaskRequest() {
        return taskRequest;
    }

    public TaskOffer taskRequest(TaskRequest taskRequest) {
        this.taskRequest = taskRequest;
        return this;
    }

    public void setTaskRequest(TaskRequest taskRequest) {
        this.taskRequest = taskRequest;
    }

    public AMSAUser getSupplier() {
        return supplier;
    }

    public TaskOffer supplier(AMSAUser aMSAUser) {
        this.supplier = aMSAUser;
        return this;
    }

    public void setSupplier(AMSAUser aMSAUser) {
        this.supplier = aMSAUser;
    }

    public ServiceOffer getServiceOffer() {
        return serviceOffer;
    }

    public TaskOffer serviceOffer(ServiceOffer serviceOffer) {
        this.serviceOffer = serviceOffer;
        return this;
    }

    public void setServiceOffer(ServiceOffer serviceOffer) {
        this.serviceOffer = serviceOffer;
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
        TaskOffer taskOffer = (TaskOffer) o;
        if (taskOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskOffer{" +
            "id=" + getId() +
            ", prize=" + getPrize() +
            ", state='" + getState() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }
}
