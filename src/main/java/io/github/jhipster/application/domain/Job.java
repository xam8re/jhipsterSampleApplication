package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.RunStatus;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_data")
    private Instant runData;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RunStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRunData() {
        return runData;
    }

    public Job runData(Instant runData) {
        this.runData = runData;
        return this;
    }

    public void setRunData(Instant runData) {
        this.runData = runData;
    }

    public RunStatus getStatus() {
        return status;
    }

    public Job status(RunStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RunStatus status) {
        this.status = status;
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
        Job job = (Job) o;
        if (job.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), job.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", runData='" + getRunData() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
