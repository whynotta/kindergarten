package kg.megalab.kindergarten.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.rmi.MarshalledObject;

@Mapper(componentModel = "spring")
public interface GroupChildrenMapper {
    GroupChildrenMapper INSTANCE = Mappers.getMapper(GroupChildrenMapper.class);
}
