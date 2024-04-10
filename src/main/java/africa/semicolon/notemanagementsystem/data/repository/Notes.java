package africa.semicolon.notemanagementsystem.data.repository;

import africa.semicolon.notemanagementsystem.data.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Notes extends MongoRepository<Note, String> {

    Note findByTitle(String title);

    List<Note> findByAuthor(String username);

}
