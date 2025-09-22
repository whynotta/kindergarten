package kg.megalab.kindergarten.controllers;

import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group-category")
public class GroupCategoryController implements CRUDController<GroupCategoryCreateDto, GroupCategoryDto> {

    @Override
    public ResponseEntity<GroupCategoryDto> create(GroupCategoryCreateDto groupCategoryCreateDto) {
        return null;
    }

    @Override
    public ResponseEntity<GroupCategoryDto> update(GroupCategoryDto groupCategoryDto, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<GroupCategoryDto> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<GroupCategoryDto> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> findAll(int pageNo, int pageSize) {
        return null;
    }
}
