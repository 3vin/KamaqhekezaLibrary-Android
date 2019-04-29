package com.example.tevin.kamaqhekezalibrary.POJO;

public class Upload {

    private String mName;
    private String mImageUrl;
	private String content;

	public Upload() {
		//empty constructor needed
	}

	public Upload(String name, String imageUrl, String content) {
		if (name.trim().equals("")) {
			name = "No Name";
		}

		this.mName = name;
		this.mImageUrl = imageUrl;
		this.content = content;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
			return mImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		mImageUrl = imageUrl;
	}
}
