package domain;

import java.util.Date;

public class PostComment {
    private String commentId;
    private String postId;
    private String writerId;
    private String commentContent;
    private String parentCommentId;
    private Date createdAt;
    private Date updatedAt;
    private String commentPassword;

    public PostComment() {
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCommentPassword() {
        return commentPassword;
    }

    public void setCommentPassword(String commentPassword) {
        this.commentPassword = commentPassword;
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "commentId='" + commentId + '\'' +
                ", postId='" + postId + '\'' +
                ", writerId='" + writerId + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", parentCommentId='" + parentCommentId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", commentPassword='" + commentPassword + '\'' +
                '}';
    }
}
