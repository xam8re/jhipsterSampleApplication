package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TechnologyClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TechnologyClass and its DTO TechnologyClassDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechnologyClassMapper extends EntityMapper<TechnologyClassDTO, TechnologyClass> {



    default TechnologyClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechnologyClass technologyClass = new TechnologyClass();
        technologyClass.setId(id);
        return technologyClass;
    }
}
