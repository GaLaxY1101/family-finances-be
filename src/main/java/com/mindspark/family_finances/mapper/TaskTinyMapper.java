package com.mindspark.family_finances.mapper;

import com.mindspark.family_finances.dto.TaskDtoTiny;
import com.mindspark.family_finances.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskTinyMapper {

    @Mapping(target = "assignerFullName", source = "task", qualifiedByName = "mapAssignerFullName")
    @Mapping(target = "assigneeFullName", source = "task", qualifiedByName = "mapAssigneeFullName")
    TaskDtoTiny toDto(Task task);

    @Named("mapAssignerFullName")
    default String mapAssignerFullName(Task task) {
        if (task.getAssigner() != null) {
            return task.getAssigner().getFirstName() + " " + task.getAssigner().getLastName();
        }
        return "";
    }

    @Named("mapAssigneeFullName")
    default String mapAssigneeFullName(Task task) {
        if (task.getAssignee() != null) {
            return task.getAssignee().getFirstName() + " " + task.getAssignee().getLastName();
        }
        return "";
    }
}
