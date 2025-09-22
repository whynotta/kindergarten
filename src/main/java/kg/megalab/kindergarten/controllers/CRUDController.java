package kg.megalab.kindergarten.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CRUDController <CreateDto,Dto> {
    ResponseEntity<Dto> create(@RequestBody CreateDto createDto);
    ResponseEntity<Dto> update(@RequestBody Dto dto,@PathVariable Long id);
    ResponseEntity<Dto> delete(@PathVariable Long id);
    ResponseEntity<Dto> findById(@PathVariable Long id);
    ResponseEntity<?> findAll(@PathVariable int pageNo,@PathVariable int pageSize);

}
