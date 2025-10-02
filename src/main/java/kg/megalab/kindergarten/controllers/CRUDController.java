package kg.megalab.kindergarten.controllers;

import jakarta.validation.Valid;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CRUDController <CreateDto,Dto> {
    ResponseEntity<GlobalResponse> create(@Valid @RequestBody CreateDto createDto);
    ResponseEntity<GlobalResponse> update(@Valid @RequestBody Dto dto,@PathVariable Long id);
    ResponseEntity<GlobalResponse> delete(@Valid @PathVariable Long id);
    ResponseEntity<GlobalResponse> findById(@Valid @PathVariable Long id);
    ResponseEntity<GlobalResponse> findAll(@Valid @RequestParam int pageNo, @RequestParam int pageSize);

}
