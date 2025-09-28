package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    ResponseEntity<GlobalResponse> createGroup(GroupCreateDto groupCreateDto);

    ResponseEntity<GlobalResponse> updateGroup(GroupDto groupDto, Long id);

    ResponseEntity<GlobalResponse> deleteGroup(Long id);

    ResponseEntity<GlobalResponse> findGroupById(Long id);

    ResponseEntity<GlobalResponse> findAllGroups(int pageNo, int pageSize);
}
