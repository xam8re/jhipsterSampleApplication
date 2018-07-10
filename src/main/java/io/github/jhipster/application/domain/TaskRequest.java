package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "task_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "taskrequest")
public class TaskRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "period")
    private Instant period;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private ServiceRequest serviceRequest;

    @OneToOne
    @JoinColumn(unique = true)
    private Technology technology;

    @OneToOne
    @JoinColumn(unique = true)
    private Material material;

    @OneToOne
    @JoinColumn(unique = true)
    private Dimension volume;

    @OneToMany(mappedBy = "taskRequest")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaskOffer> offers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public TaskRequest title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public TaskRequest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getPeriod() {
        return period;
    }

    public TaskRequest period(Instant period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Instant period) {
        this.period = period;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public TaskRequest serviceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
        return this;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Technology getTechnology() {
        return technology;
    }

    public TaskRequest technology(Technology technology) {
        this.technology = technology;
        return this;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public Material getMaterial() {
        return material;
    }

    public TaskRequest material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Dimension getVolume() {
        return volume;
    }

    public TaskRequest volume(Dimension dimension) {
        this.volume = dimension;
        return this;
    }

    public void setVolume(Dimension dimension) {
        this.volume = dimension;
    }

    public Set<TaskOffer> getOffers() {
        return offers;
    }

    public TaskRequest offers(Set<TaskOffer> taskOffers) {
        this.offers = taskOffers;
        return this;
    }

    public TaskRequest addOffer(TaskOffer taskOffer) {
        this.offers.add(taskOffer);
        taskOffer.setTaskRequest(this);
        return this;
    }

    public TaskRequest removeOffer(TaskOffer taskOffer) {
        this.offers.remove(taskOffer);
        taskOffer.setTaskRequest(null);
        return this;
    }

    public void setOffers(Set<TaskOffer> taskOffers) {
        this.offers = taskOffers;
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
        TaskRequest taskRequest = (TaskRequest) o;
        if (taskRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskRequest{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }
}
