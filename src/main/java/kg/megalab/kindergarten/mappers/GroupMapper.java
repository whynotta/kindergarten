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

    Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto);

    GroupDto groupToGroupDto(Group group);

    @Mapping(target = "id", ignore = true)
    void updateGroupFromGroupDto(GroupDto groupDto,@MappingTarget Group group);

    List<GroupDto> groupsToGroupDtos(List<Group> groups);
}
