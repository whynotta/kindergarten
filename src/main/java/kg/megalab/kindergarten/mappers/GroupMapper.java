package kg.megalab.kindergarten.mappers;

import kg.megalab.kindergarten.models.Group;
import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(source = "groupCategoryId", target = "groupCategory.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    @Mapping(source = "nannyId", target = "nanny.id")
    Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto);

    @Mapping(source = "groupCategory.id", target = "groupCategoryId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "nanny.id", target = "nannyId")
    GroupDto groupToGroupDto(Group group);

    List<GroupDto> groupsToGroupDtos(List<Group> groups);
}
