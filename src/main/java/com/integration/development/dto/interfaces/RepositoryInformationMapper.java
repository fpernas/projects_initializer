package com.integration.development.dto.interfaces;

import com.integration.development.dto.RepositoryInformationDto;
import com.integration.development.dto.request.RepositoryInformationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RepositoryInformationMapper {

    RepositoryInformationMapper INSTANCE = Mappers.getMapper(RepositoryInformationMapper.class);

    RepositoryInformationDto convertRepoInfoToDto(RepositoryInformationRequest request);
}
