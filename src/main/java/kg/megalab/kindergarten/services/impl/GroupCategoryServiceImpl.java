package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.mappers.GroupCategoryMapper;
import kg.megalab.kindergarten.repositories.GroupCategoryRepo;
import kg.megalab.kindergarten.services.GroupCategoryService;
import org.springframework.stereotype.Service;

@Service
public class GroupCategoryServiceImpl implements GroupCategoryService {
    private final GroupCategoryRepo groupCategoryRepo;
    private final GroupCategoryMapper groupCategoryMapper;

    public GroupCategoryServiceImpl(GroupCategoryRepo groupCategoryRepo, GroupCategoryMapper groupCategoryMapper) {
        this.groupCategoryRepo = groupCategoryRepo;
        this.groupCategoryMapper = GroupCategoryMapper.INSTANCE;
    }

}
