package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import kg.megalab.kindergarten.services.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    @Override
    public ResponseEntity<GroupDto> createGroup(GroupCreateDto groupCreateDto) {
        return null;
    }
}
