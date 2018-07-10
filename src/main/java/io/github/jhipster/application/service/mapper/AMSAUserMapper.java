package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.AMSAUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AMSAUser and its DTO AMSAUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AMSAUserMapper extends EntityMapper<AMSAUserDTO, AMSAUser> {

    @Mapping(source = "jhuser.id", target = "jhuserId")
    AMSAUserDTO toDto(AMSAUser aMSAUser);

    @Mapping(source = "jhuserId", target = "jhuser")
    AMSAUser toEntity(AMSAUserDTO aMSAUserDTO);

    default AMSAUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AMSAUser aMSAUser = new AMSAUser();
        aMSAUser.setId(id);
        return aMSAUser;
    }
}
