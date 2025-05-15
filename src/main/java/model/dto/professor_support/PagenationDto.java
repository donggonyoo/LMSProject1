package model.dto.professor_support;

public class PagenationDto {
	
	private String professorId;
	private int currentPage;        // 현재 페이지 번호
    private int totalRows;         // 총 로우 수
    private int itemsPerPage;       // 페이지당 로우 수
    private int totalPages;         // 총 페이지 수
    private int startPage;          // 화면에 보여지는 시작 페이지 번호
    private int endPage;            // 화면에 보여지는 끝 페이지 번호
    private boolean hasPreviousPage; // 이전 페이지 존재 여부
    private boolean hasNextPage;     // 다음 페이지 존재 여부
    private String urlPattern;      // 페이지 이동 URL 패턴 (예: /board?page=)
    private String search;   // 검색 키워드 
    private String sortDirection; // 정렬방향
    
    public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	@Override
	public String toString() {
		return "PagenationDto [professorId=" + professorId + ", currentPage=" + currentPage + ", totalRows=" + totalRows
				+ ", itemsPerPage=" + itemsPerPage + ", totalPages=" + totalPages + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", hasPreviousPage=" + hasPreviousPage + ", hasNextPage=" + hasNextPage
				+ ", urlPattern=" + urlPattern + ", search=" + search + ", sortDirection=" + sortDirection + "]";
	}

	
    
    
}