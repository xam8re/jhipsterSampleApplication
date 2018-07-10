package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
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

/**
 * Descrizione del servizio da parte dell'utente
 */
@ApiModel(description = "Descrizione del servizio da parte dell'utente")
@Entity
@Table(name = "service_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "servicerequest")
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "period")
    private Instant period;

    @OneToMany(mappedBy = "serviceRequest")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaskRequest> tasks = new HashSet<>();

    @OneToMany(mappedBy = "serviceRequest")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceOffer> offers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Material material;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Dimension volume;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ServiceRequestClass srvclass;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private AMSAUser amsaUser;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "service_request_document",
               joinColumns = @JoinColumn(name = "service_requests_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "documents_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceRequest name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getPeriod() {
        return period;
    }

    public ServiceRequest period(Instant period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Instant period) {
        this.period = period;
    }

    public Set<TaskRequest> getTasks() {
        return tasks;
    }

    public ServiceRequest tasks(Set<TaskRequest> taskRequests) {
        this.tasks = taskRequests;
        return this;
    }

    public ServiceRequest addTask(TaskRequest taskRequest) {
        this.tasks.add(taskRequest);
        taskRequest.setServiceRequest(this);
        return this;
    }

    public ServiceRequest removeTask(TaskRequest taskRequest) {
        this.tasks.remove(taskRequest);
        taskRequest.setServiceRequest(null);
        return this;
    }

    public void setTasks(Set<TaskRequest> taskRequests) {
        this.tasks = taskRequests;
    }

    public Set<ServiceOffer> getOffers() {
        return offers;
    }

    public ServiceRequest offers(Set<ServiceOffer> serviceOffers) {
        this.offers = serviceOffers;
        return this;
    }

    public ServiceRequest addOffer(ServiceOffer serviceOffer) {
        this.offers.add(serviceOffer);
        serviceOffer.setServiceRequest(this);
        return this;
    }

    public ServiceRequest removeOffer(ServiceOffer serviceOffer) {
        this.offers.remove(serviceOffer);
        serviceOffer.setServiceRequest(null);
        return this;
    }

    public void setOffers(Set<ServiceOffer> serviceOffers) {
        this.offers = serviceOffers;
    }

    public Material getMaterial() {
        return material;
    }

    public ServiceRequest material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Dimension getVolume() {
        return volume;
    }

    public ServiceRequest volume(Dimension dimension) {
        this.volume = dimension;
        return this;
    }

    public void setVolume(Dimension dimension) {
        this.volume = dimension;
    }

    public ServiceRequestClass getSrvclass() {
        return srvclass;
    }

    public ServiceRequest srvclass(ServiceRequestClass serviceRequestClass) {
        this.srvclass = serviceRequestClass;
        return this;
    }

    public void setSrvclass(ServiceRequestClass serviceRequestClass) {
        this.srvclass = serviceRequestClass;
    }

    public AMSAUser getAmsaUser() {
        return amsaUser;
    }

    public ServiceRequest amsaUser(AMSAUser aMSAUser) {
        this.amsaUser = aMSAUser;
        return this;
    }

    public void setAmsaUser(AMSAUser aMSAUser) {
        this.amsaUser = aMSAUser;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public ServiceRequest documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public ServiceRequest addDocument(Document document) {
        this.documents.add(document);
        return this;
    }

    public ServiceRequest removeDocument(Document document) {
        this.documents.remove(document);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
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
        ServiceRequest serviceRequest = (ServiceRequest) o;
        if (serviceRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }
}
