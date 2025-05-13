package domain;

import java.util.Date;

public class Post {
    private String postId;
    private String authorId;
    private String postTitle;
    private String postContent;
    private String postPassword;
    private Date postCreatedAt;
    private Date postUpdatedAt;
    private Integer postGroup;
    private Integer postGroupLevel;
    private Integer postGroupStep;
    private String postFile;
    private Integer postReadCount;
    private boolean postNotice;
    
    // Getters and Setters
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostPassword() {
		return postPassword;
	}
	public void setPostPassword(String postPassword) {
		this.postPassword = postPassword;
	}
	public Date getPostCreatedAt() {
		return postCreatedAt;
	}
	public void setPostCreatedAt(Date postCreatedAt) {
		this.postCreatedAt = postCreatedAt;
	}
	public Date getPostUpdatedAt() {
		return postUpdatedAt;
	}
	public void setPostUpdatedAt(Date postUpdatedAt) {
		this.postUpdatedAt = postUpdatedAt;
	}
	public int getPostGroup() {
		return postGroup;
	}
	public void setPostGroup(int postGroup) {
		this.postGroup = postGroup;
	}
	public int getPostGroupLevel() {
		return postGroupLevel;
	}
	public void setPostGroupLevel(int postGroupLevel) {
		this.postGroupLevel = postGroupLevel;
	}
	public int getPostGroupStep() {
		return postGroupStep;
	}
	public void setPostGroupStep(int postGroupStep) {
		this.postGroupStep = postGroupStep;
	}
	public String getPostFile() {
		return postFile;
	}
	public void setPostFile(String postFile) {
		this.postFile = postFile;
	}
	public int getPostReadCount() {
		return postReadCount;
	}
	public void setPostReadCount(int postReadCount) {
		this.postReadCount = postReadCount;
	}
	public boolean isPostNotice() {
		return postNotice;
	}
	public void setPostNotice(boolean postNotice) {
		this.postNotice = postNotice;
	}
	@Override
	public String toString() {
		return "Post [postId=" + postId + ", authorId=" + authorId + ", postTitle=" + postTitle + ", postContent="
				+ postContent + ", postPassword=" + postPassword + ", postCreatedAt=" + postCreatedAt
				+ ", postUpdatedAt=" + postUpdatedAt + ", postGroup=" + postGroup + ", postGroupLevel=" + postGroupLevel
				+ ", postGroupStep=" + postGroupStep + ", postFile=" + postFile + ", postReadCount=" + postReadCount
				+ ", postNotice=" + postNotice + "]";
	}


    
    
}