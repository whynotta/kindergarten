package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import org.springframework.http.ResponseEntity;

public interface GroupCategoryService {
    ResponseEntity<GroupCategoryDto> createGroupCategory(GroupCategoryCreateDto groupCategoryCreateDto);

    ResponseEntity<GroupCategoryDto> updateGroupCategory(GroupCategoryDto groupCategoryDto, Long id);

    ResponseEntity<GroupCategoryDto> deleteGroupCategory(Long id);

    ResponseEntity<GroupCategoryDto> findCategoryGroupById(Long id);

    ResponseEntity<?> findAllCategoryGroup(int pageNo, int pageSize);
}
