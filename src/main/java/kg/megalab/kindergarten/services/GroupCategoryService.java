package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;

public interface GroupCategoryService {
    ResponseEntity<GlobalResponse> createGroupCategory(GroupCategoryCreateDto groupCategoryCreateDto);

    ResponseEntity<GlobalResponse> updateGroupCategory(GroupCategoryDto groupCategoryDto, Long id);

    ResponseEntity<GlobalResponse> deleteGroupCategory(Long id);

    ResponseEntity<GlobalResponse> findGroupCategoryById(Long id);

    ResponseEntity<GlobalResponse> findAllCategoryGroup(int pageNo, int pageSize);
}
