package kg.megalab.kindergarten.mappers;

import kg.megalab.kindergarten.models.GroupCategory;
import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupCategoryMapper {
    GroupCategoryMapper INSTANCE = Mappers.getMapper(GroupCategoryMapper.class);

    GroupCategory groupCategoryCreateDtoToGroupCategory(GroupCategoryCreateDto groupCategoryCreateDto);

    GroupCategoryDto groupCategoryToGroupCategoryDto(GroupCategory groupCategory);

    @Mapping(target = "id", ignore = true)
    void updateGroudCategoryFromDto(GroupCategoryDto groupCategoryDto,@MappingTarget GroupCategory groupCategory);

    List<GroupCategoryDto> groupCategorysToGroupCategoryDtos(List<GroupCategory> groupCategories);
}
