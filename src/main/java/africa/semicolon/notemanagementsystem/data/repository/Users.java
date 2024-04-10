package africa.semicolon.notemanagementsystem.data.repository;

import africa.semicolon.notemanagementsystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends MongoRepository<User, String> {
    User findByUsername(String author);
}
