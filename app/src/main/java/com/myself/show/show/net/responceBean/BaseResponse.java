package com.myself.show.show.net.responceBean;

public class BaseResponse {
	public int code;
	public int pageNumber;
	public int pageSize;
	public int pageCount;
	public int totalCount;

	public String access_token;
	public String token_type;
	public String refresh_token;
	public String expires_in;
	public String scope;

	public String error;
	public String error_description;

	public String openid;
	public String nickname;
	public String headimgurl;

	@Override
	public String toString() {
		return "BaseResponse{" +
				"code=" + code +
				", pageNumber=" + pageNumber +
				", pageSize=" + pageSize +
				", pageCount=" + pageCount +
				", totalCount=" + totalCount +
				", access_token='" + access_token + '\'' +
				", token_type='" + token_type + '\'' +
				", refresh_token='" + refresh_token + '\'' +
				", expires_in='" + expires_in + '\'' +
				", scope='" + scope + '\'' +
				", error='" + error + '\'' +
				", error_description='" + error_description + '\'' +
				", openid='" + openid + '\'' +
				", nickname='" + nickname + '\'' +
				", headimgurl='" + headimgurl + '\'' +
				'}';
	}
}
