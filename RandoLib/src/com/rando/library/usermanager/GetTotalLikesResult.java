package com.rando.library.usermanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.usermanager.UserInterfaces.IGetTotalLikesResult;

public class GetTotalLikesResult implements IGetTotalLikesResult{

	
	private int totalLikes;
	private GENERALERROR getTotalLikesResult;

	public GetTotalLikesResult(GENERALERROR getTotalLikesResult) {
		this.getTotalLikesResult = getTotalLikesResult;
	}
	
	public GetTotalLikesResult(int totalLikes, GENERALERROR getTotalLikesResult) {
		this.totalLikes = totalLikes;
		this.getTotalLikesResult = getTotalLikesResult;
	}
	
	
	@Override
	public int GetTotalLikes() {
		return this.totalLikes;
	}

	@Override
	public GENERALERROR getTotalRandosResult() {
		return this.getTotalLikesResult;
	}

}
