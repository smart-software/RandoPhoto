package com.rando.library.usermanager;

import com.rando.library.LibManager.GENERALERROR;
import com.rando.library.usermanager.UserInterfaces.IGetTotalRandosResult;

public class GetTotalRandosResult implements IGetTotalRandosResult{
	
	private int totalRandos;
	private GENERALERROR getTotalRandosResult;
	
	public GetTotalRandosResult(GENERALERROR getTotalRandosResult) {
		this.getTotalRandosResult = getTotalRandosResult;
	}
	
	public GetTotalRandosResult(int totalRandos, GENERALERROR getTotalRandosResult) {
		this.totalRandos = totalRandos;
		this.getTotalRandosResult = getTotalRandosResult;
	}
	
	@Override
	public int GetTotalRandos() {
		return this.totalRandos;
	}

	@Override
	public GENERALERROR getTotalRandosResult() {
		return this.getTotalRandosResult;
	}

}
