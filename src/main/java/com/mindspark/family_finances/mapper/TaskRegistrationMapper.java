package com.mindspark.family_finances.mapper;

import com.mindspark.family_finances.dto.AddTaskRequestDto;
import com.mindspark.family_finances.model.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface TaskRegistrationMapper {

    @Mapping(target = "assigner", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Task toEntity(AddTaskRequestDto taskRegistrationDto);

}
