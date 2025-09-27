package kg.megalab.kindergarten.controllers;

import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CRUDController <CreateDto,Dto> {
    ResponseEntity<GlobalResponse> create(@RequestBody CreateDto createDto);
    ResponseEntity<GlobalResponse> update(@RequestBody Dto dto,@PathVariable Long id);
    ResponseEntity<GlobalResponse> delete(@PathVariable Long id);
    ResponseEntity<GlobalResponse> findById(@PathVariable Long id);
    ResponseEntity<GlobalResponse> findAll(@RequestParam int pageNo, @RequestParam int pageSize);

}
