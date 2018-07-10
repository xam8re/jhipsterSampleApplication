package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.OrderState;

/**
 * A TaskOrder.
 */
@Entity
@Table(name = "task_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "taskorder")
public class TaskOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    @OneToMany(mappedBy = "taskOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderHistory> orderHistories = new HashSet<>();

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

    public TaskOrder state(OrderState state) {
        this.state = state;
        return this;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Set<OrderHistory> getOrderHistories() {
        return orderHistories;
    }

    public TaskOrder orderHistories(Set<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
        return this;
    }

    public TaskOrder addOrderHistory(OrderHistory orderHistory) {
        this.orderHistories.add(orderHistory);
        orderHistory.setTaskOrder(this);
        return this;
    }

    public TaskOrder removeOrderHistory(OrderHistory orderHistory) {
        this.orderHistories.remove(orderHistory);
        orderHistory.setTaskOrder(null);
        return this;
    }

    public void setOrderHistories(Set<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
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
        TaskOrder taskOrder = (TaskOrder) o;
        if (taskOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskOrder{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            "}";
    }
}
