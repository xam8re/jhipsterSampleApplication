package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.OrderState;

/**
 * A OrderHistory.
 */
@Entity
@Table(name = "order_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderhistory")
public class OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    @Column(name = "jhi_date")
    private Instant date;

    @ManyToOne
    @JsonIgnoreProperties("")
    private AMSAUser by;

    @ManyToOne
    @JsonIgnoreProperties("orderHistories")
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JsonIgnoreProperties("orderHistories")
    private TaskOrder taskOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderState getState() {
        return state;
    }

    public OrderHistory state(OrderState state) {
        this.state = state;
        return this;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Instant getDate() {
        return date;
    }

    public OrderHistory date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public AMSAUser getBy() {
        return by;
    }

    public OrderHistory by(AMSAUser aMSAUser) {
        this.by = aMSAUser;
        return this;
    }

    public void setBy(AMSAUser aMSAUser) {
        this.by = aMSAUser;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public OrderHistory serviceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
        return this;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public TaskOrder getTaskOrder() {
        return taskOrder;
    }

    public OrderHistory taskOrder(TaskOrder taskOrder) {
        this.taskOrder = taskOrder;
        return this;
    }

    public void setTaskOrder(TaskOrder taskOrder) {
        this.taskOrder = taskOrder;
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
        OrderHistory orderHistory = (OrderHistory) o;
        if (orderHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
