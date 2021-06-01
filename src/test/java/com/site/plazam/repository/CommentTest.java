package com.site.plazam.repository;

import com.site.plazam.domain.Comment;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentTest {

    Comment comment;

    @Autowired
    CommentRepository cr;

//    @Test
//    @Order(1)
//    void commentsCreation() {
//        List<Comment> comments = cr.findAll();
//        for (Comment comment : comments) {
//            comment.setReported(true);
//            cr.save(comment);
//        }
//    }

//    @Test
//    @Order(1)
//    void getPageableCommentList() {
//        Page<Comment> page = cr.findReportedComments(PageRequest.of(2, 5));
//        if (page.hasContent() && !page.isEmpty()) {
//            for (Comment comment : page) {
//                System.out.println(comment.toString());
//                System.out.println("===================");
//            }
//        }
//    }
}
