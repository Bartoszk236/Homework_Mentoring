package com.example.BlogWithPostsAndComments.service;

import com.example.BlogWithPostsAndComments.entity.Author;
import com.example.BlogWithPostsAndComments.entity.BlogPost;
import com.example.BlogWithPostsAndComments.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BlogServiceImpl.class)
public class BlogServiceImplTest {
    private static final String ENTITY_BLOG_POST = "BlogPost";
    private static final String ENTITY_COMMENT = "Comment";
    private static final String ENTITY_AUTHOR = "Author";
    private static final String AUTHOR_NAME = "John Doe";
    private static final String AUTHOR_EMAIL = "john@example.com";
    private static final String POST_TITLE = "Post Title";
    private static final String POST_CONTENT = "Post Content";
    private static final String COMMENT_AUTHOR_NAME = "Comment Author";
    private static final String COMMENT_CONTENT = "Comment Content";

    @Autowired
    private BlogServiceImpl service;
    @Autowired
    private TestEntityManager em;

    @BeforeEach
    public void setup() {
        em.clear();
    }

    @Test
    void givenAuthorInDatabaseWhenCreateBlogPostThenReturnBlogPost() {
        //given
        Author author = createAuthor();
        em.persist(author);
        em.flush();
        em.clear();

        //when
        BlogPost call = service.createPost(author.getId(), POST_TITLE, POST_TITLE);
        em.flush();
        em.clear();

        BlogPost result = em.find(BlogPost.class, call.getId());

        //then
        assertNotNull(result);
        assertEquals(POST_TITLE, result.getTitle());
        assertEquals(POST_CONTENT, result.getContent());

        Author resultAuthor = result.getAuthor();
        assertEquals(author.getId(), resultAuthor.getId());
        assertEquals(AUTHOR_NAME, resultAuthor.getName());
        assertEquals(AUTHOR_EMAIL, resultAuthor.getEmail());
        assertTrue(resultAuthor.getBlogPosts().contains(result));

        Long count = getCountOfRecords(ENTITY_BLOG_POST);

        assertEquals(1, count);
    }

    @Test
    void givenInvalidAuthorIdWhenCreateBlogPostThenThrowException() {
        //given
        Long invalidAuthorId = 999L;

        //when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createPost(invalidAuthorId, POST_TITLE, POST_CONTENT)
        );

        //then
        assertEquals("Author with id: " + invalidAuthorId + " not found", ex.getMessage());
    }

    @Test
    void givenValidPostIdWhenAddCommentToPostThenReturnBlogPostWithNewComment() {
        // given
        Author author = createAuthor();
        em.persist(author);

        BlogPost blogPost = createBlogPost();
        blogPost.setAuthor(author);
        em.persist(blogPost);

        em.flush();
        em.clear();

        Long postId = blogPost.getId();

        //when
        BlogPost updated = service.addCommentToPost(postId, COMMENT_CONTENT, COMMENT_AUTHOR_NAME);
        em.flush();
        em.clear();

        //then
        BlogPost result = em.find(BlogPost.class, postId);
        assertNotNull(result);
        List<Comment> comments = result.getComments();
        assertEquals(1, comments.size());

        Comment comment = comments.getFirst();
        assertEquals(COMMENT_AUTHOR_NAME, comment.getAuthorName());
        assertEquals(COMMENT_CONTENT, comment.getContent());
        assertEquals(postId, comment.getBlogPost().getId());
    }

    @Test
    void givenInvalidPostIdWhenAddCommentToPostThenThrowException() {
        //given
        Long invalidPostId = 999L;

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.addCommentToPost(invalidPostId, COMMENT_CONTENT, COMMENT_AUTHOR_NAME)
        );

        //then
        assertEquals("Post with id: " + invalidPostId + " does not exist", exception.getMessage());
    }

    @Test
    void givenPostWithCommentsInDatabaseWhenGetPostWithCommentsThenReturnBlogPostWithComments() {
        //given
        Author author = createAuthor();
        em.persist(author);

        Comment comment = createComment();

        BlogPost blogPost = createBlogPost();
        blogPost.setAuthor(author);

        blogPost.addComment(comment);
        em.persist(blogPost);
        em.flush();
        em.clear();

        //when
        BlogPost result = service.getPostWithComments(blogPost.getId());

        //then
        assertNotNull(result);

        List<Comment> comments = result.getComments();
        assertEquals(1, comments.size());
        Comment comment1 = comments.getFirst();
        assertEquals(COMMENT_AUTHOR_NAME, comment1.getAuthorName());
        assertEquals(COMMENT_CONTENT, comment1.getContent());
    }

    @Test
    void givenInvalidPostIdWhenGetPostWithCommentsThenThrowException() {
        //given
        Long invalidPostId = 999L;

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getPostWithComments(invalidPostId));

        //then
        assertEquals("Post with id: " + invalidPostId + " does not exist", exception.getMessage());
    }

    @Test
    void givenPostWithCommentsInDatabaseWhenDeletePostThenDeletePostAndCommentsFromDatabase() {
        //given
        Author author = createAuthor();
        em.persist(author);

        Comment comment = createComment();

        BlogPost blogPost = createBlogPost();
        blogPost.setAuthor(author);

        blogPost.addComment(comment);

        em.persist(blogPost);
        em.flush();
        em.clear();

        Long postsCount1 = getCountOfRecords(ENTITY_BLOG_POST);
        Long commentsCount1 = getCountOfRecords(ENTITY_COMMENT);
        Long authorsCount1 = getCountOfRecords(ENTITY_AUTHOR);

        assertEquals(1, postsCount1);
        assertEquals(1, commentsCount1);
        assertEquals(1, authorsCount1);

        //when
        service.deletePost(blogPost.getId());
        em.flush();
        em.clear();

        //then
        Long postsCount = getCountOfRecords(ENTITY_BLOG_POST);
        Long commentsCount = getCountOfRecords(ENTITY_COMMENT);
        Long authorsCount = getCountOfRecords(ENTITY_AUTHOR);

        assertEquals(0, postsCount);
        assertEquals(0, commentsCount);
        assertEquals(1, authorsCount);
    }

    @Test
    void givenOneThousandCommentsWhenGetByLazeThenDisplayDuration() {
        //given
        Long blogPostId = setUpDataAndReturnBlogPostId(1_000);

        //when
        LocalTime startTime = LocalTime.now();
        BlogPost blogPost = em.find(BlogPost.class, blogPostId);
        LocalTime endTime = LocalTime.now();

        System.out.println("Duration: " + Duration.between(startTime, endTime).getNano() + " nanoseconds");
        //0.010416 s
    }

    @Test
    void givenOneThousandCommentsWhenGetByEagerThenDisplayDuration() {
        //given
        Long blogPostId = setUpDataAndReturnBlogPostId(1_000);

        //when
        LocalTime startTime = LocalTime.now();
        BlogPost blogPost = service.getPostWithComments(blogPostId);
        LocalTime endTime = LocalTime.now();

        System.out.println("Duration: " + Duration.between(startTime, endTime).getNano() + " nanoseconds");
        //0.058304 s
    }

    @Test
    void givenOneHundredThousandCommentsWhenGetByLazyThenDisplayDuration() {
        //given
        Long blogPostId = setUpDataAndReturnBlogPostId(100_000);

        //when
        LocalTime startTime = LocalTime.now();
        BlogPost blogPost = em.find(BlogPost.class, blogPostId);
        LocalTime endTime = LocalTime.now();

        System.out.println("Duration: " + Duration.between(startTime, endTime).getNano() + " nanoseconds");
        //0.021892 s
    }

    @Test
    void givenOneHundredThousandCommentsWhenGetByEagerThenDisplayDuration() {
        //given
        Long blogPostId = setUpDataAndReturnBlogPostId(100_000);

        //when
        LocalTime startTime = LocalTime.now();
        BlogPost blogPost = service.getPostWithComments(blogPostId);
        LocalTime endTime = LocalTime.now();

        System.out.println("Duration: " + Duration.between(startTime, endTime).getNano() + " nanoseconds");
        //0.438401 s
    }

    private Long setUpDataAndReturnBlogPostId(int countOfComments) {
        Author author = createAuthor();
        em.persist(author);

        BlogPost blogPost = createBlogPost();
        blogPost.setAuthor(author);

        for (int i = 0; i < countOfComments; i++) {
            blogPost.addComment(createComment());
        }

        em.persist(blogPost);
        em.flush();
        em.clear();
        return blogPost.getId();
    }

    private Long getCountOfRecords(String entityClassName) {
        String query = "select count(p) from " + entityClassName + " p";
        return em.getEntityManager()
                .createQuery(query, Long.class)
                .getSingleResult();
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setName(AUTHOR_NAME);
        author.setEmail(AUTHOR_EMAIL);
        return author;
    }

    private BlogPost createBlogPost() {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(POST_TITLE);
        blogPost.setContent(POST_CONTENT);
        return blogPost;
    }

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setAuthorName(COMMENT_AUTHOR_NAME);
        comment.setContent(COMMENT_CONTENT);
        return comment;
    }
}
