package com.example.demo.controller; // <-- 통합 기준 패키지로 갈게

import com.example.demo.dto.SubmenuWithPostsDto;
import com.example.demo.domain.Post;  // <- Post entity 패키지도 맞춰야 해
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts") // ✅ 기준을 맞춰 통일할게
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"}, allowCredentials = "true")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostConstruct
    public void init() {
        System.out.println("PostController 로드됨!");
    }

    // ✅ 1. 메뉴별 최신 게시글 리스트 조회
    @GetMapping("/by-menu/{menuId}")
    public ResponseEntity<List<SubmenuWithPostsDto>> getRecentPosts(@PathVariable int menuId) {
        List<SubmenuWithPostsDto> result = postService.getRecentPostsByMenuId(menuId);
        return ResponseEntity.ok(result);
    }

    // ✅ 2. 게시글 상세 조회
    @GetMapping("/id/{id}")
    public Optional<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // ✅ 3. 게시글 등록 (Form 방식)
    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestParam("submenuId") Integer submenuId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author,
            @RequestParam(value = "hashtags", required = false) String hashtags,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        String thumbnailUrl = null;

        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String originalFilename = thumbnail.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString() + extension;

                File dest = new File(uploadDir + savedFileName);
                thumbnail.transferTo(dest);

                thumbnailUrl = "/uploads/" + savedFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("썸네일 업로드 실패");
            }
        }

        Post post = new Post();
        post.setSubmenuId(submenuId);
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setHashtags(hashtags);
        post.setThumbnailUrl(thumbnailUrl);
        post.setCommentCount(0);
        post.setViewCount(0);

        Post savedPost = postService.savePost(post);
        return ResponseEntity.ok(savedPost);
    }

    // ✅ 4. 게시글 삭제
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    // ✅ 5. 특정 Submenu의 게시글 목록
    @GetMapping("/submenu/{submenuId}")
    public List<Post> getPostsBySubmenu(@PathVariable Integer submenuId) {
        return postService.getPostsBySubmenuId(submenuId);
    }

    // ✅ 6. 페이지네이션 조회
    @GetMapping("/paged")
    public Page<Post> getPagedPosts(Pageable pageable) {
        return postService.getPagedPosts(pageable);
    }

    // ✅ 7. 게시글 조회수 포함 조회
    @GetMapping("/{id}/view")
    public Post getPostWithViewCount(@PathVariable Long id) {
        return postService.getPostAndIncreaseView(id);
    }

    // ✅ 8. 게시글 조회수만 증가
    @PatchMapping("/{id}/view")
    public void incrementViewCount(@PathVariable Long id) {
        postService.incrementViewCount(id);
    }

    // ✅ 9. 전체 게시글 조회 + submenuId 필터
    @GetMapping
    public Page<Post> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer submenuId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postService.getPosts(pageable, submenuId);
    }

    // ✅ 10. 좋아요 증가
    @PatchMapping("/{postId}/like")
    public ResponseEntity<Post> incrementLike(@PathVariable Long postId) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        post.setLikeCount(post.getLikeCount() + 1);
        Post savedPost = postService.savePost(post);
        return ResponseEntity.ok(savedPost);
    }

    // ✅ 11. 싫어요 증가
    @PatchMapping("/{postId}/dislike")
    public ResponseEntity<Post> incrementDislike(@PathVariable Long postId) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        post.setDislikeCount(post.getDislikeCount() + 1);
        Post savedPost = postService.savePost(post);
        return ResponseEntity.ok(savedPost);
    }

    // ✅ 12. 게시글 등록 (JSON 방식)
    @PostMapping("/json")
    public ResponseEntity<?> createPostJson(@RequestBody Post post) {
        post.setCommentCount(0);
        post.setViewCount(0);
        Post savedPost = postService.savePost(post);
        return ResponseEntity.ok(savedPost);
    }
}
