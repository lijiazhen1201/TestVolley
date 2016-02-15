package com.zhenmei.testvolley.bean;

import java.util.List;

public class Joke {

	private List<DetailEntity> detail;

	public void setDetail(List<DetailEntity> detail) {
		this.detail = detail;
	}

	public List<DetailEntity> getDetail() {
		return detail;
	}

	public static class DetailEntity {
		private String author;
		private String content;
		private String picUrl;

		public void setAuthor(String author) {
			this.author = author;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getAuthor() {
			return author;
		}

		public String getContent() {
			return content;
		}

		public String getPicUrl() {
			return picUrl;
		}

		@Override
		public String toString() {
			return "DetailEntity [author=" + author + ", content=" + content + ", picUrl=" + picUrl + "]";
		}
		
		
	}

}
