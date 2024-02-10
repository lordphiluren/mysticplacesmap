//package com.sushchenko.mystictourismapp.repos;
//
//import com.sushchenko.mystictourismapp.entities.Comment;
//import org.springframework.data.mongodb.repository.DeleteQuery;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CommentRepo extends MongoRepository<Comment, String> {
//    List<Comment> findByPlaceId(String placeId);
//    @DeleteQuery(value="{'placeId' : ?0}")
//    void deleteByPlaceId(String placeId);
//}
