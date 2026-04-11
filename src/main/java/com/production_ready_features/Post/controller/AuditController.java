package com.production_ready_features.Post.controller;

import com.production_ready_features.Post.entities.Post;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/audit/posts")
public class AuditController {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @GetMapping(path = "/{postId}")
    public List<Post> getAllRevisions(@PathVariable Long postId)
    {
        AuditReader auditReader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());  //normal hibernate+access to the posts_aud and rev tables


        List<Number> revisions = auditReader.getRevisions(Post.class,postId);//create the query like
        /*
        select rev
        from posts_aud
        where id = postsId = [1,3,4,5]
         */


        return revisions.stream()
                .map(revisionsNum -> auditReader.find(Post.class,postId,revisionsNum))
                /*
                select * from posts_aud where id=postId and revisionNumber=revisionNumber and create the post object using no-args constructor
                 */
                .toList();
    }
}
