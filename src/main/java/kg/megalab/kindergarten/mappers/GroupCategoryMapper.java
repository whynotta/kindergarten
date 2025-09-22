package kg.megalab.kindergarten.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupCategoryMapper {
    GroupCategoryMapper INSTANCE = Mappers.getMapper(GroupCategoryMapper.class);
}
