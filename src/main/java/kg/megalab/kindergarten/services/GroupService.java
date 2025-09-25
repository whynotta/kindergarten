package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    ResponseEntity<GroupDto> createGroup(GroupCreateDto groupCreateDto);
}
