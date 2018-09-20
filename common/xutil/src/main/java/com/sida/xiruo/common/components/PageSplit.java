package com.sida.xiruo.common.components;

/**
 * <p>PageSplit appointing countPerPage, totalRecord and pageNumber</p>
 * <p>The commons resolution of page split</p>
 * <p>Note: the pageNumber always start with 1 the 2,3 etc</p>
 * @author Lucky
 */
public class PageSplit {
	//count number per page
	private int countPerPage = 1;
	//total record number
	private int totalRecord;
	//currentPage number
	private int currentPageNo = 1;
	
	/**
	* <p>Build a PageSplit by appointing countPerPage, totalRecord and pageNumber</p>
	* <p>Note: the pageNumber always start with 1 the 2,3 etc</p>
	* @param countPerPage count number per page
	* @param totalRecord total record number
	* @param pageNumber current page number
	*/
	public PageSplit(int countPerPage, int totalRecord, int pageNumber){
		setCurrentPageNo(pageNumber);
		this.totalRecord = totalRecord;
		this.currentPageNo = pageNumber;
		checkParameter();
	}
	
	public PageSplit(){
		
	}
	
	private void checkParameter(){
		if(this.countPerPage < 1){
			throw new IllegalArgumentException(String.format("Illegal Parameter countPerPage = %s", countPerPage));
		}else if(this.totalRecord < 0){
			throw new IllegalArgumentException(String.format("Illegal Parameter totalRecord = %s", totalRecord));
		}
	}
	
	/**
	* <p>Get record number of last page</p>
	* @return record number of last page
	*/
	public int getLastPageCount(){
		if(totalRecord == 0){
			return 0;
		}else if(totalRecord % countPerPage == 0){
			return  countPerPage;
		}else{
			return totalRecord - (countPerPage * (getTotalPage() - 1));
		}
	}
	
	/**
	* <p>Get current page number</p>
	* @return current page number
	*/
	public int getCurrentPageNo(){
		return this.currentPageNo;
	}
	
	/**
	* <p>Get total page number</p>
	* @return total page number
	*/
	public int getTotalPage(){
		if(totalRecord == 0){
			return 0;
		}else if(countPerPage == 0){
			throw new java.lang.IllegalArgumentException(String.format("Illegal countPerpage: %d", countPerPage));
		}else if(totalRecord % countPerPage == 0){
			return totalRecord / countPerPage;
		}else{
			return totalRecord / countPerPage + 1;
		}
	}
	
	/**
	* <p>Get next page number</p>
	* @return next page number
	*/
	public int getNextPage(){
		if(currentPageNo < 0){
			return 2;
		}else if(currentPageNo < getTotalPage()){
			return currentPageNo + 1;
		}else{
			return getTotalPage();
		}
	}
	
	/**
	* <p>Get pre page number</p>
	* @return pre page number
	*/
	public int getPrePage(){
		if(currentPageNo >= getTotalPage()){
			return getTotalPage() - 1;
		}else if(currentPageNo > 1){
			return currentPageNo -1;
		}else{
			return 1;
		}
	}
	
	/**
	* <p>Judging whether has next page</p>
	* @return <code>true</code>if next page exists
	*/
	public boolean hasNextPage(){
		if(currentPageNo < getTotalPage()){
			return true;
		}
		return false;
	}
	
	/**
	* <p>Judging whether has pre page</p>
	* @return <code>true</code>if pre page exists
	*/
	public boolean hasPrePage(){
		if(currentPageNo < 2){
			return false;
		}
		return true;
	}
	
	/**
	* <p>Get total record number</p>
	* @return total record number
	*/
	public int getTotalRecord() {
		return totalRecord;
	}
	
	/**
	* <p>Get from index of current page, start with 0</p>
	* @return from index of current page
	*/
	public int getFromIndex(){
		if(currentPageNo <= 1){
			return 0;
		}else if((currentPageNo -1) * countPerPage < totalRecord){
			return (currentPageNo - 1) * countPerPage;
		}else{
			return (this.getTotalPage() - 1) * countPerPage;
		}
	}
	
	/**
	* <p>Get end index of current page</p>
	* @return end index of current page
	*/
	public int getToIndex(){
		int index = getFromIndex() + this.countPerPage;
		if(index < totalRecord){
			return index;
		}else{
			return totalRecord;
		}
	}


	public int getCountPerPage() {
		return countPerPage;
	}


	public void setCountPerPage(int countPerPage) {
		if(this.countPerPage < 1){
			throw new IllegalArgumentException(String.format("Illegal Parameter countPerPage = %s", countPerPage));
		}else{
			this.countPerPage = countPerPage;
		}
		this.currentPageNo = 1;
	}


	public void setTotalRecord(int totalRecord) {
		if(this.totalRecord < 0){
			throw new IllegalArgumentException(String.format("Illegal Parameter totalRecord = %s", totalRecord));
		}else{
			this.totalRecord = totalRecord;
		}
		this.currentPageNo = 1;
	}
	
	public void setCurrentPageNo(int currentPageNo){
		if(currentPageNo < 1 || this.totalRecord == 0){
			this.currentPageNo = 1;
		}else if(currentPageNo > getTotalPage()){
			this.currentPageNo = getTotalPage();
		}else{
			this.currentPageNo = currentPageNo;
		}
	}
}