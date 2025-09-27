package kg.megalab.kindergarten.mappers;

import kg.megalab.kindergarten.models.Group;
import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto);

    GroupDto groupToGroupDto(Group group);
}
